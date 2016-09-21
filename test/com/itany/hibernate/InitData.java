//package com.itany.hibernate;
//
//import java.util.Date;
//
//import org.hibernate.Session;
//
//import com.itany.frame.entity.User1;
//import com.itany.frame.utils.HibernateUtils;
//
//public class InitData {
//	
//	public static void main(String[] args) {
//		//定义session(类似于jdbc中的connection)
//		Session session=null;
//		
//		try {
//			//通过session工厂创建session对象
//			session=HibernateUtils.getSession();
//			//开启事务
//			session.beginTransaction();
//		
//			User1 u = new User1();
//			u.setName("张三");
//			u.setPassword("123");
//			u.setCreateTime(new Date());
//			u.setExpireTime(new Date());
//			session.save(u);
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
//	}
//
//}
