/**
 * 
 */
package com.cloudsea.sys.module.bpm.status;

import java.util.List;
import java.util.Map;

import com.cloudsea.sys.module.bpm.Process;
import com.cloudsea.sys.module.bpm.observable.DeptleaderAuditBackEventSource;
import com.cloudsea.sys.module.bpm.observable.DeptleaderAuditPassEventSource;
import com.cloudsea.sys.module.bpm.observable.DeptleaderWriteEventSource;
import com.cloudsea.sys.module.bpm.observable.Observable;
import com.itany.frame.spring.ioc.anotation.Component;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class DeptleaderStatus extends BaseStatus{

	/**
	 * @param processContext
	 * @param eventMap
	 */
	public DeptleaderStatus(Process process, Map<String, Observable> eventSourceMap) {
		super(process, eventSourceMap);
	}

	
//	@Override
//	public void registerEventSources() {
//		DeptleaderAuditPassEventSource deptleaderAuditPassEventSource = new DeptleaderAuditPassEventSource();
//		DeptleaderAuditBackEventSource deptleaderAuditBackEventSource = new DeptleaderAuditBackEventSource();
//		DeptleaderWriteEventSource deptleaderWriteEventSource = new DeptleaderWriteEventSource();
//		
//		super.registerEventSource("auditPass", deptleaderAuditPassEventSource);
//		super.registerEventSource("auditBack", deptleaderAuditBackEventSource);
//		super.registerEventSource("write", deptleaderWriteEventSource);
//	}


}
