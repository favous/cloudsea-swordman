/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
import com.cloudsea.sys.module.bpm.observers.Observer;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public interface Observable {
	
	void registerListener(Observer Observer);
	
	void notifyExcute(EntityInvocation entityInvocation) throws EventSourceExcuteException;
}
