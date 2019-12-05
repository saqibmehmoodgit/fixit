package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.UserIndustry;

@Repository("UserIndustryDao")
@Transactional
public class UserIndustryDaoImpl extends AbstractJpaDao<UserIndustry> implements
		UserIndustryDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { UserIndustry.class }));

	public UserIndustryDaoImpl() {
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
	public boolean canBeMerged(UserIndustry o) {
		return true;
	}

	@Override
	@Transactional
	public Set<UserIndustry> findUserIndustryByUserId(int userId) throws DataAccessException {
		return findUserIndustryByUserId(userId ,-1,-1 );
	}

	@Override
	@Transactional
	public Set<UserIndustry> findUserIndustryByUserId(int userId, int startResult, int maxResult) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findUserIndustryByUserId",startResult, maxResult ,userId);
			 return new LinkedHashSet<UserIndustry>(query.getResultList());
		 }catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId, int indstId) throws DataAccessException {
		return findUserIndustryByUserIdandIndstId(userId,indstId,-1,-1);
	}

	@Override
	@Transactional
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId,int indstId, int startResult, int maxResult) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findUserIndustryByUserIdandIndstId",startResult, maxResult ,userId,indstId);
			 return (UserIndustry) query.getSingleResult();
		 }catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public int deleteUserIndustry(int userId ,List<Integer> indstIds) throws DataAccessException {
		try {
			 Query query = createNamedQuery("deleteUserIndustry",-1, -1 ,userId );
			 query.setParameter("indstIds", indstIds);
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}
	}
	@Override
	@Transactional
	public int deleteUserIndustryBasedOnUserId(int userId) throws DataAccessException{
		try {
			 Query query = createNamedQuery("deleteUserIndustryBasedOnUserId",-1, -1 ,userId );
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public int deleteUserIndustryByUserIdandIndstId(Integer userId, Integer indstId) throws DataAccessException {
		try {
			 Query query = createNamedQuery("deleteUserIndustryByUserIdandIndstId",-1, -1 ,userId,indstId);
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}
	}

}
