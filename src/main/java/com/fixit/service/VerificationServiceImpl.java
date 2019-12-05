package com.fixit.service;

import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.VerificationDao;
import com.fixit.domain.vo.Verification;
import com.fixit.utility.FixitException;

@Service("VerificationService")
@Transactional
public class VerificationServiceImpl implements VerificationService{
	
	@Autowired
	private VerificationDao verificationDao;
	
	
	@Override
	@Transactional
	public Set<Verification> findVerificationByUserId(Integer userId) throws FixitException{
		try{
			return verificationDao.findVerificationByUserId(userId);
		}catch(Exception e){
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public int deleteVerificationByUserId(Integer userId) throws FixitException {
		try{
			return verificationDao.deleteVerificationByUserId(userId);
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	@Transactional
	public Verification saveVerification(Verification verification) throws FixitException {
		try{
			 return verificationDao.store(verification);
		}catch(Exception e){
			return null;	
		}
		
	}

	@Override
	@Transactional
	public Verification findVerificationByHashCode(String hashCode,Calendar time) throws FixitException {
		try{
			return verificationDao.findVerificationByHashCode(hashCode,time);
		}catch(Exception e){
			return null;
		}
	}
}
