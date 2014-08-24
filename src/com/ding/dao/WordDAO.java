package com.ding.dao;

import java.util.HashSet;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Entity;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ding.pojo.Word;

/**
 * A data access object (DAO) providing persistence and search support for Word
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ding.pojo.Word
 * @author MyEclipse Persistence Tools
 */
public class WordDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(WordDAO.class);
	// property constants
	public static final String WORD_SEN_EN = "wordSenEn";
	public static final String WORD_SEN_CN = "wordSenCn";
	public static final String WORD_PRON = "wordPron";
	public static final String WORD_MEAN = "wordMean";
	public static final String WORD_TYPE = "wordType";
	public static final String WORD_BOOK = "wordBook";

	public void save(Word word) {
//		log.debug("saving Word instance");
//		try {
//			
//			getSession().save(word);
//	
////			if( findById(word.getWord()) == null){
////				getSession().save(word);
////				log.debug("save successful");
////			}else{
////				log.debug("word already exist");
////			}
//			
//		} catch (RuntimeException re) {
//			log.error("save failed", re);
//			throw re;
//		}
		
		Session session	=HibernateSessionFactory.getSession();
		Transaction t=	session.beginTransaction();
		
		session.save(word);

		t.commit();
		session.close() ;
	}

	public void delete(Word persistentInstance) {
		log.debug("deleting Word instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Word findById(java.lang.String id) {
		log.debug("getting Word instance with id: " + id);
		try {
			Word instance = (Word) getSession().get("com.ding.dao.Word", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Word instance) {
		log.debug("finding Word instance by example");
		try {
			List results = getSession().createCriteria("com.ding.dao.Word")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	
	public void saveAllWordIntoDb(HashSet<Word> allWords){
		
		Session session	=HibernateSessionFactory.getSession();
		Transaction t=	session.beginTransaction();
		
		for (Word word : allWords) {
			session.save(word);
		}
		
		t.commit();
		session.close() ;
	}
	

}