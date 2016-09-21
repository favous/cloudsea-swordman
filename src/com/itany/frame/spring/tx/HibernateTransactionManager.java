package com.itany.frame.spring.tx;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ThreadLocalSessionContext;

import com.itany.frame.spring.beans.InitializingBean;

public class HibernateTransactionManager extends AbstractTransactionManager implements InitializingBean {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	@Override
	public Object doGetTransaction(TransactionStatus transactionStatus) throws TransactionException {
		return transactionStatus.getTransaction();
	}

	@Override
	public void doBegin(TransactionDefinition txDefine, TransactionStatus transactionStatus) throws TransactionException {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		transactionStatus.setTransaction(tx);
		
		if (txDefine.getTimeout() > 0){
			tx.setTimeout(txDefine.getTimeout());
		}
		
		if (txDefine.isReadOnly()){			
			
		}
		
		ThreadLocalSessionContext.bind(session);
	}

	@Override
	public void doCommit(TransactionDefinition txDefine,
			TransactionStatus transactionStatus) throws TransactionException {
		
		((Transaction) transactionStatus.getTransaction()).commit();
	}

	@Override
	public void doRollback(TransactionDefinition txDefine,
			TransactionStatus transactionStatus) {
		
		((Transaction) transactionStatus.getTransaction()).rollback();
	}

	@Override
	public void doLast(TransactionDefinition txDefine,
			TransactionStatus transactionStatus) {
		
		super.getSessionFactory().getCurrentSession().close();
	}



}
