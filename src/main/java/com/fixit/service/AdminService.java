package com.fixit.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.AdminMemberSearch;
import com.fixit.domain.bo.FixerPayment;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.QueryReviewBo;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface AdminService {
	/**
	 * 
	 * used to get the fixer based on their status.
	 * 
	 * @param fixerStatus
	 *            - Fixer Status takes String and can be A(Activated),D(DeActivated),R(Review).
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of fixers if success.
	 */
	
	public Set<User> loadAllFixerWithStatus(int startResult, int maxRows,
			String fixerStatus) throws FixitException;
	
	
	
	/**
	 * 
	 * used to get the issue list based on the fixer id.
	 * 
	 * @param fixerId
	 *            -Take Integer as a Fixer Id.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of queries if success.
	 */
	public Set<Query> fixedIssueListByFixerId(int fixerId,int startResult, int maxRows) throws FixitException;
	
	/**
	 * 
	 * used to get the count based on the fixer status.
	 * 
	* @param fixerStatus
	 *            - Fixer Status takes String and can be A(Activated),D(DeActivated),R(Review).
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return type long .
	 */
	public long loadAllFixerCountWithStatus(int startResult, int maxRows, String fixerStatus) throws FixitException;
	
	/**
	 * 
	 * used to update the fixer status.
	 * 
	* @param fixerStatus
	 *            - Fixer Status takes String and can be A(Activated),D(DeActivated),R(Review).
	 * @param userId
	 *            -Take Integer as an argument.
	 * @return return type Integer 1 if success .
	 */
	public int updateFixerStatus(int userId,
			String fixerStatus) throws FixitException;

	/**
	 * 
	 * used to load all queries which are currently in 'NotSure' Category.
	 * 
	 * @param fixerId
	 *            -Take Integer as an argument.
	 *  @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of queries  .
	 */
	public Set<Query> loadAllNotSureQueries(int startResult, int maxRows,
			int fixerId) throws FixitException;
	
	/**
	 * 
	 * used to load all queries based on the status.
	 * 
	 * @param status
	 *            -Take String as an argument.
	 *  @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of QueriesReviewBo.
	 */
	public Set<QueryReviewBo> loadAllReviewQueries(int startResult, int maxRows,
			String status) throws FixitException;
	
	/**
	 * 
	 * used to get the count based on the status.
	 * 
	* @param status
	 *            - Take String as an argument.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return type long .
	 */
	public long loadAllReviewQueriesCount(int startResult, int maxRows, String status) throws FixitException;
	
	/**
	 * 
	 * used to get the list of users based on their type and currentStatus.
	 * 
	* @param currentStatus
	 *            - Take String as an argument.
	 * @param userType
	 *            - Take String as an argument.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of users .
	 */
	public Set<User> getListOfFixerWithActivtedStatus(String currentStatus,String userType,int startIndex , int maxResult) throws FixitException;
	
	/**
	 * 
	 * used to get the queries which are not accepted.
	 * 
	* @param currentTime
	 *            - Take Calender as an argument.
	 * @param day
	 *            - Take Integer as an argument.
	* @param timeZone
	 *            - Take String as an argument.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of MemberDashboardBo .
	 */
	public Set<MemberDashboardBo> findMemberQueryNotAccepted(Calendar currentTime ,Integer day, String timeZone , int startIndex , int maxResult) throws FixitException;
	/**
	 * 
	 * used to get the queries which are in notSure category.
	 * 
	* @param currentTime
	 *            - Take Calender as an argument.
	 * @param catId
	 *            - Take Integer as an argument.
	* @param timeZone
	 *            - Take String as an argument.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxRows
	 *            - Maximum row fetch at a time.
	 * @return return Set of MemberDashboardBo .
	 */
	public Set<MemberDashboardBo> findQueryNotSureCat(Calendar currentTime, Integer catId , String timeZone , int startIndex , int maxResult) throws FixitException;
	/**
	 * 
	 * used to get the queries which are in notSure category.
	 * 
	 * @param catId
	 *            - Take Integer as an argument.
	 * @param startResult
	 *            - Result start from startResult.
	 * @param maxResult
	 *            - Maximum row fetch at a time.
	 * @return return Long .
	 */
	public long findQueryNotSureCat( Integer catId , int startIndex , int maxResult) throws FixitException;
	/**
	 * 
	 * used to get the search list users.
	 * 
	 * @param searchFieldText
	 *            - Take String as an argument.
	 * @param startIndex
	 *            - Result start from startResult.
	 * @param maxResult
	 *            - Maximum row fetch at a time.
	 * @return return Set<FixerSearchBo> .
	 */
	public Set<FixerSearchBo> searchListOfUsersAdmin(String searchFieldText,int startIndex , int maxResult) throws FixitException;
	/**
	 * 
	 * used to get the search list users based on categories selected..
	 * 
	 * @param categories
	 *            - Take List as an argument.
	 * @param searchFieldText
	 *            - Take String as an argument.
	 * @param startIndex
	 *            - Result start from startResult.
	 * @param maxResult
	 *            - Maximum row fetch at a time.
	 * @return return Set<FixerSearchBo> .
	 */
	 public Set<FixerSearchBo> searchListOfUserswithCategory(List<Integer> categories,String searchFieldText,int startIndex , int maxResult) throws FixitException;
	 /**
		 * 
		 * used to get the search list users based on searchFieldText.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @param startIndex
		 *            - Result start from startResult.
		 * @param maxResult
		 *            - Maximum row fetch at a time.
		 * @return return Set<FixerPayment> .
		 */
	 public Set<FixerPayment> searchListOfFixerUsingSearchText(String searchFieldText,int startIndex , int maxResult) throws FixitException;
	 /**
		 * 
		 * used to get the search list users based on searchFieldText.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @param startIndex
		 *            - Result start from startResult.
		 * @param maxResult
		 *            - Maximum row fetch at a time.
		 * @return return Set<AdminMemberSearch> .
		 */
	 public Set<AdminMemberSearch> searchListOfUserUsingSearchText(String searchFieldText,int startIndex , int maxResult) throws FixitException;
	 
	 /**
		 * 
		 * used to get the Set of  fixers based on searchFieldtext.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @param startIndex
		 *            - Result start from startIndex.
		 * @param maxResult
		 *            - Maximum row fetch at a time.
		 * @return return Integer .
		 */
	 public Set<User> searchListOfUserFixersUsingSearchText(String searchFieldText,int startIndex , int maxResult) throws FixitException;
	 
	 /**
		 * 
		 * used to get the Set of  all Chat Users based on searchFieldtext.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @param startIndex
		 *            - Result start from startIndex.
		 * @param maxResult
		 *            - Maximum row fetch at a time.
		 * @return return Integer .
		 */
	 public Set<User> searchListOfChatUserUsingSearchText(String searchFieldText,int startIndex , int maxResult) throws FixitException;
	 
	 /**
		 * 
		 * used to get the count of fixers based on searchFieldtext.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @return return Integer .
		 */
	 public Integer searchListOfFixerUsingSearchCount(String searchFieldText) throws FixitException;
	 
	 /**
		 * 
		 * used to get the count of users based on searchFieldtext.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @return return Long .
		 */
	 public Long searchListOfUserUsingSearchCount(String searchFieldText) throws FixitException;
	 
	 /**
		 * 
		 * used to get the count of users based on searchFieldtext for chat.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @return return Long .
		 */
	 public Long searchListOfChatUserUsingSearchCount(String searchFieldText) throws FixitException;
	 
	 
	 
	 /**
		 * 
		 * used to get the count of fixers based on searchFieldtext.
		 * 
		 * @param searchFieldText
		 *            - Take String as an argument.
		 * @return return Long .
		 */
	 public Long searchListOfUserFixersUsingSearchCount(String searchFieldText) throws FixitException;
	 /**
		 * 
		 * used to get the list of fixers to whom Admin has to pay.
		 * 
		 * @param startIndex
		 *            - Result start from startIndex.
		 * @param maxResult
		 *            - Maximum row fetch at a time.
		 * @return return List .
		 */
	 public List<FixerPayment> fixersWithAmountToPaid (Integer startIndex , Integer maxResult) throws FixitException;
	 /**
		 * 
		 * used to get the count of fixers to whom Admin has to pay.
		 * @return return Integer .
		 */
	 public Integer fixersWithAmountToPaidCount() throws FixitException;
	 /**
		 * 
		 * used to get all the Members.
		 * 
		 * @param startResult
		 *            - Result start from startResult.
		 * @param maxRows
		 *            - Maximum row fetch at a time.
		 * @return return Set of users .
		 */
	 public Set<User> getAllMember(int startResult, int maxRows) throws FixitException;
	 
	 /**
		 * 
		 * used to get all the Fixers.
		 * 
		 * @param startResult
		 *            - Result start from startResult.
		 * @param maxRows
		 *            - Maximum row fetch at a time.
		 * @return return Set of users .
		 */
	 public Set<User> getAllFixers(int startResult, int maxRows) throws FixitException;
	
	 public Map<String , Object> asugData(Integer year, Integer month)throws FixitException;
	
	 public int updateFixerRatingByAdmin(int rating, int userId) throws FixitException;

}
