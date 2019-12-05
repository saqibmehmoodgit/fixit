package com.fixit.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.UserIndustryDao;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

@Service("UserIndustryService")
@Transactional
public class UserIndustryServiceImpl implements UserIndustryService{

	@Autowired
	private UserIndustryDao userIndustryDao;
	
	@Override
	public UserIndustry saveIndustry(UserIndustry userIndustry) throws FixitException {
		 try{
				return userIndustryDao.store(userIndustry);
			  }catch(Exception e){
				 throw new FixitException("","");
			  }
	}

	
	@Override
	@Transactional
	public Set<UserIndustry> findUserIndustryByUserId(int userId) throws FixitException {
	  try{
		return userIndustryDao.findUserIndustryByUserId(userId);
	  }catch(Exception e){
		 throw new FixitException("","");
	  }
	}

	@Override
	@Transactional
	public UserIndustry findUserIndustryByUserIdandIndstId(int userId,int indstId) throws FixitException {
		 try{
			return userIndustryDao.findUserIndustryByUserIdandIndstId(userId , indstId);
	      }catch(Exception e){
			 throw new FixitException("","");
	    }
	}

	
	@Override
	public int deleteUserIndustry(int userId, List<Integer> indstIds) throws FixitException {
		 try{
				return userIndustryDao.deleteUserIndustry(userId , indstIds);
		      }catch(Exception e){
				 throw new FixitException("","");
		    }
	}


	@Override
	public int deleteUserIndustryByUserIdandIndstId(Integer userId, Integer indstId) throws FixitException {
		 try{
				return userIndustryDao.deleteUserIndustryByUserIdandIndstId(userId , indstId);
		      }catch(Exception e){
				 throw new FixitException("","");
		    }
	}

	
	
}
