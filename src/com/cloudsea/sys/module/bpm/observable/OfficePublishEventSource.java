/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.Observer;
import com.cloudsea.sys.module.bpm.observers.PrintArticleListener;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class OfficePublishEventSource extends BaseEventSource {

	public OfficePublishEventSource(List<Observer> listenerList) {
		super(listenerList);
		// TODO Auto-generated constructor stub
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		PrintArticleListener printArticleListener = new PrintArticleListener();
//		this.registerListener(printArticleListener);
//	}
	

}
