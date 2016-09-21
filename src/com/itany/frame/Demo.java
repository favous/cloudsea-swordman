//package com.itany.frame;
//
//import java.util.Date;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//import com.itany.frame.entity.User1;
//import com.itany.frame.utils.HibernateUtils;
//
//public class Demo {
//	
//	public static void main(String[] args) {
//		
//	
//		//定义session(类似于jdbc中的connection)
//		Session session=null;
//		
//		try {
//			//通过session工厂创建session对象
//			session=HibernateUtils.getSession();
//			//开启事务
//			session.beginTransaction();
//		
//			User1 user1 = (User1) session.get(User1.class, "1");
//			System.out.println(user1);
//		    
//			
//			//提交事务
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			//回滚事务
//			session.getTransaction().rollback();
//		}
//		finally{
//		   HibernateUtils.closeSession(session);
//		}
//		
//		
//	}
//
//}
