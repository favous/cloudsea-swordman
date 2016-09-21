package com.itany.frame.service;

import java.util.List;

import com.itany.frame.entity.User1;

public interface IUserService {

	User1 getUserInfo();

	void saveUser(User1 user);

	void updateUser(User1 user);

	List<?> getAllUserInfo();

}