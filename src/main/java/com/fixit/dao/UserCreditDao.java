package com.fixit.dao;


import org.skyway.spring.util.dao.JpaDao;

import com.fixit.domain.vo.UserCredit;
import com.fixit.utility.FixitException;

public interface UserCreditDao extends JpaDao<UserCredit> {
	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @return Long value
	 */
	public Long findUserCreditPoints(int userId) throws FixitException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * 
	 */
	public Long getCreditCountsByUserId(int userId) throws FixitException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @param points
	 *            - credits of a user.
	 * @return 1 if success.
	 */
	public int updateUserCreditPoints(int userId, long points) throws FixitException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @return 1 if success
	 */
	public int deleteUserCreditPoints(int userId) throws FixitException;

}
