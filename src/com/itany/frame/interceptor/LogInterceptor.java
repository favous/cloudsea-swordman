package com.itany.frame.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.itany.frame.spring.ioc.anotation.Component;

@Component
public class LogInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		
		try {
			System.out.println("begin");
			Object obj = arg0.proceed();
			System.out.println("seccuss");
			return obj;
		} catch (Exception e) {
			System.out.println("Exception");
			throw e;
		}
	}

}
