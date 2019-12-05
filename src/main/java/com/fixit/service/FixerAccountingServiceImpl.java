package com.fixit.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.FixerAccountingDao;
import com.fixit.domain.vo.FixerAccounting;
import com.fixit.utility.FixitException;

@Service("FixerAccountingService")
@Transactional
public class FixerAccountingServiceImpl implements FixerAccountingService{
	
	@Autowired
	private FixerAccountingDao fixerAccountingDao;

	@Override
	@Transactional
	public FixerAccounting saveFixerAccounting(FixerAccounting fixerAccounting) throws FixitException {
		try{
		fixerAccounting = fixerAccountingDao.store(fixerAccounting);
		return fixerAccounting;
		}catch(Exception e){
			throw new FixitException("", ""); 
		}
	}

	@Override
	@Transactional
	public Long findFixerUnpaidQueryCount(Integer fixerId) throws FixitException {
		try{
			
			return null;
			}catch(Exception e){
				throw new FixitException("", ""); 
			}
	}

	@Override
	@Transactional
	public Set<FixerAccounting> findFixerUnpaidQuery(Integer fixerId) throws FixitException {
		try{
			
			return null;
			}catch(Exception e){
				throw new FixitException("", ""); 
			}
	}

	@Override
	@Transactional
	public int updateFixerPaidStatus(Integer fixerId ,int status) throws FixitException{
		try{
			return fixerAccountingDao.updateFixerPaidStatus(fixerId, status);
			}catch(Exception e){
				throw new FixitException("", ""); 
			}
	}
}
