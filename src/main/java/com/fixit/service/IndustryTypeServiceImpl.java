package com.fixit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.IndustryTypeDao;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

@Service("IndustryTypeService")
@Transactional
public class IndustryTypeServiceImpl implements IndustryTypeService{
	
	@Autowired
	private IndustryTypeDao industryTypeDao;

	@Override
	@Transactional
	public Set<IndustryType> findAllIndustryType() throws FixitException {
		try{
		   return industryTypeDao.findAllIndustryType();	
		}catch(Exception e){
			return null;
		}
    }

	@Override
	@Transactional
	public Set<IndustryType> findIndustryTypeListFromIndstIds(Set<UserIndustry> userIndustries) throws FixitException{
		Set<IndustryType> industryTypes = new HashSet<IndustryType>();
		for(UserIndustry userIndustry : userIndustries){
			industryTypes.add(industryTypeDao.findIndustryTypeByIndstId(userIndustry.getIndustryType().getIndstId()));
		}
		return industryTypes;
	}
 
	public IndustryType findIndustryTypeByIndstId(int indstId) throws FixitException{
		try{
			   return industryTypeDao.findIndustryTypeByIndstId(indstId);	
			}catch(Exception e){
				return null;
			}
	}
	
}
