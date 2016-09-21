/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.ArrayList;
import java.util.List;

import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
import com.cloudsea.sys.module.bpm.observers.Observer;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public abstract class BaseEventSource implements Observable {

	private List<Observer> listenerList = new ArrayList<Observer>();
	
	public BaseEventSource(List<Observer> listenerList){
		this.listenerList = listenerList;
//		registerListeners();
	}

	@Override
	public void registerListener(Observer Observer) {
		listenerList.add(Observer);
	}

//	public abstract void registerListeners();
	
	@Override
	public void notifyExcute(EntityInvocation entityInvocation) throws EventSourceExcuteException {
		try {
			for (Observer obs : listenerList)
				obs.action(entityInvocation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EventSourceExcuteException();
		}
	}

}