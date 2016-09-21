package com.itany.frame.spring.tx;

import java.lang.annotation.Annotation;

import com.itany.frame.spring.tx.enums.Isolation;
import com.itany.frame.spring.tx.enums.Propagation;

public class TransactionDefinition {
	
	private boolean isReadOnly;
	private int timeout;
	private boolean newTransaction;
	private boolean newSynchronization;
	private boolean debug;
	private Class<? extends Annotation> annotationType;
	private Isolation isolation;
	private Class<?>[] noRollbackFor;
	private String[] noRollbackForClassName;
	private Propagation propagation;
	private String annotationValue;
	
	public TransactionDefinition(Class<? extends Annotation> annotationType,
			Isolation isolation, Class<?>[] noRollbackFor,
			String[] noRollbackForClassName, Propagation propagation,
			boolean isReadOnly, int timeout, String annotationValue) {
		this.annotationType = annotationType;
		this.isolation = isolation;
		this.noRollbackFor = noRollbackFor;
		this.noRollbackForClassName = noRollbackForClassName;
		this.propagation = propagation;
		this.isReadOnly = isReadOnly;
		this.timeout = timeout;
		this.annotationValue = annotationValue;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public boolean isNewTransaction() {
		return newTransaction;
	}
	public void setNewTransaction(boolean newTransaction) {
		this.newTransaction = newTransaction;
	}
	public boolean isNewSynchronization() {
		return newSynchronization;
	}
	public void setNewSynchronization(boolean newSynchronization) {
		this.newSynchronization = newSynchronization;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}
	public void setAnnotationType(Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
	}
	public Isolation getIsolation() {
		return isolation;
	}
	public void setIsolation(Isolation isolation) {
		this.isolation = isolation;
	}
	public Class<?>[] getNoRollbackFor() {
		return noRollbackFor;
	}
	public void setNoRollbackFor(Class<?>[] noRollbackFor) {
		this.noRollbackFor = noRollbackFor;
	}
	public String[] getNoRollbackForClassName() {
		return noRollbackForClassName;
	}
	public void setNoRollbackForClassName(String[] noRollbackForClassName) {
		this.noRollbackForClassName = noRollbackForClassName;
	}
	public Propagation getPropagation() {
		return propagation;
	}
	public void setPropagation(Propagation propagation) {
		this.propagation = propagation;
	}
	public String getAnnotationValue() {
		return annotationValue;
	}
	public void setAnnotationValue(String annotationValue) {
		this.annotationValue = annotationValue;
	}

	
}
