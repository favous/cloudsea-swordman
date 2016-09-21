package com.itany.frame.spring.ioc;

public interface FactoryBean<T> {

	T getObject() throws Exception;

	
	Class<?> getObjectType();

	
	boolean isSingleton();
}
