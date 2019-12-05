package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.UserCategory;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserIndustry;
import com.sun.org.apache.bcel.internal.generic.RET;

public interface UserCategoryDao extends JpaDao<UserCategory> {
	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @return Set(collection) of userCategories
	 */
	public Set<UserCategory> findUserCategoryByUserId(int userId) throws DataAccessException;

	public Set<User> findFixerByCategory() throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param catlist
	 *            - LIst of id of catgories.
	 * @return 1 if success
	 */
	public int deleteUserCategory(int userId, List<Integer> catlist) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @return 1 if success
	 */
	public int deleteUserCategoryBasedOnUserId(int userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param catId
	 *            - category Id(catID) of a category.
	 * @return object of UserCategory.
	 */
	public UserCategory findUserCategoryByUserIdandcatId(int userId, int catId) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param catId
	 *            - category Id(catID) of a category.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Object of UserCategory filtered with userId and CatId
	 */
	UserCategory findUserCategoryByUserIdandcatId(int userId, int catId, int startResult, int maxResult)
			throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Set(collection) of object of UserCategory  
	 */
	Set<UserCategory> findUserCategoryByUserId(int userId, int startResult, int maxResult) throws DataAccessException;

	public int deleteUserCategoryBasedOnUserIdandCatId(Integer userId,Integer catId) throws DataAccessException;
}
