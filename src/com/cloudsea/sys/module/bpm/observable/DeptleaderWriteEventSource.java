/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.Observer;
import com.cloudsea.sys.module.bpm.observers.UpdateArticleListener;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class DeptleaderWriteEventSource extends BaseEventSource {

	public DeptleaderWriteEventSource(List<Observer> listenerList) {
		super(listenerList);
		// TODO Auto-generated constructor stub
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		UpdateArticleListener updateArticleListener = new UpdateArticleListener();
//		this.registerListener(updateArticleListener);
//	}
	

}