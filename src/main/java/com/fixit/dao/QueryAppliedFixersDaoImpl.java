package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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

import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.UserCategory;

@Repository("QueryAppliedFixersDao")
@Transactional
public class QueryAppliedFixersDaoImpl extends AbstractJpaDao<QueryAppliedFixers> implements QueryAppliedFixersDao {

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { QueryAppliedFixers.class }));

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(QueryAppliedFixers o) {
		
		return true;
	}

	@Override
	public List<QueryAppliedFixers> findFixerListByQueryId(Integer userId, Integer queryId, int startIndex,
			int maxResult) {
		try {
			Query query = createNamedQuery("getFixerListBasedOnQueryAndMemberId", startIndex, maxResult, queryId,
					userId);
			return new LinkedList<QueryAppliedFixers>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateAppliedFixerStatus(Integer fixerId, Integer queryId, String status) {
		
		Query query = createNamedQuery("updateAppliedFixerStatus", -1, -1, fixerId, queryId, status);
		query.executeUpdate();

	}

	@Override
	public long getAppliedQueriesCount(int userId) {

		try {
			Query query = createNamedQuery("getAppliedQueriescount", -1, -1, userId);
			return (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;

		}
		

	}

	@Override
	public List<QueryAppliedFixers> findFixerListByFixerId(int userId, int startIndex, int pAGE_SIZE) {
		
		try {
			Query query = createNamedQuery("getFixerListBasedOnFixerId", startIndex, pAGE_SIZE, userId);
			return new LinkedList<QueryAppliedFixers>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<com.fixit.domain.vo.Query> findQuerApplied(int userId, int startResult, int max)
			throws DataAccessException {
		
		try {
			javax.persistence.Query query = createNamedQuery("getAppliedQueries", startResult, max, userId);
			return new LinkedHashSet<com.fixit.domain.vo.Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	
	public int deleteAppliedFixerByQueryId(Integer queryId) throws DataAccessException {

		try {
			javax.persistence.Query query = createNamedQuery("deleteAppliedFixerByQueryId", -1, -1, queryId);
			return query.executeUpdate();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public long countAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException {
		Query  query = createNamedQuery("countAppliedFixerByQueryIdAndStatus", -1, -1, queryId, status);
		long count = (long) query.getSingleResult();
		return count;
	}

	@Override
	public int deleteAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException {

		try {
			javax.persistence.Query query = createNamedQuery("deleteAppliedFixerByQueryIdAndStatus", -1, -1, queryId, status);
			return query.executeUpdate();
		} catch (NoResultException nre) {
			return 0;
		}
	}

}
