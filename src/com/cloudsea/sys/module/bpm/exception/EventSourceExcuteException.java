package com.cloudsea.sys.module.bpm.exception;

/**
 * @author zhangxiaorong
 * 2014-1-26
 */
public class EventSourceExcuteException extends Exception {
	
	private String message;
	private Throwable r;
	private Object obj;
	
	public EventSourceExcuteException() {
		super();
	}

	public EventSourceExcuteException(String message) {
		super();
		this.message = message;
	}

	public EventSourceExcuteException(String message, Object obj) {
		super();
		this.message = message;
		this.obj = obj;
	}

	public EventSourceExcuteException(String message, Throwable r, Object obj) {
		super();
		this.message = message;
		this.r = r;
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getR() {
		return r;
	}

	public void setR(Throwable r) {
		this.r = r;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
