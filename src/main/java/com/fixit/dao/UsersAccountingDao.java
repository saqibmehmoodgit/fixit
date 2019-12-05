package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.UserCredit;
import com.fixit.domain.vo.UsersAccounting;

public interface UsersAccountingDao extends JpaDao<UsersAccounting> {
	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @return 1 if success
	 */
	public Integer deleteUserAccountingDataBasedOnUserId(Integer userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @return set of object of userAccounting 
	 */
	public Set<UsersAccounting> getDataFromUserAccountingBasedOnUseriD(Integer userId) throws DataAccessException;

	public List<UsersAccounting> findUserAccountingByUserIdAndMonth(Integer userId, Integer year,Integer month) throws DataAccessException;

}
