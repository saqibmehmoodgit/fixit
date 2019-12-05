package com.fixit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.QueryExpireDao;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

@Service("QueryExpireService")
@Transactional
public class QueryExpireServiceImpl implements QueryExpireService {

	@Autowired
	private RandomConverter randomConverter;

	@Autowired
	private QueryExpireDao queryExpireDao;

	@Override
	@Transactional
	public Set<QueryExpire> saveQueryExpire(Set<User> fixers, Integer memberId,
			Integer queryId,String baseUrl) throws FixitException {
		
       Set<QueryExpire> queryExpireSet=new HashSet<QueryExpire>();
		QueryExpire queryExpire = null;
		try {
			for (User user : fixers) {
				queryExpire = new QueryExpire();
				queryExpire.setQueryId(queryId);
				queryExpire.setMemberId(memberId);
				queryExpire.setFixerId(user.getUserId());
				queryExpire = queryExpireDao.store(queryExpire);
				String hashcode = randomConverter.stringASCIIGenerator(queryExpire.getQueryExpireId());
				queryExpire.setHashcode(hashcode);
				queryExpire.setInternalUrl(randomConverter
						.hashStringToUrlConverter(hashcode,baseUrl));
				queryExpireSet.add(queryExpire);
			}
		} catch (Exception e) {
			throw new FixitException("queryExpire.notsaved",
					"queryExpire.notsaved");
		}
		queryExpireDao.flush();
		return queryExpireSet;
	}
	public QueryExpire findQueryExpireByFixerIdAndhashcode(Integer fixerId,String hashcode) throws FixitException{
		QueryExpire queryExpire =queryExpireDao.findQueryExpireByFixerIdAndhashCode(fixerId, hashcode); 
		return queryExpire;
	}
	
	
	@Override
	@Transactional
	public String deleteQueryExpireByDaysInterval(String currentDate,int days)
			throws FixitException {
		Set<QueryExpire> queryExpireSet = queryExpireDao.findQueryExpireByDaysInterval(currentDate, days);
		String result = queryExpireDao.deleteDataByDaysIinterval(queryExpireSet);
		return result;
	}
	
	
}
