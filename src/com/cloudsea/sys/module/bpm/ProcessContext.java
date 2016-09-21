package com.cloudsea.sys.module.bpm;

import java.util.HashMap;
import java.util.Map;

import com.itany.frame.spring.ioc.XmlConfigApplicatinContext;

public class ProcessContext {
	
	private static Map<String, Process> processMap = new HashMap<String, Process>();

	public static Process getProcess(String name, EntityInvocation entityInvocation) throws Exception{
		Process process = processMap.get(name);
		if (process == null){
			process = (Process) XmlConfigApplicatinContext.getBean(name);
			processMap.put(name, process);
		}
		process.setEntityInvocation(entityInvocation);
		return process;
	}


}
