/**
 * 
 */
package com.cloudsea.sys.module.bpm.observers;

import com.itany.frame.spring.ioc.anotation.Component;

import com.cloudsea.sys.module.bpm.EntityInvocation;
import com.cloudsea.sys.module.bpm.domain.Article;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
@Component
public class WriteArticleListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		Article article = entityInvocation.getArticle();
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("起草文件一份，保存到数据库");
		System.out.println("数据库保存，对象是：" + article.toString());
		
	}
	

}
