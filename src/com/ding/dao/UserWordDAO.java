package com.ding.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserWord entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ding.dao.UserWord
 * @author MyEclipse Persistence Tools
 */

public class UserWordDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserWordDAO.class);
	// property constants
	public static final String LAST_TIME = "lastTime";
	public static final String STAGE = "stage";

	public void save(UserWord transientInstance) {
		log.debug("saving UserWord instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserWord persistentInstance) {
		log.debug("deleting UserWord instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserWord findById(com.ding.dao.UserWordId id) {
		log.debug("getting UserWord instance with id: " + id);
		try {
			UserWord instance = (UserWord) getSession().get(
					"com.ding.dao.UserWord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserWord instance) {
		log.debug("finding UserWord instance by example");
		try {
			List results = getSession().createCriteria("com.ding.dao.UserWord")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserWord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserWord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLastTime(Object lastTime) {
		return findByProperty(LAST_TIME, lastTime);
	}

	public List findByStage(Object stage) {
		return findByProperty(STAGE, stage);
	}

	public List findAll() {
		log.debug("finding all UserWord instances");
		try {
			String queryString = "from UserWord";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserWord merge(UserWord detachedInstance) {
		log.debug("merging UserWord instance");
		try {
			UserWord result = (UserWord) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserWord instance) {
		log.debug("attaching dirty UserWord instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserWord instance) {
		log.debug("attaching clean UserWord instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}