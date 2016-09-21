package com.itany.frame.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.proxy.Callback;

import org.aopalliance.intercept.MethodInterceptor;

import com.itany.frame.spring.aop.definition.AopConfigDefinition;
import com.itany.frame.spring.aop.definition.AopConfigDefinitionParser;
import com.itany.frame.spring.aop.definition.AopConfigDefinitionReader;
import com.itany.frame.spring.aop.proxy.CglibFactory;
import com.itany.frame.spring.aop.proxy.CglibInterceptorImpl;
import com.itany.frame.spring.aop.proxy.InvocationHandlerImpl;
import com.itany.frame.spring.aop.proxy.ProxyFactory;
import com.itany.frame.spring.ioc.GenericApplicationContext;

public class AopContext {
	
	private List<AopConfigDefinition> definitionList = new ArrayList<AopConfigDefinition>();
	
	//切入点 key:id, val:类与方法对应关系的Map
	private Map<String, Map<Class<?>, List<Method>>> pointcutMap = new HashMap<String, Map<Class<?>, List<Method>>>();
	
	//key:类， val:方法与拦截器对应关系的map
	private Map<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>> beanMethodInterceptorConfig 
			= new HashMap<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>>();
	
	public AopContext (Map<String, Object> singletonMap, URL[] urls) throws Exception{
		init(singletonMap, urls);
	}
	
	private void init(Map<String, Object> singletonMap, URL[] urls) throws Exception{
		for (URL url : urls){			
			AopConfigDefinitionReader.readXmlBean(url, definitionList);
		}
		for (AopConfigDefinition definition : definitionList){		
			AopConfigDefinitionParser.pointcutParser(definition, singletonMap, pointcutMap);
			AopConfigDefinitionParser.AopAdvisorParser(pointcutMap, beanMethodInterceptorConfig, singletonMap, definition);
		}
	}
	
	public void instanceProxyBeans(GenericApplicationContext context){
		
		Map<String, Object> singletonMap = context.getSingletonMap();
		Map<Class<?>, Object> proxyClassMap = context.getProxyClassMap();
		Map<String, Object> proxyIdMap = context.getProxyIdMap();
		
		for (Entry<String, Object> entry : singletonMap.entrySet()){
			for (Entry<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>> ic : beanMethodInterceptorConfig.entrySet()){
				
				String id = entry.getKey();
				Class<?> clazz = entry.getValue().getClass();
				
				if (ic.getKey() == clazz){
					
					Object targetBean = entry.getValue();
					ClassLoader classLoader = clazz.getClassLoader();
					LinkedHashMap<Method, List<MethodInterceptor>> linkedHashMap = ic.getValue();
					Class<?>[] interfaceClasses = clazz.getInterfaces();
					Object proxy;
					
					if (interfaceClasses.length != 0){
						InvocationHandler invocationHandler = new InvocationHandlerImpl(targetBean, linkedHashMap);
						ProxyFactory factory = new ProxyFactory(classLoader, interfaceClasses, invocationHandler);
						proxy = factory.createProxyInstance();
						
					} else {
						Callback callback = new CglibInterceptorImpl(targetBean, linkedHashMap);
						CglibFactory factory = new CglibFactory(clazz, callback);
						proxy = factory.createCglibInstance();
					}
					
					proxyClassMap.put(clazz, proxy);
					proxyIdMap.put(id, proxy);
					
					break;
				}
			}
		}
	}
	
	public void addAopContext(GenericApplicationContext context){
		Map<Class<?>, Object> map = context.getSingletonClassMap();
		for (Entry<Class<?>, Object> entry : map.entrySet()){
			Class<?> clazz = entry.getKey();
			if (AopContextAdder.class.isAssignableFrom(clazz)){
				AopContextAdder handler = (AopContextAdder) entry.getValue();
				handler.add(context);
			}
		}
	}

	public Map<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>> getBeanMethodInterceptorConfig() {
		return beanMethodInterceptorConfig;
	}
	
	
}
