package com.itany.frame.spring.aop.definition;

public class AopAdvisor {
	
	private String id;
	private String pointcut;
	private String pointcutRef;
	private String adviceRef;
	private String order;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPointcutRef() {
		return pointcutRef;
	}
	public void setPointcutRef(String pointcutRef) {
		this.pointcutRef = pointcutRef;
	}
	public String getAdviceRef() {
		return adviceRef;
	}
	public void setAdviceRef(String adviceRef) {
		this.adviceRef = adviceRef;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getPointcut() {
		return pointcut;
	}
	public void setPointcut(String pointcut) {
		this.pointcut = pointcut;
	}

}
