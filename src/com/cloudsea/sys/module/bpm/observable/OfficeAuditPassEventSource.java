/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.NoticeAuditListener;
import com.cloudsea.sys.module.bpm.observers.Observer;
import com.cloudsea.sys.module.bpm.observers.OfficeAuditPassListener;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class OfficeAuditPassEventSource extends BaseEventSource {

	public OfficeAuditPassEventSource(List<Observer> listenerList) {
		super(listenerList);
		// TODO Auto-generated constructor stub
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		OfficeAuditPassListener officeAuditPassListener = new OfficeAuditPassListener();
//		NoticeAuditListener noticeAuditListener = new NoticeAuditListener();
//		
//		this.registerListener(officeAuditPassListener);
//		this.registerListener(noticeAuditListener);
//	}
	

}
