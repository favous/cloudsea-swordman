package com.cloudsea.sys.module.bpm.domain;

public class User {
	
	String userName; // 用户姓名

	String password;//密码
	
	String userSex; // 用户性别

	public User() {
		super();
	}

	public User(String userName, String password, String userSex) {
		super();
		this.userName = userName;
		this.password = password;
		this.userSex = userSex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	
	
}
