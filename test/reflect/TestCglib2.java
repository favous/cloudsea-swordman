package reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import net.sf.cglib.proxy.Callback;

import com.itany.frame.service.IUserService;
import com.itany.frame.service.UserService;
import com.itany.frame.spring.aop.proxy.CglibFactory;
import com.itany.frame.spring.aop.proxy.CglibInterceptorImpl;

public class TestCglib2 {
	
	public static void main(String[] args) throws Exception {

        IUserService targetBean = new UserService();

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
				System.out.println("0000");
				try {
					Object o = invocation.proceed();
					System.out.println("545454345454");
					return o;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("77777");
					return null;
				}
			}
		};
		
		MethodInterceptor mi3 = new MethodInterceptor(){
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
		Method method = UserService.class.getMethod("getAllUserInfo");
		List<MethodInterceptor> interceptors = Arrays.asList(new MethodInterceptor[]{mi1, mi2, mi3});
		LinkedHashMap.put(method, interceptors);
		Callback callback = new CglibInterceptorImpl(targetBean, LinkedHashMap);

		CglibFactory factory = new CglibFactory(UserService.class, callback);
		UserService UserService = factory.createCglibInstance();
		UserService.getAllUserInfo();
		
		
	}

}
