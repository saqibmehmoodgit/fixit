package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.IndustryType;

public interface IndustryTypeDao extends JpaDao<IndustryType> {
	/**
	* method used to return Set(collection) of industries.
	* 
	*/
	public Set<IndustryType> findAllIndustryType() throws DataAccessException;

	/**
	 * @param startResult
	 *            - firstResult the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * 
	 */
	public Set<IndustryType> findAllIndustryType(int startResult, int max) throws DataAccessException;

	/**
	 * @param indstId
	 *            - industry id of a industry.
	 * 
	 */
	public IndustryType findIndustryTypeByIndstId(int indstId) throws DataAccessException;

	/**
	 * @param indstId
	 *            - industry id of a industry.
	 * @param startResult
	 *            - firstResult the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * 
	 */
	public IndustryType findIndustryTypeByIndstId(int indstId, int startResult, int max) throws DataAccessException;
}
