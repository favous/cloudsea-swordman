/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.NoticePublishListener;
import com.cloudsea.sys.module.bpm.observers.Observer;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class DeptleaderAuditPassEventSource extends BaseEventSource {

	public DeptleaderAuditPassEventSource(List<Observer> listenerList) {
		super(listenerList);
		// TODO Auto-generated constructor stub
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		NoticePublishListener noticePublishListener = new NoticePublishListener();
//		this.registerListener(noticePublishListener);
//	}
	

}
