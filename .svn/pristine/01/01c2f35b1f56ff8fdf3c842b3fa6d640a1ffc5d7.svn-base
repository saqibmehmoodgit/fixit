package com.fixit.service;

import java.util.Set;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

public interface IndustryTypeService {
	
	/**
	 * 
	 * used ot find all the industries type.
	 * Used to find all Industry type.
	 * @return Set of IndustryType. 
	 */
   public Set<IndustryType> findAllIndustryType() throws FixitException;

	/**
	 * used to find the industries type list from indstIds.
	 * 
	 * @param userIndustries- Set of IndustryType of a user.
	 * 
	 * @return Set<IndustryType>
	 */
   public Set<IndustryType> findIndustryTypeListFromIndstIds(Set<UserIndustry> userIndustries) throws FixitException;

   public IndustryType findIndustryTypeByIndstId(int indstId) throws FixitException;
}
