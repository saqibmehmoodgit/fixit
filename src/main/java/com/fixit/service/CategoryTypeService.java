package com.fixit.service;

import java.util.Set;

import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.UserCategory;
import com.fixit.utility.FixitException;

public interface CategoryTypeService {
	/**
	 * @param status
	 *            - status of a categories wns/wons
	 * @return list of object of categories.
	 */
	public Set<CategoryType> findAllCategoryType(String status) throws FixitException;

	/**
	 * @param status
	 *            - status of a categories wns/wons return list object of parent
	 *            of categories
	 */
	public Set<CategoryType> findAllParentCategory(String status) throws FixitException;

	/**
	 * @param userCategories
	 *            - Set object of type UserCategories
	 * 
	 * @return list of object of categories by catId.
	 */
	public Set<CategoryType> findCategoryTypeListFromcatIds(Set<UserCategory> userCategories) throws FixitException;

	/**
	 * @param catId
	 *            - category Id of a particular category
	 * @return object of CategoryType by category Id.
	 */
	public CategoryType findCategoryTypeByCatId(Integer catId) throws FixitException;
}
