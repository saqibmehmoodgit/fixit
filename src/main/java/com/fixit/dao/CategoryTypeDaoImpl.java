package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.CategoryType;
import com.fixit.utility.FixitVariables;

@Repository("CategoryTypeDao")
@Transactional
public class CategoryTypeDaoImpl extends AbstractJpaDao<CategoryType> implements CategoryTypeDao{

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { CategoryType.class }));

	public CategoryTypeDaoImpl() {
		super();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(CategoryType o) {
		return true;
	}

	@Override
	public CategoryType findCategoryTypeByCatId(Integer catId) throws DataAccessException {
		return findCategoryTypeByCatId(catId, -1, -1);
	}

	@Transactional
	public CategoryType findCategoryTypeByCatId(Integer catId, int startResult,int max) throws DataAccessException {
		 try {
			 Query query = createNamedQuery("findCategoryTypeById",startResult, max, catId);
			 return (CategoryType) query.getSingleResult();
		 } catch (Exception  nre) {
			 nre.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public Set<CategoryType> findAllCategoryType(String status) throws DataAccessException {
		return findAllCategoryType(-1,-1 ,status);
	}

	@Override
	@Transactional
	public Set<CategoryType> findAllCategoryType(int startResult, int max , String status) throws DataAccessException {
		 try {
			 Query query = null;
			 if(status.equals(FixitVariables.WITH_NOT_SURE)){
				  query = createNamedQuery("findAllCategoryType",startResult, max);
			 }else{
				  query = createNamedQuery("findAllCategoryTypeWithoutNotSure",startResult, max);
			 }
			 
			 return new LinkedHashSet<CategoryType>( query.getResultList());
		 } catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<CategoryType> findAllParentCategory(String status) throws DataAccessException {
		return findAllParentCategory(-1,-1,status);
	}

	@Override
	@Transactional
	public Set<CategoryType> findAllParentCategory(int startResult, int max,String status) throws DataAccessException {
		try {
			Query query = null;
			if(status.equals(FixitVariables.WITH_NOT_SURE)){
			  query = createNamedQuery("findAllParentCategory",startResult, max);
			}else{
			  query = createNamedQuery("findAllParentCategoryWithoutNotSure",startResult, max);
			}
			 return new LinkedHashSet<CategoryType>( query.getResultList());
		 } catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public CategoryType findQueryParentCategory(int queryId) throws DataAccessException {
		
		try{
			String nativeQuery =  "select * from cat_type where cat_id = (select parent_id from " +
					"cat_type where cat_id= (select  cat_id  from query_cat where query_id = '"+queryId+"' limit 1 ))";
	       Query query = entityManager.createNativeQuery(nativeQuery, "categoryTypeMapping");
	        return (CategoryType) query.getSingleResult();
	    }catch(Exception e){
			return null;	
		}	
		
		
	}

	@Override
	public List<String> findAllParentCategoryByCatId(List<Integer> catId) throws DataAccessException {
		Query query = createNamedQuery("findAllParentCategoryByCatId", -1, -1, catId);
		List<String> catName = query.getResultList();
		return catName;
	}

		
	
	

}
