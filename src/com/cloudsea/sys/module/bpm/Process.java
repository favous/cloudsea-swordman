/**
 * 
 */
package com.cloudsea.sys.module.bpm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
import com.cloudsea.sys.module.bpm.exception.IncorrectStatusParamException;
import com.cloudsea.sys.module.bpm.exception.NoneEventSourceException;
import com.cloudsea.sys.module.bpm.status.DeptleaderStatus;
import com.cloudsea.sys.module.bpm.status.OfficeStatus;
import com.cloudsea.sys.module.bpm.status.Status;
import com.cloudsea.sys.module.bpm.status.WriterRoomStatus;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class Process {
	
	private EntityInvocation entityInvocation;
	private Map<String, Status> statusMap = new HashMap<String, Status>();
	private Status currentStatus;
//	
//	public Process(){
//		initStatuses();
//	}
//
//	/**
//	 * 注册事务流程所据有的所有处理状态
//	 */
//	private void initStatuses() {
//		WriterRoomStatus writerRoomStatus = new WriterRoomStatus(this);
//		OfficeStatus officeStatus = new OfficeStatus(this);
//		DeptleaderStatus deptleaderStatus = new DeptleaderStatus(this);
//		
//		statusMap.put("writerRoomStatus", writerRoomStatus);
//		statusMap.put("OfficeStatus", officeStatus);
//		statusMap.put("DeptleaderStatus", deptleaderStatus);
//		
//		currentStatus = writerRoomStatus;
//	}

	public void execute(String methodName, String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, ReflectiveOperationException {
		Method method = currentStatus.getClass().getMethod(methodName, 
				new Class[]{EntityInvocation.class, String.class});
		method.invoke(currentStatus, new Object[]{entityInvocation, nextStatus}); 
	}	
	
	
	public void write(String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException{
		currentStatus.write(entityInvocation, nextStatus);
	}

	public void auditPass(String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException{
		currentStatus.auditPass(entityInvocation, nextStatus);
	}

	public void auditBack(String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException{
		currentStatus.auditBack(entityInvocation, nextStatus);
	}


	public void publish(String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException{
		currentStatus.publish(entityInvocation, nextStatus);
	}

	
	public EntityInvocation getEntityInvocation() {
		return entityInvocation;
	}

	public void setEntityInvocation(EntityInvocation entityInvocation) {
		this.entityInvocation = entityInvocation;
	}

	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Map<String, Status> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, Status> statusMap) {
		this.statusMap = statusMap;
	}

	 
}
