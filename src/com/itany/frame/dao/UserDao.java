package com.itany.frame.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.itany.frame.entity.User1;
import com.itany.frame.spring.ioc.anotation.Autowired;
import com.itany.frame.spring.ioc.anotation.Component;
import com.itany.frame.spring.ioc.anotation.Resource;

@Component
public class UserDao implements IUserDao {
	
//	@Resource(name = "sessionFactory")
	@Autowired
	SessionFactory sessionFactory;
	
	public UserDao(){
		
	}
	
//	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User1 getUserInfo(){
		return null;
		
	}

	@Override
	public List<?> getAllUserInfo(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User1 u");
		List<?> list = query.list();
		return list;
		
	}

	@Override
	public void saveUser(User1 user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User1 user) {
		// TODO Auto-generated method stub
		
	}

}
