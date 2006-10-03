package org.hl7.hibernate;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.FlushMode;
import org.hibernate.Criteria;
import org.hibernate.Transaction;

/** Simple interface to the persistence mechanism. Helps
 with a thread-local Session singleton and thread-global
 SessionFactory singleton.
 */
public class Persistence {
	
	private static Configuration _configuration = null;
	private static SessionFactory _sessionFactory = null;
	
	private static synchronized void init() {
		if(_sessionFactory == null) {
			if(_configuration == null) {
				_configuration = new Configuration()
					.setNamingStrategy(ReservedWordAvoidanceNamingStrategy.instance())
					.configure();
			}
			_sessionFactory = _configuration.buildSessionFactory();
		}
	}
	
	public static Configuration getConfiguration() {
		if(_configuration == null)
			init();
		return _configuration;
	}

	public static SessionFactory getSessionFactory() {
		if(_sessionFactory == null)
			init();
		return _sessionFactory;
	}
	
	private static ThreadLocal _session = new ThreadLocal();
	
	public static Session getSession() {
		Session session = (Session)_session.get();
		if(session == null) {
			session = getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			Transaction tx2 = session.getTransaction();
			if(tx != tx2)
				throw new Error("assertion failed about " + tx + " == " + tx2);		
		}
		// only flush on commits
		session.setFlushMode(FlushMode.COMMIT);
		_session.set(session);
		return session;
	}
	
	public static Object load(Class clazz, java.io.Serializable id) {
		return getSession().load(clazz, id);
	}
	
	public static void delete(Object obj) {
		getSession().delete(obj);
	}
	
	public static boolean isPersistent(Object object) {
	  return getSession().contains(object);
	}
	
	public static Query createHQLQuery(String hql) {
	  return getSession().createQuery(hql);
	}
	
	public static SQLQuery createSQLQuery(String sql) {
		return getSession().createSQLQuery(sql);
	}
	
	public static Query createNamedQuery(String query) {
		return getSession().getNamedQuery(query);
	}
	
	public static Query createFilter(Object collection, String hql) {
		return getSession().createFilter(collection, hql);
	}
	
	public static Criteria createCriteria(Class criteriaClass) {
		return getSession().createCriteria(criteriaClass);
	}
	
	public static void save(Object object) {
		getSession().save(object);
	}
	
	public static void commit() {
		Session session = getSession();
		session.getTransaction().commit();
		Transaction tx = session.beginTransaction();
		Transaction tx2 = session.getTransaction();
		if(tx != tx2)
		  throw new Error("assertion failed about " + tx + " == " + tx2);		
	}
	
	public static void close() {
		getSession().close();
		_session.set(null);
	}
} 
