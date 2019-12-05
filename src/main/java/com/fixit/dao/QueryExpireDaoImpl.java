package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.utility.FixitException;
@Repository("QueryExpireDao")
@Transactional
public class QueryExpireDaoImpl extends AbstractJpaDao<QueryExpire> implements QueryExpireDao{

	
	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { Query.class }));
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(QueryExpire o) {
		return true;
	}

	@Override
	@Transactional
	public QueryExpire findQueryExpireByFixerIdAndhashCode(Integer fixerId,
			String hashcode) throws FixitException {
		try {
			javax.persistence.Query query = createNamedQuery(
					"findQueryExpireByFixerIdAndhashCode", -1, -1, fixerId,hashcode);
			return (QueryExpire) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<QueryExpire> findQueryExpireByDaysInterval(String currentDate,int day)
			throws FixitException {
		String nativeQuery = "SELECT * FROM query_expire WHERE  DATE_FORMAT(created_at,'%Y-%m-%d') <= DATE_FORMAT(DATE_SUB('"
				+ currentDate
				+ "',INTERVAL "
				+ day + " DAY),'%Y-%m-%d')";
		javax.persistence.Query query = entityManager.createNativeQuery(
				nativeQuery, "expireQueryMapping");
		return new HashSet<QueryExpire>(query.getResultList());

	}

	@Override
	@Transactional
	public String deleteDataByDaysIinterval(Set<QueryExpire> queryExpireSet)
			throws FixitException {
		if(queryExpireSet.size() != 0){
			Iterator<QueryExpire> iterator = queryExpireSet.iterator();
			while(iterator.hasNext()){
				QueryExpire queryExpire = iterator.next();
				javax.persistence.Query query = createNamedQuery("deleteQueryExpireById", -1, -1, queryExpire.getQueryExpireId());
			    query.executeUpdate();
			}
		}
		return "success";
	}
	
}
