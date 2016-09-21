package com.cloudsea.sys.utils.expand.test;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.cloudsea.sys.utils.expand.anotation.Table;
import com.cloudsea.sys.utils.expand.anotation.TableColumn;
import com.cloudsea.sys.utils.expand.anotation.TableId;


@Table(tableName="sm_user")		
public class User{
	
	private static final long serialVersionUID = -5976474144608010507L;

	@TableId(idName = "id", sequenceName = "SEQ_SYS_BASE")
	private Long id; //用户编号
	
	@TableColumn(columnName="user_No")
	private String userNo; //用户编号
	
	@TableColumn(columnName="login_Name")
	private String loginName;//登录�?
	
	@TableColumn(columnName="password")
	private String password;//密码
	
	@TableColumn(columnName="user_Name")
	private String userName;//用户姓名
	
	@TableColumn(columnName="status")
	private Integer status;//状�? 1：正�?    2：注�?

	@TableColumn(columnName="create_Time")
	private Date createTime;//创建时间
	
	private Set<Object> regions=new HashSet<Object>();//区域

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<Object> getRegions() {
		return regions;
	}

	public void setRegions(Set<Object> regions) {
		this.regions = regions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
