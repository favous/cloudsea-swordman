package spring;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ThreadLocalSessionContext;

import com.itany.frame.entity.User1;
import com.itany.frame.service.UserService;
import com.itany.frame.spring.ioc.AnotationConfigApplicatinContext;

public class TestTx {

	public static void main(String[] args) throws Exception {
		
        URL url = TestTx.class.getClassLoader().getResource("spring-process.xml"); 
        String path = TestTx.class.getResource("/com").getPath();
        URL[] urls = new URL[]{url};
        File[] files = new File[]{new File(path)};        
        AnotationConfigApplicatinContext context = AnotationConfigApplicatinContext.init(urls, files);

        System.out.println(context);
        for (Entry<String, Object> m : context.getSingletonMap().entrySet()){
        	System.out.println(m.getKey() + "\t" + m.getValue());
        }
        for (Entry<Class<?>, Object> m : context.getSingletonClassMap().entrySet()){
        	System.out.println(m.getKey() + "\t" + m.getValue());
        }
        
        List<?> users = null;
        
//        >>>>>>>>>>>>>>>可以嵌入事务代理层（注入UserService的事务代理对象）
        SessionFactory sessionFactory = (SessionFactory) context.getSingletonMap().get("sessionFactory");
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        ThreadLocalSessionContext.bind(session);
        
        try {
        	UserService userService = (UserService) context.getSingletonMap().get("userService");
        	users = userService.getAllUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
        tx.commit();
        session.close();
//      <<<<<<<<<<<<<<<<<<<<<<<<<<<
        
        for (Object o : users){
        	User1 user = (User1) o;
        	System.out.println(user.getName());
        	System.out.println(user.getPassword());
        	System.out.println(user.getCreateTime());
        	System.out.println(user.getExpireTime());
        }
	}
}
