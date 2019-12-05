package com.fixit.service;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.FixerAccounting;
import com.fixit.utility.FixitException;

public interface FixerAccountingService {

	/**
	 * used to save fixer accounting
	 * 
	 * @param fixerAccounting
	 *            - object of FixerAccounting.
	 *
	 * @return object of saved fixerAccounting
	 */
	public FixerAccounting saveFixerAccounting(FixerAccounting fixerAccounting) throws FixitException;
	/**
	 * used to find fixer unpaid query count.
	 * 
	 * @param fixerId
	 *            - Take fixerId as Integer.
	 *
	 * @return Long
	 */
	public Long findFixerUnpaidQueryCount(Integer fixerId) throws FixitException;
	/**
	 * used to save fixer unpaid query.
	 * 
	 * @param fixerId
	 *            - Take fixerId as Integer.
	 *
	 * @return Set<FixerAccounting>
	 */
	public Set<FixerAccounting> findFixerUnpaidQuery(Integer fixerId) throws FixitException;

	/**
	 * 
	 * used to update fixer paid status.
	 * @param fixerId
	 *            - userId of a fixer.
	 * @param status - paid staus of a particular fixer.
	 * 
	 * @return Integer
	 */

	public int updateFixerPaidStatus(Integer fixerId, int status) throws FixitException;

}
