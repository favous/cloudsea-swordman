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
public class NoticeModifyListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		String articleName = entityInvocation.getArticle().getName();
		String returnResean = entityInvocation.getArticle().getAuditState().getReturnResean();
		String returnUser = entityInvocation.getArticle().getAuditState().getReturnUser().getUserName();
		
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("您起草的文件被打回来了，请及时修改！");
		System.out.println("文件名是：" + articleName);
		System.out.println("打回原因是：" + returnResean);
		System.out.println("打回人是：" + returnUser);
	}
	

}
