package com.itany.frame.spring.tx;

public class TransactionExecuter {
	
	TransactionManager transactionManager;
	
	TransactionStatus transactionStatus;
		
	TransactionDefinition txDefine;
	
	public TransactionExecuter(TransactionDefinition txDefine, AbstractTransactionManager transactionManager) {
		this.txDefine = txDefine;
		this.transactionManager = transactionManager;
		
		
		
	}

	public void doBegin(){
		
	}
	
	public void doComplete(){
		
	}
	
	public void doException(){
		
	}

	public void doOver(){
		
	}

}
