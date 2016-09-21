package com.itany.frame.service;

import java.util.List;

import com.itany.frame.dao.IUserDao;
import com.itany.frame.entity.User1;
import com.itany.frame.spring.ioc.anotation.Autowired;
import com.itany.frame.spring.ioc.anotation.Service;
import com.itany.frame.spring.ioc.anotation.Transactional;

@Transactional
@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Override
	public User1 getUserInfo(){
		return userDao.getUserInfo();		
	}

	@Override
	public void saveUser(User1 user) {
		userDao.saveUser(user);		
	}

	@Override
	public void updateUser(User1 user) {
		userDao.updateUser(user);	
	}

	@Override
	public List<?> getAllUserInfo(){
		return userDao.getAllUserInfo();
	}
}
