package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.itany.frame.entity.User1;
import com.itany.frame.service.IUserService;
import com.itany.frame.service.UserService;
import com.itany.frame.spring.aop.proxy.ProxyFactory;

public class TestProxy {
	
	public static void main(String[] args) {
		
		final IUserService targetBean = new UserService();
		ClassLoader classLoader = IUserService.class.getClassLoader();
		Class<?>[] interfaceClasses = new Class<?>[]{IUserService.class};
		
		InvocationHandler txHandle = new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return method.invoke(targetBean, args);
			}
		};
		
		ProxyFactory factory = new ProxyFactory(classLoader, interfaceClasses, txHandle);
		final IUserService txProxy = (IUserService) factory.createProxyInstance();
		System.out.println(txProxy);
		
		InvocationHandler logHandle = new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return method.invoke(txProxy, args);
			}
		};
		
		ProxyFactory logFactory = new ProxyFactory(classLoader, interfaceClasses, logHandle);
		final IUserService logProxy = (IUserService) logFactory.createProxyInstance();
		System.out.println(logProxy);
		
		User1 user = new User1();
		user.setName("name");
		user.setPassword("pwd");
		
		logProxy.saveUser(user);
		System.out.println();
	}

}
