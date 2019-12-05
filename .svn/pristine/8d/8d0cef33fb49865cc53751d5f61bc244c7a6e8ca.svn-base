package com.fixit.service;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.AppliedFixersListBo;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface QueryAppliedFixersService {
	
	public QueryAppliedFixers saveAppliedFixer(User fixer,Query query) throws FixitException;
	public List<AppliedFixersListBo> findFixerListByQueryId(Integer member,Integer query , int startIndex,int maxResult ) throws FixitException;
	public void saveFixerDecline(Integer fixerId, Integer queryId)throws FixitException;
	public long getAppliedQueriesCount(int userId)throws FixitException;
	public List<QueryAppliedFixers> getAppliedQueries(int userId, int startIndex, int pAGE_SIZE)throws FixitException;
	public int deleteAppliedFixerByQueryId(Integer queryId) throws DataAccessException ;
	public long countAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException;		

	public int deleteAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException;		


}
