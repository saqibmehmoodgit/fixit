package com.fixit.service;

import com.fixit.domain.bo.UsersAccountingBo;
import com.fixit.domain.vo.UsersAccounting;
import com.fixit.utility.FixitException;

public interface UsersAccountingService {

	/** 
	 * used for saving user accounting data.
	 * 
	 *@param usersAccountingBo -  object of UserAcountingBo, user to set type,amount,user.
	 * 
	 * @return return UserAccounting
	 */
	public UsersAccounting saveUserAccounting(UsersAccountingBo usersAccountingBo) throws  FixitException; 
}
