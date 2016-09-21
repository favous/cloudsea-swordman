package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CglibMethodInvocationImpl implements MethodInvocation {
	
	private Object target;
	private Method method;
	private Object[] arguments;
	private MethodProxy methodProxy;
	
	
	public CglibMethodInvocationImpl(Object target, Method method, Object[] arguments) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	public CglibMethodInvocationImpl(Object target, Method method,
			Object[] arguments, MethodProxy methodProxy) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.methodProxy = methodProxy;
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
	public Object getThis() {
		return this;
	}

	@Override
	public Object proceed() throws Throwable {
		if (methodProxy == null){
			return getMethod().invoke(target, getArguments());
		} else {
			return methodProxy.invokeSuper(target, getArguments());
		}
	}

	@Override
	public AccessibleObject getStaticPart() {
		return null;
	}
	
	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

}
