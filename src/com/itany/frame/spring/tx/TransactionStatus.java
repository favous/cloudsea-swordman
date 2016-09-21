package com.itany.frame.spring.tx;

public class TransactionStatus {
	
	private Object transaction;
	
	private boolean isNewTransaction;
	
	private boolean isNewSynchronization;
	
	private boolean isReadOnly;
	
	private boolean isDebug;
	
	private Object getSuspendedResources;

	public Object getTransaction() {
		return transaction;
	}

	public void setTransaction(Object transaction) {
		this.transaction = transaction;
	}

	public boolean isNewTransaction() {
		return isNewTransaction;
	}

	public void setNewTransaction(boolean isNewTransaction) {
		this.isNewTransaction = isNewTransaction;
	}

	public boolean isNewSynchronization() {
		return isNewSynchronization;
	}

	public void setNewSynchronization(boolean isNewSynchronization) {
		this.isNewSynchronization = isNewSynchronization;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public Object getGetSuspendedResources() {
		return getSuspendedResources;
	}

	public void setGetSuspendedResources(Object getSuspendedResources) {
		this.getSuspendedResources = getSuspendedResources;
	}

}
