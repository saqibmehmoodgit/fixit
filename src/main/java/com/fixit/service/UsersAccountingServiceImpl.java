package com.fixit.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.UsersAccountingDao;
import com.fixit.domain.bo.UsersAccountingBo;
import com.fixit.domain.vo.UsersAccounting;
import com.fixit.utility.FixitException;


@Service("UserAccountingService")
@Transactional
public class UsersAccountingServiceImpl implements UsersAccountingService {

	@Autowired
	private UsersAccountingDao usersAccountingDao;
	
	
	@Override
	public UsersAccounting saveUserAccounting(UsersAccountingBo usersAccountingBo) throws FixitException {
		
		UsersAccounting usersAccounting=null;
		try{
		usersAccounting=new UsersAccounting();
		usersAccounting.setUser(usersAccountingBo.getUser());
		usersAccounting.setAmount(usersAccountingBo.getAmount());
		usersAccounting.setType(usersAccountingBo.getType());
		usersAccounting.setTime(Calendar.getInstance());
		usersAccounting=usersAccountingDao.store(usersAccounting);
		return usersAccounting;
		}catch(Exception e){
			return null;
		}
	}

}
