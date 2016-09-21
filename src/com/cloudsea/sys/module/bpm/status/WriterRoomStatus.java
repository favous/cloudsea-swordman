/**
 * 
 */
package com.cloudsea.sys.module.bpm.status;

import java.util.Map;

import com.cloudsea.sys.module.bpm.Process;
import com.cloudsea.sys.module.bpm.observable.Observable;
import com.cloudsea.sys.module.bpm.observable.WriterRoomWriteEventSource;
import com.itany.frame.spring.ioc.anotation.Component;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class WriterRoomStatus extends BaseStatus{

	/**
	 * @param processContext
	 * @param eventMap
	 */
	public WriterRoomStatus(Process process, Map<String, Observable> eventSourceMap) {
		super(process, eventSourceMap);
	}

	
//	@Override
//	public void registerEventSources() {
//		WriterRoomWriteEventSource writerRoomWriteEventSource = new WriterRoomWriteEventSource();
//		super.registerEventSource("write", writerRoomWriteEventSource);
//	}
	

}
