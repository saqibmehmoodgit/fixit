package com.fixit.service;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.UserCategoryBo;
import com.fixit.domain.vo.UserCategory;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

public interface UserCategoryService {

	/**
	 * used to save user categories.
	 * 
	 * @param fixerBo
	 *            - object of UserCategoryBo uer to set fixerCatId,userId,catId,
	 * 
	 * @return return UserCategory
	 */
	public UserCategory saveUserCat(UserCategoryBo fixerBo);

	/**
	 *    used to find the categories by userId.
	 *    
	 * @param userId
	 *            - userId of a particular user.
	 * @return return Set(collection) of type UserCategory
	 * 
	 */
	public Set<UserCategory> findUserCategoryByUserId(int userId) throws FixitException;
	
	
	/**
	 * 
	 * @param userId
	 * @param categories
	 * @return
	 */
	public Integer deleteUserCategory(Integer userId , List<Integer> categories) throws FixitException;
	
	/**
	 * @param userId
	 *            - userId of a particular user.
	 * @param catId
	 *            - category Id(catID) of a category.
	 * @return object of UserCategory.
	 */
	public UserCategory findUserCategoryByUserIdandcatId(int userId, int catId) throws FixitException;
	
	public int deleteUserCategoryBasedOnUserIdandCatId(Integer userId,Integer catId) throws FixitException;
	
	
}
