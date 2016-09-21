/**
 * 
 */
package com.cloudsea.sys.module.bpm.observers;


import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.itany.frame.spring.ioc.anotation.Component;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
@Component
public class NoticeAuditListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		String articleName = entityInvocation.getArticle().getName();
		String articleAuthor = entityInvocation.getArticle().getAuthor();
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("有新的文件可以审核");
		System.out.println("文件名是：" + articleName);
		System.out.println("作者是：" + articleAuthor);
	}
	

}
