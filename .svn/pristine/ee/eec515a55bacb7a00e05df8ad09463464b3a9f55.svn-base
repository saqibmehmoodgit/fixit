package com.fixit.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.CategoryTypeDao;
import com.fixit.dao.UserCategoryDao;
import com.fixit.dao.UserDao;
import com.fixit.domain.bo.UserCategoryBo;
import com.fixit.domain.vo.UserCategory;
import com.fixit.utility.FixitException;

@Service("FixerCategoryService")
@Transactional
public class UserCategoryServiceImp implements UserCategoryService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Autowired
	private UserCategoryDao userCategoryDao;
	
	

	@Override
	public UserCategory saveUserCat(UserCategoryBo userCategoryBo) {
		UserCategory userCategory = new UserCategory();
		userCategory.setUser(userDao.findUserByUserId(userCategoryBo.getUserId()));
		userCategory.setCategoryType(categoryTypeDao.findCategoryTypeByCatId(userCategoryBo.getCatId()));
		userCategory = userCategoryDao.store(userCategory);
		userCategoryDao.flush();
        return userCategory;
	}
	
	@Override
	@Transactional
	public Set<UserCategory> findUserCategoryByUserId(int userId) throws FixitException{
		  try{
				return userCategoryDao.findUserCategoryByUserId(userId);
			  }catch(Exception e){
				 throw new FixitException("","");
			  }
	}
	
	
	
	@Override
	@Transactional
	public Integer deleteUserCategory(Integer userId , List<Integer> categories) throws FixitException{
		 try{
				return userCategoryDao.deleteUserCategory(userId,categories);
			  }catch(Exception e){
				 throw new FixitException("","");
			  }
	}
	
	@Override
	@Transactional
	public UserCategory findUserCategoryByUserIdandcatId(int userId, int catId) throws FixitException{
		try{
			return userCategoryDao.findUserCategoryByUserIdandcatId(userId,catId);
		  }catch(Exception e){
			 throw new FixitException("","");
		  }
	}

	@Override
	public int deleteUserCategoryBasedOnUserIdandCatId(Integer userId, Integer catId) throws FixitException {
		try{
			return userCategoryDao.deleteUserCategoryBasedOnUserIdandCatId(userId,catId);
		  }catch(Exception e){
			 throw new FixitException("","");
		  }
	}
	
}
