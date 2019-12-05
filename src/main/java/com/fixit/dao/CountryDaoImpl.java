package com.fixit.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.Country;
@Repository("CountryDao")
@Transactional
public class CountryDaoImpl extends AbstractJpaDao<Country> implements CountryDao{

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { Country.class }));

	public CountryDaoImpl() {
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
	public boolean canBeMerged(Country o) {
		return true;
	}


	@Override
	@Transactional
	public Set<Country> findAllCountry() throws DataAccessException {
		try{
			Query query = createNamedQuery("findAllCountry",-1, -1);
			return new LinkedHashSet<Country>(query.getResultList());
		}catch(Exception e){
			return null;	
		}
		
	}

	
}
