package com.fixit.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.UserDeclineDao;
import com.fixit.domain.vo.UserDecline;
import com.fixit.utility.FixitException;

@Service("UserDeclineService")
@Transactional
public class UserDeclineServiceImpl implements UserDeclineService{

	@Autowired
	private UserDeclineDao userDeclineDao;
	
	@Override
	public UserDecline saveUserDecline(Integer userId, Integer queryId) throws FixitException {
		UserDecline userDecline;
	 try{
		 userDecline = new UserDecline();
		 userDecline.setUserId(userId);
		 userDecline.setQueryId(queryId);
		 userDecline = userDeclineDao.store(userDecline);
	 }catch(Exception e){
		 throw new FixitException("", "");
	 }
	 return userDecline;
	}

	@Override
	@Transactional
	public Set<Integer> findDeclineQueryByUserId(Integer userId) throws FixitException {
		try{
			Set<Integer> declineIds = new LinkedHashSet<Integer>();
			Set<UserDecline> userDeclines =	userDeclineDao.findDeclineQueryByUserId(userId);
			for(UserDecline userDecline : userDeclines){
				declineIds.add(userDecline.getQueryId());	
			}
			return  declineIds;
		}catch(Exception e){
			throw new FixitException("", "");
		}
	}
	@Override
	@Transactional
	public UserDecline findDeclineQueryByAndUserId(Integer userId,Integer queryId) throws FixitException{
		try{
			UserDecline userDecline =	userDeclineDao.findDeclineQuery(userId, queryId);
			
			return  userDecline;
		}catch(Exception e){
			throw new FixitException("", "");
		}
	}

}
