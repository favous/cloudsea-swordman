package spring;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import com.itany.frame.action.UserAction;
import com.itany.frame.entity.User1;
import com.itany.frame.spring.ioc.AnotationConfigApplicatinContext;

public class TestIoc {

	public static void main(String[] args) throws Exception {
		
        URL url = TestIoc.class.getClassLoader().getResource("spring-process.xml"); 
        String path = TestIoc.class.getResource("/com").getPath();
        URL[] urls = new URL[]{url};
        File[] files = new File[]{new File(path)};        
        AnotationConfigApplicatinContext context = AnotationConfigApplicatinContext.init(urls, files);

        Object bean = context.getSingletonMap().get("userService");
        UserAction bean2 = (UserAction) context.getSingletonMap().get("userAction");
        User1 info = bean2.getUserInfo();
        List<?> all = bean2.getAllUserInfo();
        
        System.out.println(bean);
        for (Entry<String, Object> m : context.getSingletonMap().entrySet()){
        	System.out.println(m.getKey() + "\t" + m.getValue());
        }
        for (Entry<Class<?>, Object> m : context.getSingletonClassMap().entrySet()){
        	System.out.println(m.getKey() + "\t" + m.getValue());
        }
       
	}
}
