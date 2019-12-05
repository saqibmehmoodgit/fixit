package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.UserDecline;

public interface UserDeclineDao extends JpaDao<UserDecline> {
	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @param queryId
	 *            - query id of a query.
	 * @return Object of declined user.
	 */
	public UserDecline findDeclineQuery(Integer userId, Integer queryId) throws DataAccessException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @return 1 if success
	 */
	public Integer deleteDeclineQueryBasedOnUserID(Integer userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * @return Set of object of UserDecline
	 */
	public Set<UserDecline> findDeclineQueryByUserId(Integer userId) throws DataAccessException;

}
