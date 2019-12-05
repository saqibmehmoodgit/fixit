package com.fixit.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface QueryFixersService {

	public void saveQueryFixers(Set<User> fixers,Query query) throws FixitException;
	
	public Map<String,String> getQueryFixersIdsAndNamesBasedOnQueryId(Integer queryId,int startIndex , int maxResult) throws FixitException;
	
	public List<QueryFixers> getQueryFixersBasedOnQueryId(Integer queryId,int startIndex , int maxResult) throws FixitException;
	
	public QueryFixers findQueryFixersBasedOnQueryIdAndFixerId(Integer queryId,Integer userId,int startIndex , int maxResult) throws FixitException;
	
	public Integer deleteQueryFixersBasedOnQueryId(Integer queryId) throws FixitException;

	Map<String, String> getQueryFixersIdsAndNamesBasedOnQueryIdNoBrackets(Integer queryId, int startIndex,
			int maxResult) throws FixitException;
	
}
