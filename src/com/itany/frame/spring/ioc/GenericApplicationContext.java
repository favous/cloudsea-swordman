package com.itany.frame.spring.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.itany.frame.spring.aop.AopContext;


public class GenericApplicationContext {

	 /** bean定义集合 */  
    private List<BeanDefinition> beanDefineList = new ArrayList<BeanDefinition>();  
    
    /** bean实例 集合*/  
    private Map<String, Object> singletonMap = new HashMap<String, Object>();

    /** bean实例 集合*/  
    private Map<Class<?>, Object> singletonClassMap = new HashMap<Class<?>, Object>();
    
    private AopContext aopContext;
    
    /** bean代理 集合*/  
    private Map<String, Object> proxyIdMap = new HashMap<String, Object>();

    /** bean代理 集合*/  
    private Map<Class<?>, Object> proxyClassMap = new HashMap<Class<?>, Object>();

	public GenericApplicationContext() throws Exception {
		
	}
	
	public void buildClassMap(){
		for (Entry<String, Object> entry : singletonMap.entrySet()){
			singletonClassMap.put(entry.getValue().getClass(), entry.getValue());
		}
	}
	
	public List<BeanDefinition> getBeanDefineList() {
		return beanDefineList;
	}

	public void setBeanDefineList(List<BeanDefinition> beanDefineList) {
		this.beanDefineList = beanDefineList;
	}

	public Map<String, Object> getSingletonMap() {
		return singletonMap;
	}

	public void setSingletonMap(Map<String, Object> singletonMap) {
		this.singletonMap = singletonMap;
	}

	public Map<Class<?>, Object> getSingletonClassMap() {
		return singletonClassMap;
	}

	public void setSingletonClassMap(Map<Class<?>, Object> singletonClassMap) {
		this.singletonClassMap = singletonClassMap;
	}

	public Map<String, Object> getProxyIdMap() {
		return proxyIdMap;
	}

	public void setProxyIdMap(Map<String, Object> proxyIdMap) {
		this.proxyIdMap = proxyIdMap;
	}

	public Map<Class<?>, Object> getProxyClassMap() {
		return proxyClassMap;
	}

	public void setProxyClassMap(Map<Class<?>, Object> proxyClassMap) {
		this.proxyClassMap = proxyClassMap;
	}

	public AopContext getAopContext() {
		return aopContext;
	}

	public void setAopContext(AopContext aopContext) {
		this.aopContext = aopContext;
	}
	
}
