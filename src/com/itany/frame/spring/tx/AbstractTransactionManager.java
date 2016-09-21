package com.itany.frame.spring.tx;

import org.aopalliance.intercept.MethodInterceptor;
import org.hibernate.SessionFactory;


public abstract class AbstractTransactionManager implements TransactionManager{
	
	private SessionFactory sessionFactory;
	MethodInterceptor interceptor;
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MethodInterceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(MethodInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	
}
