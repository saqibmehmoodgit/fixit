package com.fixit.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.bo.FixerRatingCountBo;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.IndustryType;

@Repository("FixerRatingDao")
@Transactional
public class FixerRatingDaoImpl extends AbstractJpaDao<FixerRating> implements FixerRatingDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { FixerRating.class }));

	public FixerRatingDaoImpl() {
		super();
	}
	
	
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}



	@Override
	public boolean canBeMerged(FixerRating o) {
		return true;
	}



	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}



	@Override
	public FixerRating findFixerRatingByQueryId(int queryId) {
		
		return null;
	}



	@Override
	public List<FixerRating> findFixerRatingByFixerId(int fixerId) {
		
		return null;
	}



	@Override
	@Transactional
	public FixerRating findRatingBasedOnIds(Integer queryId, Integer fixerId,
			Integer memberId) throws DataAccessException {
		
		try {
			javax.persistence.Query query = createNamedQuery(
					"findRatingBasedOnIds", -1, -1, queryId,fixerId,memberId);
			return (FixerRating) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		
	}
	@Override
	@Transactional
	public Double getAggregateRatingsOfFixer(int fixerId) throws DataAccessException{
		try {
			javax.persistence.Query query = createNamedQuery(
					"getAggregateRatingsOfFixer", -1, -1, fixerId);
			return  (Double) query.getSingleResult();
		} catch (NoResultException nre) {
			return (double) -1;
		}
	}
	@Override
	@Transactional
	public Long getCountsRatingsOfFixer(int fixerId) throws DataAccessException{
		try {
			javax.persistence.Query query = createNamedQuery(
					"getCountsRatingsOfFixer", -1, -1, fixerId);
			return  (Long) query.getSingleResult();
		} catch (NoResultException nre) {
			return  (long) -1;
		}
	}
	
	@Override
	@Transactional
	public Integer upDateRatingBasedOnIds(Integer queryId, Integer fixerId,
			Integer memberId, double startPoints,String review) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery(
					"updateFixerRatingBasedOnIds", -1, -1, queryId,fixerId,memberId,startPoints,review);
			return query.executeUpdate();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
		@Override
		@Transactional
		public Integer deleteRatingBasedOnQueryId(Integer queryId) throws DataAccessException{
			try {
				javax.persistence.Query query = createNamedQuery(
						"deleteRatingBasedOnQueryId", -1, -1, queryId);
				return query.executeUpdate();
			} catch (NoResultException nre) {
				return null;
			}
		
	}



		@Override
		public List<FixerRatingCountBo> findRatingByFixerIdAndRating(int fixerId) {
			try{
			Query query = createNamedQuery("findRatingByFixerIdAndRating", -1, -1, fixerId);
			List<FixerRatingCountBo> list = new ArrayList<FixerRatingCountBo>();
			List<Object[]>  rows =  query.getResultList();
			
			for (Object[] row : rows) {
				long count = (long) row[0];
			    double rating = (double) row[1];
			    FixerRatingCountBo fixerRatingCountBo = new FixerRatingCountBo();
			    fixerRatingCountBo.setCount(count);
			    fixerRatingCountBo.setRating(rating);
			    list.add(fixerRatingCountBo);
			}
			return list;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			
		}



	


}
