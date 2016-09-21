package com.itany.frame.spring.aop.definition;

import java.util.ArrayList;
import java.util.List;

import com.itany.frame.spring.aop.definition.aspect.AopAfter;
import com.itany.frame.spring.aop.definition.aspect.AopAfterReturn;
import com.itany.frame.spring.aop.definition.aspect.AopAfterThrow;
import com.itany.frame.spring.aop.definition.aspect.AopAround;
import com.itany.frame.spring.aop.definition.aspect.AopBefore;
import com.itany.frame.spring.aop.definition.aspect.AopDeclareParents;

public class AopAspect {

	private String id;
	private String ref;
	private String order;
	List<AopAfter> afterList = new ArrayList<AopAfter>();
	List<AopAfterReturn> afterReturnList = new ArrayList<AopAfterReturn>();
	List<AopAfterThrow> afterThrowList = new ArrayList<AopAfterThrow>();
	List<AopAround> aroundList = new ArrayList<AopAround>();
	List<AopBefore> beforeList = new ArrayList<AopBefore>();
	List<AopDeclareParents> declareParentsList = new ArrayList<AopDeclareParents>();
	List<AopPointcut> pointcutList = new ArrayList<AopPointcut>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List<AopAfter> getAfterList() {
		return afterList;
	}
	public void setAfterList(List<AopAfter> afterList) {
		this.afterList = afterList;
	}
	public List<AopAfterReturn> getAfterReturnList() {
		return afterReturnList;
	}
	public void setAfterReturnList(List<AopAfterReturn> afterReturnList) {
		this.afterReturnList = afterReturnList;
	}
	public List<AopAfterThrow> getAfterThrowList() {
		return afterThrowList;
	}
	public void setAfterThrowList(List<AopAfterThrow> afterThrowList) {
		this.afterThrowList = afterThrowList;
	}
	public List<AopAround> getAroundList() {
		return aroundList;
	}
	public void setAroundList(List<AopAround> aroundList) {
		this.aroundList = aroundList;
	}
	public List<AopBefore> getBeforeList() {
		return beforeList;
	}
	public void setBeforeList(List<AopBefore> beforeList) {
		this.beforeList = beforeList;
	}
	public List<AopDeclareParents> getDeclareParentsList() {
		return declareParentsList;
	}
	public void setDeclareParentsList(List<AopDeclareParents> declareParentsList) {
		this.declareParentsList = declareParentsList;
	}
	public List<AopPointcut> getPointcutList() {
		return pointcutList;
	}
	public void setPointcutList(List<AopPointcut> pointcutList) {
		this.pointcutList = pointcutList;
	}
	
}
