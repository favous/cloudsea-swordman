/**
 * 
 */
package com.cloudsea.sys.module.bpm.status;

import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
import com.cloudsea.sys.module.bpm.exception.IncorrectStatusParamException;
import com.cloudsea.sys.module.bpm.exception.NoneEventSourceException;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public interface Status {

	/**
	 * @throws EventSourceExcuteException 
	 * 
	 */
	void write(EntityInvocation entityInvocation, String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException;
	
	void auditPass(EntityInvocation entityInvocation, String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException;

	void auditBack(EntityInvocation entityInvocation, String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException;
	
	void publish(EntityInvocation entityInvocation, String nextStatus) throws EventSourceExcuteException, NoneEventSourceException, IncorrectStatusParamException;
	
}
