package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * proxy对象创建工厂
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ProxyFactory {
	
	ClassLoader classLoader;
	Class<?>[] interfaceClasses;
	private InvocationHandler handler;
	
	
	public ProxyFactory(ClassLoader classLoader, Class<?>[] interfaceClasses,
			InvocationHandler handler) {
		super();
		this.classLoader = classLoader;
		this.interfaceClasses = interfaceClasses;
		this.handler = handler;
	}


	@SuppressWarnings("unchecked")
    public <T> T createProxyInstance() {
		return (T) Proxy.newProxyInstance(classLoader, interfaceClasses, handler);
	}

}
