//package com.itany.hibernate;
//
//import org.hibernate.LockMode;
//import org.hibernate.Session;
//
//import com.itany.frame.entity.User1;
//import com.itany.frame.utils.HibernateUtils;
//
//import junit.framework.TestCase;
//
//public class PessimisticLockTest extends TestCase{
//
//	public void testQuery1(){
//		//定义session(类似于jdbc中的connection)
//		Session session=null;
//		
//		try {
//			//通过session工厂创建session对象
//			session=HibernateUtils.getSession();
//			//开启事务
//			session.beginTransaction();
//		    //加上update锁		
//			User1 u1 = (User1)session.load
//			(User1.class, "402886884d758c57014d758c584c0001",LockMode.UPGRADE);
//			System.out.println("user.name="+u1.getName());
//			System.out.println("user.password="+u1.getPassword());
//			System.out.println("user.createTime="+u1.getCreateTime());
//			//持久化状态的值发生任何改变，在提交事务之前都会更新数据库,
//			//同时更新版本号
//			//所以不需再调用update语句，原因：持久化状态下的值处于session管理下
//			u1.setPassword("789");
//			//session.update(u1);
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
//}
