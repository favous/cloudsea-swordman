package com.itany.frame.dao;

import java.util.List;

import com.itany.frame.entity.User1;

public interface IUserDao {

	User1 getUserInfo();

	void saveUser(User1 user);

	void updateUser(User1 user);

	List<?> getAllUserInfo();

}