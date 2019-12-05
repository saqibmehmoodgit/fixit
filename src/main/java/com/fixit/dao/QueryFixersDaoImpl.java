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

import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;
import com.fixit.utility.FixitException;

@Repository("QueryFixersDao")
@Transactional
public class QueryFixersDaoImpl extends AbstractJpaDao<QueryFixers> implements QueryFixersDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { UserCategory.class }));
	
	@Override
	public EntityManager getEntityManager() {
		
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(QueryFixers o) {
		
		return true;
	}

	@Override
	@Transactional
	public List<QueryFixers> getQueryFixersBasedOnQueryId(Integer queryId,int startIndex , int maxResult) throws DataAccessException {
		 try {
			 Query query = createNamedQuery("getQueryFixersBasedOnQueryId",startIndex, maxResult, queryId);
			 return new LinkedList<QueryFixers>(query.getResultList());
		 } catch (NoResultException nre) {
			return null;
		}
		 catch(Exception e){
			 return null;
		 }
	}
	@Override
	@Transactional
	public Integer deleteQueryFixersBasedOnQueryId(Integer queryId) throws DataAccessException{
		 try {
			 Query query = createNamedQuery("deleteQueryFixersBasedOnQueryId",-1, -1, queryId);
			 return (Integer) query.executeUpdate();
			} catch (NoResultException nre) {
			return -1;
		}
	}
	@Override
	@Transactional
	public QueryFixers findQueryFixersBasedOnQueryIdAndFixerId(Integer queryId,Integer userId,int startIndex , int maxResult) throws FixitException{
		 try {
			 Query query = createNamedQuery("findQueryFixersBasedOnQueryIdAndFixerId",-1, -1, queryId,userId);
			 return (QueryFixers) query.getSingleResult();
			} catch (NoResultException nre) {
			return null;
		}catch(Exception e){
			return null;
		}
	}
}
