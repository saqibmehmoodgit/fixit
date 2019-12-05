package com.fixit.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.UserCredit;
import com.fixit.domain.vo.UsersAccounting;

@Repository("UsersAccountingDao")
@Transactional
public class UsersAccountingDaoImpl extends AbstractJpaDao<UsersAccounting> implements UsersAccountingDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { Query.class }));

	@Override
	public EntityManager getEntityManager() {
		
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(UsersAccounting o) {
		
		return true;
	}

	@Override
	@Transactional
	public Integer deleteUserAccountingDataBasedOnUserId(Integer userId) throws DataAccessException {
		try {

			javax.persistence.Query query = createNamedQuery("deleteUserAccountingDataBasedOnUserId", -1, -1, userId);
			return query.executeUpdate();
		} catch (Exception nre) {
			return -1;
		}
	}

	@Override
	@Transactional
	public Set<UsersAccounting> getDataFromUserAccountingBasedOnUseriD(Integer userId) throws DataAccessException {
		try {

			javax.persistence.Query query = createNamedQuery("getDataFromUserAccountingBasedOnUserID", -1, -1, userId);
			return new LinkedHashSet<UsersAccounting>(query.getResultList());
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<UsersAccounting> findUserAccountingByUserIdAndMonth(Integer userId, Integer year,Integer month) throws DataAccessException {
		
		try {

			javax.persistence.Query query = createNamedQuery("findUserAccountingByUserIdAndMonthYear", -1, -1, userId, year, month);
			List<UsersAccounting> list  = new ArrayList<UsersAccounting>(query.getResultList());
			return list;
		} catch (Exception nre) {
			return null;
		}
	}
}
