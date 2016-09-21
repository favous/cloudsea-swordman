/**
 * 
 */
package com.cloudsea.sys.module.bpm.status;

import java.util.Map;

import com.cloudsea.sys.module.bpm.Process;
import com.cloudsea.sys.module.bpm.observable.Observable;
import com.cloudsea.sys.module.bpm.observable.OfficeAuditBackEventSource;
import com.cloudsea.sys.module.bpm.observable.OfficeAuditPassEventSource;
import com.cloudsea.sys.module.bpm.observable.OfficePublishEventSource;
import com.itany.frame.spring.ioc.anotation.Component;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class OfficeStatus extends BaseStatus{

	/**
	 * @param processContext
	 * @param eventMap
	 */
	public OfficeStatus(Process process, Map<String, Observable> eventSourceMap) {
		super(process, eventSourceMap);
	}

	
//	@Override
//	public void registerEventSources() {
//		OfficeAuditPassEventSource officeAuditPassEvent = new OfficeAuditPassEventSource();
//		OfficeAuditBackEventSource officeAuditBackEventSource = new OfficeAuditBackEventSource();
//		OfficePublishEventSource OfficePublishEventSource = new OfficePublishEventSource();
//		
//		super.registerEventSource("auditPass", officeAuditPassEvent);
//		super.registerEventSource("auditBack", officeAuditBackEventSource);
//		super.registerEventSource("publish", OfficePublishEventSource);
//	}
//	


}
