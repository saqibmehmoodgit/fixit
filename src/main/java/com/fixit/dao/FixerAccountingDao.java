package com.fixit.dao;

import java.util.List;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.FixerAccountingGroup;
import com.fixit.domain.vo.FixerAccounting;

public interface FixerAccountingDao extends JpaDao<FixerAccounting> {
	/**
	 * method used to get count of unpaid fixer.
	 * 
	 */
	public Integer findFixerUnpaidQueryCount() throws DataAccessException;

	/**
	 * @param startIndex
	 *            - firstResult the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * 
	 */
	public List<FixerAccountingGroup> findFixerUnpaidQuery(Integer startIndex, Integer maxResult)
			throws DataAccessException;

	/**
	 * @param userId
	 *            - user id of a particular user(Fixer).
	 * @param status - status of a user.
	 */
	public int updateFixerPaidStatus(Integer fixerId, int status) throws DataAccessException;

	/**
	 * @param queryId - query Id of a query to be deleted.
	 * 
	 */
	public int deleteDataBasedOnQueryId(Integer queryId) throws DataAccessException;
}
