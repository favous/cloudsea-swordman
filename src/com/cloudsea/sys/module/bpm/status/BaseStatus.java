/**
 * 
 */
package com.cloudsea.sys.module.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.cloudsea.sys.module.bpm.Process;
import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
import com.cloudsea.sys.module.bpm.exception.IncorrectStatusParamException;
import com.cloudsea.sys.module.bpm.exception.NoneEventSourceException;
import com.cloudsea.sys.module.bpm.observable.Observable;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public abstract class BaseStatus implements Status  {
	
		
	private Process process;
	private Map<String, Observable> eventSourceMap = new HashMap<String, Observable>(); 
	
	public BaseStatus(Process process, Map<String, Observable> eventSourceMap){
		this.process = process;
		this.eventSourceMap = eventSourceMap;
//		registerEventSources();
	}
	
	
	public void registerEventSource(String name, Observable eventSource){
		this.eventSourceMap.put(name, eventSource);
	}
	
	public Observable getEventSource(String name){
		return this.eventSourceMap.get(name);
	}
	
	@Override
	public void auditPass(EntityInvocation entityInvocation, String nextStatus) 
			throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException {
		executeAndUpdateStatus(entityInvocation, nextStatus);	
	}


	@Override
	public void write(EntityInvocation entityInvocation, String nextStatus) 
			throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException {
		executeAndUpdateStatus(entityInvocation, nextStatus);	
	}

	
	@Override
	public void auditBack(EntityInvocation entityInvocation, String nextStatus) 
			throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException {
		executeAndUpdateStatus(entityInvocation, nextStatus);	
	}

	
	@Override
	public void publish(EntityInvocation entityInvocation, String nextStatus) 
			throws EventSourceExcuteException, NoneEventSourceException, 
			IncorrectStatusParamException {
		executeAndUpdateStatus(entityInvocation, nextStatus);			
	}
	
	
	private String getMethodName(){
		StackTraceElement ste = new Throwable().getStackTrace()[2];
		String methodName = ste.getMethodName();
		return methodName;
	}

	private void executeAndUpdateStatus(EntityInvocation entityInvocation, 
			String nextStatus) throws EventSourceExcuteException, 
			NoneEventSourceException, IncorrectStatusParamException{
		
		//状态方法执行，触发事件源的通知功能
		Observable eventSource = eventSourceMap.get(getMethodName());
		if (eventSource == null)
			throw new NoneEventSourceException();
		eventSource.notifyExcute(entityInvocation);
		
		//如果状态方法执行时，传入有效的状态参数，就更新状态
		Map<String, Status> statusMap = process.getStatusMap();
		if (nextStatus != null && !nextStatus.trim().equals("")){
			for (Entry<String, Status> entry : statusMap.entrySet()){
				if (nextStatus.trim().equals(entry.getKey().trim())){
					updateStatus(entityInvocation, entry.getValue());
					return;
				}
			}
			
			//如果找不到状态参数nextStatus所对应的状态对象，说明状态参数nextStatus不正确
			throw new IncorrectStatusParamException();
		}
	}
	
	/**
	 * 状态对象默认的执行更新的方法，如果需要修改方法内容，子类可以重写
	 * @param entityInvocation
	 * @param Status
	 */
	public void updateStatus(EntityInvocation entityInvocation,Status Status){
		process.setCurrentStatus(Status);
	}
	
//	/**
//	 * 给此状态的需要的方法注册事件源，由子类来实现
//	 */
//	public abstract void registerEventSources();
	
	
}
