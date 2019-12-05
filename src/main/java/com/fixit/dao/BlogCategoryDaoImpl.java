package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.fixit.domain.vo.Blog;
import com.fixit.domain.vo.BlogCategory;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;
import com.google.inject.persist.Transactional;

@Repository("BlogCategoryDao")
@Transactional
public class BlogCategoryDaoImpl extends AbstractJpaDao<BlogCategory> implements
		BlogCategoryDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { BlogCategory.class }));

	public BlogCategoryDaoImpl() {

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

	public boolean canBeMerged(BlogCategory o) {
		
		return true;
	}
	
	@Override
	public int deleteBlogCategoryByBlogId(int blogId) throws FixitException {
		Query query = createNamedQuery("deleteBlogCategoryByBlogId", -1, -1, blogId);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public List<BlogCategory> categoriesByBlogId(int blogId) throws FixitException {
		Query query = createNamedQuery("categoriesByBlogId", -1, -1, blogId);
		List<BlogCategory> blogCategories = query.getResultList();
		return blogCategories;
	}

	@Override
	public List<BlogCategory> blogByCategories(int userId,int catId , int min, int max) throws FixitException {
		Query query = createNamedQuery("blogByCategories", min, max, catId , userId);
		List<BlogCategory> blogCategories = query.getResultList();
		return blogCategories;
		
	}

	@Override
	public List<BlogCategory> findBlogByStatusAndCategories(int catId,
			String blogStatus, int min, int max) throws FixitException {
		Query query = createNamedQuery("adminBlogListByCategories", min, max, catId);
		List<BlogCategory> blogCategories = query.getResultList();
		return blogCategories;
		}

	@Override
	public List<BlogCategory> findBlogByUserIdStatusAndCategories(int catId,
			String blogStatus, int userId, int min, int max)
			throws FixitException {
		Query query = createNamedQuery("adminBlogListByCategoriesAndStatus", min, max, catId, blogStatus,userId);
		List<BlogCategory> blogCategories = query.getResultList();
		return blogCategories;	
	}

}
