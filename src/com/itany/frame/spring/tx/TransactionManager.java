package com.itany.frame.spring.tx;

public interface TransactionManager {
	
	Object doGetTransaction(TransactionStatus transactionStatus) throws TransactionException;

	void doBegin(TransactionDefinition txDefine, TransactionStatus transactionStatus) throws TransactionException;

	void doCommit(TransactionDefinition txDefine, TransactionStatus transactionStatus) throws TransactionException;

	void doRollback(TransactionDefinition txDefine, TransactionStatus transactionStatus);
	
	void doLast(TransactionDefinition txDefine, TransactionStatus transactionStatus);

}
