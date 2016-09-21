package reflect;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.itany.frame.service.UserService;

public class TestCglib1 {
	
	public static void main(String[] args) {
		
		MethodInterceptor mi = new MethodInterceptor(){

			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy methodProxy) throws Throwable {
				return methodProxy.invokeSuper(new UserService(){}, args);
			}
			
		};
		Enhancer en = new Enhancer();
		en.setSuperclass(UserService.class);
		en.setCallbacks(new Callback[]{mi});
		UserService obj = (UserService) en.create();
		obj.getAllUserInfo();
		
//		MethodInterceptor mi2 = new MethodInterceptor(){
//
//			@Override
//			public Object intercept(Object obj, Method method, Object[] args,
//					MethodProxy methodProxy) throws Throwable {
//				return methodProxy.invokeSuper(obj, args);
//			}
//			
//		};
//		Enhancer en2 = new Enhancer();
//		en2.setSuperclass(obj.getClass());
//		System.out.println(obj.getClass().getName());
//		en2.setCallbacks(new Callback[]{mi2});
//		UserService obj2 = (UserService) en2.create();
//		obj2.getAllUserInfo();
		
		
		
	}

}
