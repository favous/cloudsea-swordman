package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxyFactory implements InvocationHandler{
	
	private Object targetBean;
	private Class<?> targetClass;
	
	public Object createProxyInstance(Object bean) {
		this.targetBean = bean;
		this.targetClass = targetBean.getClass();
		Class<?> proxyClass = Proxy.getProxyClass(targetClass.getClassLoader(), targetClass.getInterfaces());
		try {
			return proxyClass.getConstructor(
					new Class[] { InvocationHandler.class }).newInstance(
					new Object[] { this });
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), this);

	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		targetClass.cast(proxy);
		return method.invoke(targetBean, args);
	}

}
