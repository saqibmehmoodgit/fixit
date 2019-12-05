package com.fixit.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.utility.FixitException;


	
	public interface QueryAppliedFixersDao extends JpaDao<QueryAppliedFixers>{

		List<QueryAppliedFixers> findFixerListByQueryId(Integer userId, Integer queryId, int startIndex, int maxResult);

		void updateAppliedFixerStatus(Integer fixerId, Integer queryId, String string);

		long getAppliedQueriesCount(int userId);

		List<QueryAppliedFixers> findFixerListByFixerId(int userId, int startIndex, int pAGE_SIZE);

		Set<com.fixit.domain.vo.Query> findQuerApplied(int userId, int startResult, int max) throws DataAccessException;
		
		public int deleteAppliedFixerByQueryId(Integer queryId) throws DataAccessException;		
		public int deleteAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException;		

	
		public long countAppliedFixerByQueryIdAndStatus(Integer queryId, String status) throws DataAccessException;		

}
