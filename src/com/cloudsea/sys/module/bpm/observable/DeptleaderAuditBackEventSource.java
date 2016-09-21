/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.NoticeModifyListener;
import com.cloudsea.sys.module.bpm.observers.Observer;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class DeptleaderAuditBackEventSource extends BaseEventSource {

	public DeptleaderAuditBackEventSource(List<Observer> listenerList) {
		super(listenerList);
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		NoticeModifyListener noticeModifyListener = new NoticeModifyListener();
//		this.registerListener(noticeModifyListener);
//	}
	

}
