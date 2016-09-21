package com.itany.frame.spring.aop.definition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInterceptor;

import com.itany.frame.utils.ReflectUtil;

public class AopConfigDefinitionParser {
	
	public static void pointcutParser(AopConfigDefinition define, Map<String, Object> singletonMap,
			Map<String, Map<Class<?>, List<Method>>> pointcutMap) throws Exception{
		
		for (AopPointcut pointcutConfig : define.getPointcutList()){
			
			String id = pointcutConfig.getId();
			String expression = pointcutConfig.getExpression().trim();
			Map<Class<?>, List<Method>> classMethodMap = new HashMap<Class<?>, List<Method>>();
			
			if (expression.startsWith("(") && expression.endsWith(")")){
				expression = expression.substring(expression.indexOf("(") + 1, expression.lastIndexOf(")")).trim();
			}
			
			String[] expArray = expression.split("\\|\\|");
			
			for (String str : expArray){
				expression = str.trim();
				if (!expression.startsWith("execution")){
					throw new Exception("pointcut表达式不正确, id=" + id + "， expression=" + expression);
				}
				
				expression = expression.substring(expression.indexOf("execution") + 9).trim();
				if (!expression.startsWith("(") || !expression.endsWith(")")){
					throw new Exception("pointcut表达式不正确, id=" + id + "， expression=" + expression);
				}
				
				expression = expression.substring(expression.indexOf("(") + 1, expression.length() - 1).trim();
				
				String returnStr = expression.substring(0, expression.indexOf(" ")).trim();
				String fullPath = expression.substring(expression.indexOf(" ") + 1).trim();
				String parameterStr = fullPath.substring(fullPath.indexOf("(") + 1, fullPath.lastIndexOf(")")).trim();
				String classMethodPath = fullPath.substring(0, fullPath.indexOf("(")).trim();
				String methodName = classMethodPath.substring(classMethodPath.lastIndexOf(".") + 1).trim();
				String classPath = classMethodPath.substring(0, classMethodPath.lastIndexOf(".")).trim();
				
				for (Entry<String, Object> entry : singletonMap.entrySet()){
					Class<?> clazz = entry.getValue().getClass();
					
					if (matchClassPath(classPath, clazz)){
						Method[] methods = ReflectUtil.getAllMethodWithParent(clazz, false);
						List<Method> methodList = grepMethod(methods, methodName, parameterStr, returnStr);
						if (methodList != null && !methodList.isEmpty()){					
							classMethodMap.put(clazz, methodList);
						}
					}
				}
				
				pointcutMap.put(id, classMethodMap);
			}
		}
	}
	
	public static void AopAdvisorParser(Map<String, Map<Class<?>, List<Method>>> pointcutMap, 
			Map<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>> BeanMethodInterceptorConfig, 
			Map<String, Object> singletonMap, AopConfigDefinition define){
		
		for (AopAdvisor advisor : define.getAdvisorList()){
			
			String pointcutRef = advisor.getPointcutRef();
			String adviceRef = advisor.getAdviceRef();
			
			Map<Class<?>, List<Method>> classMethodMap = pointcutMap.get(pointcutRef);
			MethodInterceptor interceptor = (MethodInterceptor) singletonMap.get(adviceRef);
			
			for (Entry<Class<?>, List<Method>> entry : classMethodMap.entrySet()){
				Class<?> clazz = entry.getKey();
				List<Method> methodList = entry.getValue();
				
				if (methodList == null || methodList.isEmpty()){
					continue;
				}
				
				LinkedHashMap<Method, List<MethodInterceptor>> linkMap = BeanMethodInterceptorConfig.get(clazz);
				if (linkMap == null){					
					linkMap = new LinkedHashMap<Method, List<MethodInterceptor>>();
					BeanMethodInterceptorConfig.put(clazz, linkMap);
				} 
				
				for (Method method : methodList){
					List<MethodInterceptor> interceptors = linkMap.get(method);
					if (interceptors == null){
						interceptors = new ArrayList<MethodInterceptor>();
						linkMap.put(method, interceptors);
					}
					
					interceptors.add(interceptor);
				}
			}
		}
	}

	private static List<Method> grepMethod(Method[] methods, String methodName, String parameterStr, String returnStr) {
		
		if ("*".equals(methodName)){
			return Arrays.asList(methods);
		}
		
		List<Method> methodList = new ArrayList<Method>();
		
		for (Method method : methods){
			
			if (!"*".equals(returnStr) && !method.getReturnType().getName().equals(returnStr)){
				continue;
			}
			
			if (!"*".equals(methodName) && !method.getName().equals(methodName)){
				continue;
			}			
			
			if (!"\\.\\.".equals(parameterStr)){
				String[] typenames = parameterStr.split(",");
				Class<?>[] types = method.getParameterTypes();
				
				if (types.length != typenames.length){
					continue;
				}
				
				for (Class<?> type : types){
					for (String tname : typenames){
						if (!type.getName().equals(tname)){
							continue;
						}
					}
				}
			}
			
			methodList.add(method);
		}
		
		return methodList;
	}

	private static boolean matchClassPath(String classPath, Class<?> clazz) {
		
		String namecfg = classPath.substring(classPath.lastIndexOf(".") + 1).trim();
		String packagePath = classPath.substring(0, classPath.lastIndexOf(".")).trim();
		String simpleName = clazz.getSimpleName();
		String fullname = clazz.getName();
		
		//类名一致或为*
		if (!"*".equals(namecfg) && !simpleName.equals(namecfg)){
			return false;
		}
		
		String[] strs = packagePath.split("\\.\\.");
		
		//包路径以前缀开头
		if (strs.length > 0 && !strs[0].trim().equals("")){
			if (!fullname.startsWith(strs[0].trim())){
				return false;
			}
		}
		
		//包路径以后缀结尾
		if (strs.length > 0 && !strs[strs.length-1].trim().endsWith(".")){
			if (!fullname.endsWith(strs[strs.length-1].trim() + "." + simpleName)){
				return false;
			}
		}
		
		for (String str : strs){
			if (!str.trim().equals("" ) && fullname.indexOf(str) < 0){
				return false;
			}
			packagePath = packagePath.substring(packagePath.indexOf(str) + 1);
		}
		
		return true;
	}

}
