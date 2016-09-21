package com.cloudsea.sys.module.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import org.springframework.beans.factory.FactoryBean;


/**
 * 线程池工厂类
 * @author Administrator
 *
 */
//public class ThreadPoolFactory implements FactoryBean<ThreadPoolExecutor>{
public class ThreadPoolFactory {
	
	private ThreadPoolConfig config;

//	@Override
	public ThreadPoolExecutor getObject() throws Exception {
		ThreadPoolExecutor pool = null;
		
		int corePoolSize = config.getCorePoolSize();
		int maximumPoolSize = config.getMaximumPoolSize();
		long keepAliveTime = config.getKeepAliveTime();
		int queueSize = config.getQueueSize();
		TimeUnit unit = config.getUnit();
		ThreadFactory threadFactory = config.getThreadFactory();
		RejectedExecutionHandler handler = config.getRejectedExecutionHandler();
		BlockingQueue<Runnable> queue = config.getWorkQueue();
		
		if(queue == null)
			queue = new ArrayBlockingQueue<Runnable>(queueSize);
			
		if(handler == null){
			if (threadFactory == null)
	            pool = new ThreadPoolExecutor(corePoolSize,
	            		maximumPoolSize, keepAliveTime, unit, queue);
			else
				pool = new ThreadPoolExecutor(corePoolSize,
						maximumPoolSize, keepAliveTime, unit, queue, threadFactory);
				
        }else{
        	if (threadFactory == null)
	            pool = new ThreadPoolExecutor(corePoolSize,
	            		maximumPoolSize, keepAliveTime, unit, queue, handler);
        	else
        		pool = new ThreadPoolExecutor(corePoolSize,
        				maximumPoolSize,keepAliveTime, unit, queue, threadFactory, handler);
        }
		return pool;
	}

//	@Override
	public Class<?> getObjectType() {
		return ThreadPoolExecutor.class;
	}

//	@Override
	public boolean isSingleton() {
		return true;
	}

	public ThreadPoolConfig getConfig() {
		return config;
	}

	public void setConfig(ThreadPoolConfig config) {
		this.config = config;
	}
    
}
