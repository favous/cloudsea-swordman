package com.itany.frame.spring.aop.definition;

import java.util.ArrayList;
import java.util.List;

public class AopAspectjAutoproxyDefinition {
	
	List<AopAdvisor> advisorList = new ArrayList<AopAdvisor>();
	List<AopAspect> aspectList = new ArrayList<AopAspect>();
	List<AopPointcut> pointcutList = new ArrayList<AopPointcut>();
	
	public List<AopAdvisor> getAdvisorList() {
		return advisorList;
	}
	public void setAdvisorList(List<AopAdvisor> advisorList) {
		this.advisorList = advisorList;
	}
	public List<AopAspect> getAspectList() {
		return aspectList;
	}
	public void setAspectList(List<AopAspect> aspectList) {
		this.aspectList = aspectList;
	}
	public List<AopPointcut> getPointcutList() {
		return pointcutList;
	}
	public void setPointcutList(List<AopPointcut> pointcutList) {
		this.pointcutList = pointcutList;
	}
	

}
