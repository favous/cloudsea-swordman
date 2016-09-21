/**
 * 
 */
package com.cloudsea.sys.module.bpm.domain;

/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public class Article {
	
	private String name;
	private String content;
	private String author;
	AuditState auditState;
	
	public Article() {
		super();
	}

	public Article(String name, String content, String author, AuditState AuditState) {
		super();
		this.name = name;
		this.content = content;
		this.author = author;
		this.auditState = AuditState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public AuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
	}
	
	
}
