package threadlocal;

import java.io.Serializable;
import java.sql.Connection;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;

public class Start {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		final ThreadLocalSessionContext cxt = new ThreadLocalSessionContext();
		for (int i = 0; i < 3; i++) {

			Runnable runnable = new Runnable() {

				@Override
				public void run() {

					//action location
					//action location
					Session session = new Session() {

						@Override
						public Transaction beginTransaction() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Criteria createCriteria(Class arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Criteria createCriteria(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Criteria createCriteria(Class arg0, String arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Criteria createCriteria(String arg0, String arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Query createQuery(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public SQLQuery createSQLQuery(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Query getNamedQuery(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public String getTenantIdentifier() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Transaction getTransaction() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public LockRequest buildLockRequest(LockOptions arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public IdentifierLoadAccess byId(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public IdentifierLoadAccess byId(Class arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public NaturalIdLoadAccess byNaturalId(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public NaturalIdLoadAccess byNaturalId(Class arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public SimpleNaturalIdLoadAccess bySimpleNaturalId(
								String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public SimpleNaturalIdLoadAccess bySimpleNaturalId(
								Class arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void cancelQuery() throws HibernateException {
							// TODO Auto-generated method stub

						}

						@Override
						public void clear() {
							// TODO Auto-generated method stub

						}

						@Override
						public Connection close() throws HibernateException {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public boolean contains(Object arg0) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public Query createFilter(Object arg0, String arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void delete(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void delete(String arg0, Object arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void disableFetchProfile(String arg0)
								throws UnknownProfileException {
							// TODO Auto-generated method stub

						}

						@Override
						public void disableFilter(String arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public Connection disconnect() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public <T> T doReturningWork(ReturningWork<T> arg0)
								throws HibernateException {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void doWork(Work arg0) throws HibernateException {
							// TODO Auto-generated method stub

						}

						@Override
						public void enableFetchProfile(String arg0)
								throws UnknownProfileException {
							// TODO Auto-generated method stub

						}

						@Override
						public Filter enableFilter(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void evict(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void flush() throws HibernateException {
							// TODO Auto-generated method stub

						}

						@Override
						public Object get(Class arg0, Serializable arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object get(String arg0, Serializable arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object get(Class arg0, Serializable arg1,
								LockMode arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object get(Class arg0, Serializable arg1,
								LockOptions arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object get(String arg0, Serializable arg1,
								LockMode arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object get(String arg0, Serializable arg1,
								LockOptions arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public CacheMode getCacheMode() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public LockMode getCurrentLockMode(Object arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Filter getEnabledFilter(String arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public String getEntityName(Object arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public FlushMode getFlushMode() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Serializable getIdentifier(Object arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public LobHelper getLobHelper() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public SessionFactory getSessionFactory() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public SessionStatistics getStatistics() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public TypeHelper getTypeHelper() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public boolean isConnected() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean isDefaultReadOnly() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean isDirty() throws HibernateException {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean isFetchProfileEnabled(String arg0)
								throws UnknownProfileException {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean isOpen() {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean isReadOnly(Object arg0) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public Object load(Class arg0, Serializable arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object load(String arg0, Serializable arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void load(Object arg0, Serializable arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public Object load(Class arg0, Serializable arg1,
								LockMode arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object load(Class arg0, Serializable arg1,
								LockOptions arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object load(String arg0, Serializable arg1,
								LockMode arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object load(String arg0, Serializable arg1,
								LockOptions arg2) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void lock(Object arg0, LockMode arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void lock(String arg0, Object arg1, LockMode arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public Object merge(Object arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Object merge(String arg0, Object arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void persist(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void persist(String arg0, Object arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void reconnect(Connection arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void refresh(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void refresh(String arg0, Object arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void refresh(Object arg0, LockMode arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void refresh(Object arg0, LockOptions arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void refresh(String arg0, Object arg1,
								LockOptions arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void replicate(Object arg0, ReplicationMode arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void replicate(String arg0, Object arg1,
								ReplicationMode arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public Serializable save(Object arg0) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public Serializable save(String arg0, Object arg1) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void saveOrUpdate(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void saveOrUpdate(String arg0, Object arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public SharedSessionBuilder sessionWithOptions() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public void setCacheMode(CacheMode arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void setDefaultReadOnly(boolean arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void setFlushMode(FlushMode arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void setReadOnly(Object arg0, boolean arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void update(Object arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void update(String arg0, Object arg1) {
							// TODO Auto-generated method stub

						}
					};
					cxt.bind(session);
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//dao location
					//dao location
					Session sn = (Session) cxt.sessionMap().get(
							ThreadLocalSessionContext.factory);
					boolean istrue = sn == session ? true : false;
					System.out.println(istrue);
				}
			};
			Thread t = new Thread(runnable);
			t.start();
		}
	}
}
