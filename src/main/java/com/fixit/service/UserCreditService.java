package com.fixit.service;

import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCredit;
import com.fixit.utility.FixitException;

public interface UserCreditService {
	/**
	 * used to save the user credits.
	 * 
	 * @param user
	 *            - Object of User.
	 * @param points
	 *            -credits of a user.
	 *            
	 *            @return return UserCredit
	 */
	public UserCredit saveUserCredits(User user, Long points) throws FixitException;

	/**
	 * 
	 * used to get the user credit points.
	 * @param userId
	 *            - userId of a particular user.
	 * 
	 * return long
	 */
	public long getPoints(int userId);

	/**
	 *used to get the number of entries in User Credit .
	 * 
	 * @param userId
	 *            - userId of a particular user.
	 * 
	 * @return long
	 */
	public long getCreditCounts(int userId);

	/**
	 * 
	 * used to update user credit points.
	 * 
	 * @param userId
	 *            - userId of a particular user.
	 * @param points
	 *            -credits of a user.
	 *            
	 * @return Integer           
	 */
	public int updatePoints(int userId, long points);
}
