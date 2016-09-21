package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class MethodInvocationImpl implements MethodInvocation {
	
	private Object target;
	private Method method;
	private Object[] arguments;
	private AccessibleObject staticPart;
	
	
	public MethodInvocationImpl(Object target, Method method, Object[] arguments) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	public MethodInvocationImpl(Object target, Method method,
			Object[] arguments, AccessibleObject staticPart) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.staticPart = staticPart;
	}

	
	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return staticPart;
	}

	@Override
	public Object getThis() {
		return this;
	}

	@Override
	public Object proceed() throws Throwable {
		return getMethod().invoke(target, getArguments());
	}

}
