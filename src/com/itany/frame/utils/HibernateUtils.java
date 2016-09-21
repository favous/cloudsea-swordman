package com.itany.frame.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * hibernate工具类，用于封装最基础的hibernate操作
 * @author Administrator
 *
 */
public class HibernateUtils {

	private  static SessionFactory factory;
	
	static{
		try {
			//根据hibernate.cfg.xml文件生成Configuration对象
			Configuration cfg= new Configuration().configure();
			//创建session工厂
			factory=cfg.buildSessionFactory();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private  HibernateUtils() {
		// TODO Auto-generated constructor stub
	}

	public static SessionFactory getFactory() {
		return factory;
	}
	
	public static Session getSession(){
		return factory.openSession();
	}
	
	public static void closeSession(Session session){
		if(session !=null){
			if(session.isOpen()){
				session.close();
			}
		}
	}
	
	
}
