package com.itany.frame.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class User1 {
	
	
	private String id;
	
	private String name;
	
	private String password;
	
	private Date  createTime;
	
	private Date  expireTime;
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void print() throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields){
			Object val = field.get(this);
			System.out.println(val);
		}
		
	}


}
