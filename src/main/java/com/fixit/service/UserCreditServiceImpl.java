package com.fixit.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.UserCreditDao;
import com.fixit.dao.UserDao;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCredit;
import com.fixit.utility.FixitException;

@Service("UserCreditService")
@Transactional
public class UserCreditServiceImpl  implements UserCreditService{

	@Autowired
	private UserCreditDao userCreditDao;

	@Override
	@Transactional
	public long getPoints(int userId) {
		
		try{
		return userCreditDao.findUserCreditPoints(userId);
		}catch(Exception e){
		return -1;
		}
	}

	@Override
	public int updatePoints(int userId,long points) {
		
		try{
			return userCreditDao.updateUserCreditPoints(userId,points);
			}catch(Exception e){
			return -1;
			}
	}

	@Override
	@Transactional
	public long getCreditCounts(int userId) {
		
		try{
			return userCreditDao.getCreditCountsByUserId(userId);
			}catch(Exception e){
			return -1;
			}
	}

	@Override
	@Transactional
	public UserCredit saveUserCredits(User user,Long points) throws FixitException {
		
		UserCredit userCredit=null;
		double intialUserCredits=0;
		try{
		userCredit=new UserCredit();
		userCredit.setUser(user);
		
		userCredit.setPoints(points);
		userCredit.setCreatedAt(Calendar.getInstance());
		userCreditDao.store(userCredit);
		return userCredit;
		}catch(Exception e){
			return null;
		}
		
	}
}
