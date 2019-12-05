package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;
import com.fixit.domain.vo.QueryCategory;

public interface QueryCategoryDao extends JpaDao<QueryCategory> {
	/**
	 * @param queryCategoryId
	 *            - id of a particular QueryCAtegory.
	 * @return object of QueryCategory
	 * 
	 */
	public QueryCategory findQueryCategoryByQueryCategoryId(Integer queryCategoryId) throws DataAccessException;

	/**
	 * @param queryCategoryId
	 *            - id of a particular QueryCAtegory.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return object of QueryCategory
	 * 
	 */
	public QueryCategory findQueryCategoryByQueryCategoryId(Integer queryCategoryId, int startResult, int max)
			throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * 
	 * @return Set of object of QueryCategory.
	 */
	public Set<QueryCategory> findQueryCategoryByQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Set of object of QueryCategory.
	 */
	public Set<QueryCategory> findQueryCategoryByQueryId(Integer queryId, int startResult, int max)
			throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param categories
	 *            - List of categories Id.
	 * @return 1 if success.
	 */
	public int deleteQueryCategoryByQueryId(Integer queryId, List<Integer> categories);

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param catId - Id of a particular categories.          
	 * @return object of QueryCategory
	 */
	public QueryCategory findQueryCategoryByQueryIdandCatId(int queryId, int catId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @return 1 if success.
	 */
	public int deleteQueryCategoryByQueryId(int queryId);

}
