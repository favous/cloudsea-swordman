package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.itany.frame.entity.User1;
import com.itany.frame.service.IUserService;
import com.itany.frame.service.UserService;
import com.itany.frame.spring.aop.proxy.InvocationHandlerImpl;
import com.itany.frame.spring.aop.proxy.ProxyFactory;

public class TestProxy2 {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
		final IUserService targetBean = new UserService();
		ClassLoader classLoader = IUserService.class.getClassLoader();
		Class<?>[] interfaceClasses = new Class<?>[]{IUserService.class};
		
		MethodInterceptor mi1 = new MethodInterceptor(){
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("2222");
				try {
					Object o = invocation.proceed();
					System.out.println("3333");
					return o;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("5555");
					return null;
				}
			}
		};
		
		MethodInterceptor mi2 = new MethodInterceptor(){
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("11111");
				try {
					Object o = invocation.proceed();
					System.out.println("4444");
					return o;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("6666");
					return null;
				}
			}
		};
		
		LinkedHashMap<Method, List<MethodInterceptor>> LinkedHashMap = new LinkedHashMap<Method, List<MethodInterceptor>>();
		Method method = IUserService.class.getMethod("saveUser", User1.class);
		List<MethodInterceptor> interceptors = Arrays.asList(new MethodInterceptor[]{mi1, mi2});
		LinkedHashMap.put(method, interceptors);
		
		InvocationHandler invocationHandler = new InvocationHandlerImpl(targetBean, LinkedHashMap);
		
		ProxyFactory factory = new ProxyFactory(classLoader, interfaceClasses, invocationHandler);
		final IUserService proxy = factory.createProxyInstance();
		
		User1 user = new User1();
		user.setName("name");
		user.setPassword("pwd");
		proxy.saveUser(user);
		System.out.println();
	}

}
