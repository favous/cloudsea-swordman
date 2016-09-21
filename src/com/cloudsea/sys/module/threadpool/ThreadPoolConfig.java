package com.cloudsea.sys.module.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置类
 * @author Administrator
 *
 */
public class ThreadPoolConfig {
	
	//池中所保存的线程数，包括空闲线程。
    private int corePoolSize;
    
    //池中允许的最大线程数。
    private int maximumPoolSize;
    
    //当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
    private long keepAliveTime; 
 
    //时间单位名。
    private String timeUnitName;
 
    //任务队列的长度
    private int queueSize;
    
    //执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务。
    private BlockingQueue<Runnable> workQueue;
    
    //用于设置创建线程的工厂
    ThreadFactory threadFactory;
    
	//由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。 
    private RejectedExecutionHandler rejectedExecutionHandler;

    public ThreadFactory getThreadFactory() {
    	if (threadFactory == null) {
			synchronized (this) {
		    	if (threadFactory == null) {		    		
					threadFactory = new ThreadFactory() {
						public Thread newThread(Runnable r) {
							return new Thread(r);
						};
					};
		    	}
			}
		}
    	return threadFactory;
    }
    
    public void setThreadFactory(ThreadFactory threadFactory) {
    	this.threadFactory = threadFactory;
    }
    
	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public TimeUnit getUnit() {
		for (TimeUnit unit : TimeUnit.values()){
			if (unit.name().equalsIgnoreCase(timeUnitName))
				return unit;
		}
		return null;
	}

	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return rejectedExecutionHandler;
	}

	public void setRejectedExecutionHandler(
			RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public String getTimeUnitName() {
		return timeUnitName;
	}

	public void setTimeUnitName(String timeUnitName) {
		this.timeUnitName = timeUnitName;
	}
    
}
