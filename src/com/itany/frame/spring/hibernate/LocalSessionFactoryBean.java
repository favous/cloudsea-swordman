package com.itany.frame.spring.hibernate;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cfg.Configuration;

import com.itany.frame.spring.beans.InitializingBean;
import com.itany.frame.spring.ioc.FactoryBean;
import com.itany.frame.spring.ioc.anotation.Component;


@Component("sessionFactory")
public class LocalSessionFactoryBean implements FactoryBean<SessionFactory>, InitializingBean {

	private DataSource dataSource;
	private Properties hibernateProperties;
	private Class<?>[] annotatedClasses;
	private String[] annotatedPackages;
	private String[] packagesToScan;
//	private ResourcePatternResolver resourcePatternResolver;
	private Configuration configuration;
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getObject() throws Exception {
		return this.sessionFactory;
	}

	@Override
	public Class<?> getObjectType() {
	    return this.sessionFactory == null ? SessionFactory.class : this.sessionFactory.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
		if (sfb != null)
			return sfb.buildSessionFactory();
		
		Configuration cfg= new Configuration().configure();
		return cfg.buildSessionFactory();

	}

	@Override
	public void afterPropertiesSet() throws Exception {
	    this.sessionFactory = buildSessionFactory(null);
	}

}
