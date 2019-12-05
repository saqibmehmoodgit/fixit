package com.fixit.dao;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.FavouriteFixer;

public interface FavouriteFixerDao extends JpaDao<FavouriteFixer> {
	/**
	 * method used to delete a fixer.
	 * 
	 * @param userId
	 *            - user id of a particular user(Fixer).
	 */
	public Integer deleteUserByFixerId(int userId) throws DataAccessException;

	/**
	 * @param userId
	 *            - user id of a particular user(Fixer).
	 * @param fixerId
	 *            - userId of a particular user(member).
	 */
	public Integer deleteFavouriteFixerByFixerId(int userId, int fixerId) throws DataAccessException;
	
	public FavouriteFixer findFavFixerByIds(int userId, int fixerId) throws DataAccessException;
	
	
}
