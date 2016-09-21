package com.itany.frame.spring.tx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.itany.frame.spring.aop.AopContextAdder;
import com.itany.frame.spring.ioc.GenericApplicationContext;
import com.itany.frame.spring.ioc.anotation.Component;
import com.itany.frame.spring.ioc.anotation.Transactional;

@Component
public class TransactionInterceptor implements MethodInterceptor, AopContextAdder {
		
	AbstractTransactionManager transactionManager;
	TransactionExecuter executer;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		try {
		
			executer.doBegin();;
			Object obj = invocation.proceed();
			executer.doComplete();
			return obj;
			
		} catch (Exception e) {
			executer.doException();
			throw e;
		} finally {
			executer.doOver();
		}
		
	}

	@Override
	public void add(GenericApplicationContext context) {
		
		Map<Class<?>, LinkedHashMap<Method, List<MethodInterceptor>>> proxyConfig = context.getAopContext().getBeanMethodInterceptorConfig();
		
		for (Class<?> key : context.getSingletonClassMap().keySet()){
			
			boolean methodAnnotation = false;
			
			for (Method method : key.getMethods()){
				if (method.isAnnotationPresent(Transactional.class)){
					
					if (!methodAnnotation){
						methodAnnotation = true;
					}
															
					Transactional tx = method.getAnnotation(Transactional.class);
					
					TransactionDefinition txDifine = new TransactionDefinition(
							tx.annotationType(), tx.isolation(),
							tx.noRollbackFor(), tx.noRollbackForClassName(),
							tx.propagation(), tx.readOnly(), tx.timeout(),
							tx.value());
					
					executer = new TransactionExecuter(txDifine, transactionManager);
					
					LinkedHashMap<Method, List<MethodInterceptor>> map = proxyConfig.get(key);
					if (map == null){
						map = new LinkedHashMap<Method, List<MethodInterceptor>>();
						proxyConfig.put(key, map);
					}
					
					List<MethodInterceptor> interceptors = map.get(method);
					if (interceptors == null){
						interceptors = new ArrayList<MethodInterceptor>();
						map.put(method, interceptors);
					}
					
					interceptors.add(0, this);
				}
			}
			
			if (!methodAnnotation){			
				if (key.isAnnotationPresent(Transactional.class)){
					
					Transactional tx = key.getAnnotation(Transactional.class);
					
					TransactionDefinition txDifine = new TransactionDefinition(
							tx.annotationType(), tx.isolation(),
							tx.noRollbackFor(), tx.noRollbackForClassName(),
							tx.propagation(), tx.readOnly(), tx.timeout(),
							tx.value());
					
					executer = new TransactionExecuter(txDifine, transactionManager);
					
					for (Method method : key.getMethods()){
						
						
						LinkedHashMap<Method, List<MethodInterceptor>> map = proxyConfig.get(key);
						if (map == null){
							map = new LinkedHashMap<Method, List<MethodInterceptor>>();
							proxyConfig.put(key, map);
						}
						
						List<MethodInterceptor> interceptors = map.get(method);
						if (interceptors == null){
							interceptors = new ArrayList<MethodInterceptor>();
							map.put(method, interceptors);
						}
						
						interceptors.add(0, this);
					}
				}
			}
		}
		
	}

}
