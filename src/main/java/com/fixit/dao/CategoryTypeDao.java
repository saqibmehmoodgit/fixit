package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.CategoryType;

public interface CategoryTypeDao extends JpaDao<CategoryType> {

	/**
	 * @param catId
	 *            - categoryId(i.e. catId) of a particular category.
	 * @return Object of Category
	 */
	public CategoryType findCategoryTypeByCatId(Integer catId) throws DataAccessException;

	/**
	 * @param catId
	 *            - categoryId(i.e. catId) of a particular category.
	 * @param startResult
	 *            - firstResult the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Object of Category
	 */
	public CategoryType findCategoryTypeByCatId(Integer catId, int startResult, int max) throws DataAccessException;

	/**
	 * @param status
	 *            - category with WNS/WONS.
	 * 
	 */
	public Set<CategoryType> findAllCategoryType(String status) throws DataAccessException;

	/**
	 * @param startResult
	 *            - firstResult the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @param status
	 *            - category with WNS/WONS.
	 * 
	 */
	public Set<CategoryType> findAllCategoryType(int startResult, int max, String status) throws DataAccessException;

	/**
	 * @param status
	 *            - category with WNS/WONS.
	 * 
	 */
	public Set<CategoryType> findAllParentCategory(String status) throws DataAccessException;

	/**
	 * @param startResult
	 *            - firstResult the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @param status
	 *            - category with WNS/WONS.
	 * 
	 */
	public Set<CategoryType> findAllParentCategory(int startResult, int max, String status) throws DataAccessException;

	/**
	 * @param queryId
	 *            - queryId of a query used to fetch from query category.
	 * 
	 */
	public CategoryType findQueryParentCategory(int queryId) throws DataAccessException;
	
	public List<String> findAllParentCategoryByCatId(List<Integer> catId) throws DataAccessException;
	

}
