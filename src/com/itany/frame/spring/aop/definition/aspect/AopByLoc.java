package com.itany.frame.spring.aop.definition.aspect;

public class AopByLoc {

	private String method;
	private String argNames;
	private String pointcutRef;
	private String pointcut;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getArgNames() {
		return argNames;
	}
	public void setArgNames(String argNames) {
		this.argNames = argNames;
	}
	public String getPointcutRef() {
		return pointcutRef;
	}
	public void setPointcutRef(String pointcutRef) {
		this.pointcutRef = pointcutRef;
	}
	public String getPointcut() {
		return pointcut;
	}
	public void setPointcut(String pointcut) {
		this.pointcut = pointcut;
	}
	
}
