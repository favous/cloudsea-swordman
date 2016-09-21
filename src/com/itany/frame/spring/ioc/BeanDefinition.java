package com.itany.frame.spring.ioc;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
	
	/**  bean标签的class属性. */
	private String className;
	
	/** bean标签的id属性. */
	private String id;
	
	/** bean标签的子元素property集合. */
	private List<PropertyDefinition> PropertyList = new ArrayList<PropertyDefinition>();

	private List<PropertyClassRefDefinition> PropertyClassList = new ArrayList<PropertyClassRefDefinition>();

	/** bean标签的子元素property集合. */
	private List<PropertyDefinition> constructorArgList = new ArrayList<PropertyDefinition>();

	/** bean标签的子元素property集合. */
	private List<PropertyClassRefDefinition> constructorClassRefArgList = new ArrayList<PropertyClassRefDefinition>();
	
	/** bean是否被读过 */
	private boolean read;

	
	public BeanDefinition(String id, String className) {
		this.className = className;
		this.id = id;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<PropertyDefinition> getPropertyList() {
		return PropertyList;
	}


	public void setPropertyList(List<PropertyDefinition> propertyList) {
		PropertyList = propertyList;
	}


	public List<PropertyClassRefDefinition> getPropertyClassList() {
		return PropertyClassList;
	}


	public void setPropertyClassList(
			List<PropertyClassRefDefinition> propertyClassList) {
		PropertyClassList = propertyClassList;
	}


	public List<PropertyDefinition> getConstructorArgList() {
		return constructorArgList;
	}


	public void setConstructorArgList(List<PropertyDefinition> constructorArgList) {
		this.constructorArgList = constructorArgList;
	}


	public List<PropertyClassRefDefinition> getConstructorClassRefArgList() {
		return constructorClassRefArgList;
	}


	public void setConstructorClassRefArgList(
			List<PropertyClassRefDefinition> constructorClassRefArgList) {
		this.constructorClassRefArgList = constructorClassRefArgList;
	}


	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}


}
