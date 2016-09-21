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
public class NoticePublishListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		
		String articleName = entityInvocation.getArticle().getName();
		String content = entityInvocation.getArticle().getContent();
		
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("文件审核已通过，可以发行！");
		System.out.println("文件名是：" + articleName);
		System.out.println("内容是：" + content);
	}
	

}
