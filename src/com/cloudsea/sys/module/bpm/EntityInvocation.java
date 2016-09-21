/**
 * 
 */
package com.cloudsea.sys.module.bpm;

import com.cloudsea.sys.module.bpm.domain.Article;
import com.cloudsea.sys.module.bpm.domain.User;




/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class EntityInvocation {
	
	Article article;
	User user;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
