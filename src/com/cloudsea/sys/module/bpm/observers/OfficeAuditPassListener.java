/**
 * 
 */
package com.cloudsea.sys.module.bpm.observers;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.EntityInvocation;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
@Component
public class OfficeAuditPassListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("办公室审核通过");
	}	

}
