package com.fixit.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.FixerRatingCountBo;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface FixerRatingService {

	/**
	 * used to save the fixer ratings.
	 * 
	 * @param query
	 *            - object of Query .
	 * @param fixer
	 *            - object of a user (fixer).
	 * @param starPoints
	 *            - rating of a fixer.
	 * 
	 * @return Fixer Rating
	 */
	public FixerRating saveFixerRatings(Query query, User fixer, int userId, double starPoints, String review)
			throws FixitException;

	/**
	 * Used to find rating
	 * 
	 * @param queryId
	 *            - queryId of a query.
	 * @param fixerId
	 *            - userId of a fixer.
	 * @param memberId
	 *            - userId of a member
	 * @return FixerRating
	 */
	public FixerRating findRatingBasedOnIds(Integer queryId, Integer fixerId, Integer memberId) throws FixitException;

	/**
	 * used to update fixer rating based on id.
	 * 
	 * @param queryId
	 *            - queryId of a particular query.
	 * @param fixerId
	 *            - userId of a fixer.
	 * @param memberId
	 *            - userId of a member.
	 * @param startPoints
	 *            - credit of a user.
	 * 
	 * @return Integer
	 */
	public Integer upDateRatingBasedOnIds(Integer queryId, Integer fixerId, Integer memberId, double startPoints,
			String review) throws FixitException;

	/**
	 * 
	 * used to get the aggregate ratings of a fixer.
	 * 
	 * @param fixerId
	 *            - userId of a fixer.
	 * 
	 * @return Double.
	 */
	public double getAggregateRatingsOfFixer(int fixerId, int fixerRatingByAdmin) throws FixitException;
	public Long getCountsRatingsOfFixer(int fixerId) throws DataAccessException;


	public List<FixerRatingCountBo>  findRatingByFixerIdAndRating(int fixerId) throws FixitException;

}
