/**
 * 
 */
package com.cloudsea.sys.module.bpm.domain;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class AuditState {
	//简单的设值：1已起草，2办公室审核通过，2办审核退回，3领导审通过，4领导审核退回，5发行
	private Integer value;
	private String returnResean;
	private User returnUser;
	private Integer prepValue ;
	
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getReturnResean() {
		return returnResean;
	}
	public void setReturnResean(String returnResean) {
		this.returnResean = returnResean;
	}
	public Integer getPrepValue() {
		return prepValue;
	}
	public void setPrepValue(Integer prepValue) {
		this.prepValue = prepValue;
	}
	public User getReturnUser() {
		return returnUser;
	}
	public void setReturnUser(User returnUser) {
		this.returnUser = returnUser;
	}
	
	
}
