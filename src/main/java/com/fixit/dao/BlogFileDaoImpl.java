package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.stereotype.Repository;

import com.fixit.domain.vo.Blog;
import com.fixit.domain.vo.BlogFiles;
import com.google.inject.persist.Transactional;

@Repository("BlogFileDao")
@Transactional
public class BlogFileDaoImpl extends AbstractJpaDao<BlogFiles> implements BlogFileDao{

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { BlogFiles.class }));

	public BlogFileDaoImpl() {
		
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
	public boolean canBeMerged(BlogFiles o) {
		
		return true;
	}

}
