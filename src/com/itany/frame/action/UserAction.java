package com.itany.frame.action;

import java.util.List;

import com.itany.frame.entity.User1;
import com.itany.frame.service.IUserService;
import com.itany.frame.spring.ioc.anotation.Autowired;
import com.itany.frame.spring.ioc.anotation.Component;

@Component
public class UserAction {

	@Autowired
	private IUserService userService;

	public User1 getUserInfo() {
		return userService.getUserInfo();
	}

	public void saveUser(User1 user) {
		userService.saveUser(user);
	}

	public void updateUser(User1 user) {
		userService.updateUser(user);
	}
	
	public List<?> getAllUserInfo(){
		return userService.getAllUserInfo();
	}

}
