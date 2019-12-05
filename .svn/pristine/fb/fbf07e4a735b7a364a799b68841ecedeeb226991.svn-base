package com.fixit.dao;

import java.util.Calendar;
import java.util.Set;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;
import com.fixit.domain.vo.Verification;

public interface VerificationDao extends JpaDao<Verification> {
	/**
	 * @param userId
	 *            - user id of a particular user.
	 * 
	 * @return Set of object of verification based of userId.
	 */
	public Set<Verification> findVerificationByUserId(Integer userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - user id of a particular user.
	 * 
	 * @return 1 if success.
	 */
	public int deleteVerificationByUserId(Integer userId) throws DataAccessException;

	/**
	 * @param hashCode
	 *            - generated hash value.
	 * @param time
	 *            - Calendar object.
	 * 
	 * @return object of Verification based on time and hashCode.
	 */
	public Verification findVerificationByHashCode(String hashCode, Calendar time) throws DataAccessException;
}
