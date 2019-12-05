package com.fixit.dao;

import java.math.BigInteger;
import java.util.List;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.FixerRatingCountBo;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.Query;
import com.fixit.utility.FixitException;

public interface FixerRatingDao extends JpaDao<FixerRating> {
	/**
	 * @param queryId
	 *            - queryId Of a query used to find the fixerRating.
	 * @return fixer rating
	 */
	public FixerRating findFixerRatingByQueryId(int queryId);

	/**
	 * @param fixerId
	 *            - user id of a particular user(Fixer).
	 * @return list of fixer Rating
	 */
	public List<FixerRating> findFixerRatingByFixerId(int fixerId);

	/**
	 * @param queryId
	 *            - queryId Of a query used to find the fixerRating.
	 * @param fixerId
	 *            - user id of a particular user(Fixer).
	 * @param memberId
	 *            - user id of a particular user(member).
	 * @return Object of fixer Rating based on ids
	 */
	public FixerRating findRatingBasedOnIds(Integer queryId, Integer fixerId, Integer memberId)
			throws DataAccessException;

	/**
	 * @param queryId
	 *            - queryId Of a query used to find the fixerRating.
	 * @param fixerId
	 *            - user id of a particular user(Fixer).
	 * @param memberId
	 *            - user id of a particular user(member).
	 * @param startPoints
	 *            - credit of a user.
	 *            @return 1 if success. 
	 */
	public Integer upDateRatingBasedOnIds(Integer queryId, Integer fixerId, Integer memberId, double startPoints,String review)
			throws DataAccessException;

	/**
	 * @param queryId
	 *            - queryId of a query.
	 * @return 1 if success.
	 */
	public Integer deleteRatingBasedOnQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param fixerId
	 *            - user id of a particular user(Fixer).
	 * @return aggregate rating of fixer
	 */
	public Double getAggregateRatingsOfFixer(int fixerId) throws DataAccessException;
	
	
	public List<FixerRatingCountBo> findRatingByFixerIdAndRating(int fixerId);
	public Long getCountsRatingsOfFixer(int fixerId) throws DataAccessException;


}
