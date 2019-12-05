package com.fixit.dao;

import java.util.List;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface QueryFixersDao extends JpaDao<QueryFixers>{
	
	public List<QueryFixers> getQueryFixersBasedOnQueryId(Integer queryId,int startIndex , int maxResult) throws DataAccessException;
	
	public Integer deleteQueryFixersBasedOnQueryId(Integer queryId) throws DataAccessException;
	
	public QueryFixers findQueryFixersBasedOnQueryIdAndFixerId(Integer queryId,Integer userId,int startIndex , int maxResult) throws FixitException;

}
