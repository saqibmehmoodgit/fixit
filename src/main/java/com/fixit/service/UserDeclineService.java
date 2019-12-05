package com.fixit.service;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.UserDecline;
import com.fixit.utility.FixitException;

public interface UserDeclineService {
	/**
	 * used to save user decline data.
	 * 
	 * @param userId
	 *            - userId of a User,take Integer as a argument.
	 * @param queryId
	 *            - queryId of a Query, take interger as a argument.
	 * 
	 * return UserDecline
	 */
	public UserDecline saveUserDecline(Integer userId, Integer queryId) throws FixitException;

	/**
	 * used to find the user whose entry is in User Decline.
	 * @param userId
	 *            - userId of a User,take Integer as a argument.
	 * @return return Set
	 */
	public Set<Integer> findDeclineQueryByUserId(Integer userId) throws FixitException;

	/**
	 * used to get the user decline object based on userId and queryId.
	 * 
	 * @param userId
	 *            - userId of a User,take Integer as a argument.
	 * @param queryId
	 *            - queryId of a Query, take interger as a argument.
	 * 
	 * @return return UserDecline
	 */
	public UserDecline findDeclineQueryByAndUserId(Integer userId, Integer queryId) throws FixitException;
}
