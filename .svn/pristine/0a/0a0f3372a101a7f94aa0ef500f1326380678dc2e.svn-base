package com.fixit.service;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

public interface UserIndustryService {
	
	
	public UserIndustry saveIndustry(UserIndustry userIndustry) throws FixitException;
	
	
    /**
     * used to find the user industries based on userId.
     * 
     *@param userId -  Take integer as a argument , userId of a user. 
     *
     *@return Set
     */
	public Set<UserIndustry> findUserIndustryByUserId(int userId) throws FixitException;
	
	/**
	 * used to find the user industries based on userId and industryId.
     *@param userId -  Take integer as a argument , userId of a user. 
     *@param indstId -  Take integer as a argument, indstId of a Industry.
     *
     *@return return UserIndustry
     */
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId, int indstId) throws FixitException;
	
	public int deleteUserIndustry(int userId ,List<Integer> indstIds) throws FixitException;
	
	public int deleteUserIndustryByUserIdandIndstId(Integer userId , Integer indstId) throws FixitException;
	
	
	
	
}
