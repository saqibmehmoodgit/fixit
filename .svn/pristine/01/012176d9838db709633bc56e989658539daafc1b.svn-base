package com.fixit.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fixit.dao.CategoryTypeDao;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.UserCategory;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.utility.FixitException;

@Service("CategoryTypeService")
@Transactional
public class CategoryTypeServiceImpl implements CategoryTypeService {

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Override
	@Transactional
	public Set<CategoryType> findAllParentCategory(String status) throws FixitException {
		try {
			return categoryTypeDao.findAllParentCategory(status);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<CategoryType> findAllCategoryType(String status) throws FixitException {
		try {
			return categoryTypeDao.findAllCategoryType(status);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	
	@Override
	@Transactional
	public Set<CategoryType> findCategoryTypeListFromcatIds(Set<UserCategory> userCategories) throws FixitException{
		Set<CategoryType> categoryTypes = new HashSet<CategoryType>();
		for(UserCategory userCategory : userCategories){
			
			categoryTypes.add(categoryTypeDao.findCategoryTypeByCatId(userCategory.getCategoryType().getCatId()));
		}
		return categoryTypes;
	}

	@Override
	@Transactional
	public CategoryType findCategoryTypeByCatId(Integer catId) throws FixitException {
		try {
			return categoryTypeDao.findCategoryTypeByCatId(catId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
}
