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

import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.QueryCategory;
import com.fixit.domain.vo.User;

@Repository("QueryCategoryDao")
@Transactional
public class QueryCategoryDaoImpl extends AbstractJpaDao<QueryCategory> implements QueryCategoryDao{

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { QueryCategory.class }));

	public QueryCategoryDaoImpl() {
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
	public boolean canBeMerged(QueryCategory o) {
		return true;
	}

	
	public QueryCategory findQueryCategoryByQueryCategoryId(Integer queryCategoryId ) throws DataAccessException{
		return findQueryCategoryByQueryCategoryId(queryCategoryId , -1,-1);
	}
	
	public QueryCategory findQueryCategoryByQueryCategoryId(Integer queryCategoryId , int startResult, int maxRows) throws DataAccessException{
		 try {
			 Query query = createNamedQuery("findQueryCategoryById",startResult, maxRows, queryCategoryId);
			 return (QueryCategory) query.getSingleResult();
		 } catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<QueryCategory> findQueryCategoryByQueryId(Integer queryId) throws DataAccessException {
		return findQueryCategoryByQueryId(queryId,-1,-1);
	}

	@Override
	@Transactional
	public Set<QueryCategory> findQueryCategoryByQueryId(Integer queryId, int startResult, int max) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findQueryCategoryByQueryId",startResult, max, queryId);
			 return new LinkedHashSet<QueryCategory>(query.getResultList());
		 } catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public int deleteQueryCategoryByQueryId(Integer queryId , List<Integer> categories ) {
		try {
			 Query query = createNamedQuery("deleteQueryCategoryByQueryIds",-1, -1 ,queryId );
			 query.setParameter("categories", categories);
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}
		
	}
	
	@Override
	@Transactional
	public QueryCategory findQueryCategoryByQueryIdandCatId(int queryId, int catId) throws DataAccessException {
		try {
			 Query query = createNamedQuery("findQueryCategoryByQueryIdandCatId",-1, -1,queryId,catId);
			 return (QueryCategory) query.getSingleResult();
		 } catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public int deleteQueryCategoryByQueryId(int queryId) {
		try {
			javax.persistence.Query query = createNamedQuery("deleteQueryCategoryByQueryId",-1, -1 ,queryId );
			 return query.executeUpdate();
		 }catch (NoResultException nre) {
			return 0;
		}

	}

	
}
