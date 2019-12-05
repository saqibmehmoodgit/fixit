package com.fixit.service;

import com.fixit.domain.vo.FavouriteFixer;
import com.fixit.utility.FixitException;

public interface FavouriteFixerService {

	
	/**
	 * method used to save Favourite Fixer by member 
	 * 
	 * @param memberId - userId of a member. 
	 * @param fixerId - userId of a fixer.
	 * 
	 * @return object of Favourite Fixer selected by member 
	 */
	public FavouriteFixer saveFavourateFixer(int memberId,int fixerId) throws FixitException;
	
	
	/**
	 * used to delete Favourite Fixer by member
	 * 
	 * @param userId - userId of a user from sesson. 
	 * @param fixerId - userId of a fixer.
	 * 
	 * @return 1 if success.
	 */
	public Integer deleteFavouriteFixer(int userId,int fixerId) throws FixitException;
	
	public FavouriteFixer findFavFixerByIds(int userId, int fixerId) throws FixitException;
	
}
