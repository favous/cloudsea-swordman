/**
 * 
 */
package com.cloudsea.sys.module.bpm.observable;

import java.util.List;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.observers.NoticeAuditListener;
import com.cloudsea.sys.module.bpm.observers.Observer;
import com.cloudsea.sys.module.bpm.observers.UpdateArticleListener;
import com.cloudsea.sys.module.bpm.observers.WriteArticleListener;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class WriterRoomWriteEventSource extends BaseEventSource {

	public WriterRoomWriteEventSource(List<Observer> listenerList) {
		super(listenerList);
		// TODO Auto-generated constructor stub
	}

//	/* (non-Javadoc)
//	 * @see com.redstoneinfo.stateflow.observable.BaseEventSource#registerListeners()
//	 */
//	@Override
//	public void registerListeners() {
//		WriteArticleListener writeArticleListener = new WriteArticleListener();
//		NoticeAuditListener noticeAuditListener = new NoticeAuditListener();
//		UpdateArticleListener updateArticleListener = new UpdateArticleListener();
//		
//		this.registerListener(writeArticleListener);
//		this.registerListener(noticeAuditListener);
//		this.registerListener(updateArticleListener);
//	}
	

}