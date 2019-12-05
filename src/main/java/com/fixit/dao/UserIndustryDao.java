package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

public interface UserIndustryDao extends JpaDao<UserIndustry> {
	/**
	 * @param userId
	 *            - userId of a particlar user.
	 * @return Set of object of UserIndustry
	 */
	public Set<UserIndustry> findUserIndustryByUserId(int userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particlar user.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Set of object of UserIndustry
	 * 
	 */
	public Set<UserIndustry> findUserIndustryByUserId(int userId, int startResult, int maxResult)
			throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particlar user.
	 * @param indstId
	 *            - List of industry ID.
	 * @return object of UserIndustry
	 * 
	 */
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId, int indstId) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param indstId
	 *            - List of industry ID.
	 * @param startResult
	 *            - the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Set of object of UserIndustry
	 * 
	 */
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId, int indstId, int startResult, int maxResult)
			throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particlar user.
	 * @param indstIds
	 *            - List of industries ID.
	 *@return if success. 
	 */
	public int deleteUserIndustry(int userId, List<Integer> indstIds) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particlar user.
	 * @return if success. 
	 */
	public int deleteUserIndustryBasedOnUserId(int userId) throws DataAccessException;
	
	public int deleteUserIndustryByUserIdandIndstId(Integer userId , Integer indstId) throws DataAccessException;

}
