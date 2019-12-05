package com.fixit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.CountryDao;
import com.fixit.domain.vo.Country;
import com.fixit.utility.FixitException;

@Service("CountryService")
@Transactional
public class CountryServiceImpl implements CountryService{
	
	@Autowired
	private CountryDao countryDao;

	@Override
	public Set<Country> findAllCountry() throws FixitException{
		
		try{
		 return  countryDao.findAllCountry();
		}catch(Exception e){
			throw new FixitException("","");
		}
	}

}
