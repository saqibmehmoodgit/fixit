package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.utility.FixitException;

public interface QueryExpireDao extends JpaDao<QueryExpire> {
	/**
	 * @param fixerId - userId of a user(fixer).
	 * @param hashcode - generated hash Value.
	 * @return Object of QueryExpire.
	 */
	public QueryExpire findQueryExpireByFixerIdAndhashCode(Integer fixerId, String hashcode) throws FixitException;

	/**
	 * @param currentDate - current system date.
	 * @param days duration in days.
	 * @return Set of object of QueryExpires.
	 */
	public Set<QueryExpire> findQueryExpireByDaysInterval(String currentDate, int days) throws FixitException;

	/**
	 * 
	 * @param queryExpireSet - set of object of QueryExpire.
	 * @return 1 if success
	 */
	public String deleteDataByDaysIinterval(Set<QueryExpire> queryExpireSet) throws FixitException;
}
