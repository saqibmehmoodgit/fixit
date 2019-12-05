package com.fixit.dao;

import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.Country;

public interface CountryDao extends JpaDao<Country>{
/**
 * thi method used to get all country 
 * 
 *@return SET(collection) of country type  
 * 
 */
	 public Set<Country> findAllCountry() throws DataAccessException;
}
