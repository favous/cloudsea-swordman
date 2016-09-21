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
public class UpdateArticleListener implements Observer {

	
	@Override
	public void action(EntityInvocation entityInvocation) {
		
		Article article = entityInvocation.getArticle();
		System.out.println("\r\t\r\t----------监听器执行－－－－－－－");
		System.out.println("数据库更新，对象是：" + article.toString());
	}
	

}
