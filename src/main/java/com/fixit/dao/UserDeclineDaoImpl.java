package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserDecline;
@Repository("UserDeclineDao")
@Transactional
public class UserDeclineDaoImpl extends AbstractJpaDao<UserDecline> implements UserDeclineDao{

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;
	
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { UserDecline.class }));
	public UserDeclineDaoImpl(){
		super();
	}

	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(UserDecline o) {
		return true;
	}


	@Override
	@Transactional
	public UserDecline findDeclineQuery(Integer userId, Integer queryId) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findDeclineQuery",-1, -1 ,userId ,queryId);
			 return  (UserDecline) query.getSingleResult();
		 }catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Integer  deleteDeclineQueryBasedOnUserID(Integer userId) throws DataAccessException{
		try {
			 Query query = createNamedQuery("deleteDeclineQueryBasedOnUserID",-1, -1 ,userId );
			 return  (Integer) query.executeUpdate();
		 }catch (NoResultException nre) {
			return -1;
		}
	}

	@Override
	public Set<UserDecline> findDeclineQueryByUserId(Integer userId) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findDeclineQueryByUserId",-1, -1 ,userId );
			 return new LinkedHashSet<UserDecline>(query.getResultList());
		 }catch (NoResultException nre) {
			return null;
		}
		
	}
	
	
}
