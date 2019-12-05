package com.fixit.dao;

import java.util.List;
import java.util.Set;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.utility.FixitException;

public interface QueryAuditDao extends JpaDao<QueryAudit> {
	/**
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @return count of unresolved Issue.
	 */
	public int findUnresolvedIssueByFIxerId(int fixerId) throws DataAccessException;

	/**
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param startResult
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return count of unresolved Issue.
	 * 
	 */
	public int findUnresolvedIssueByFIxerId(int fixerId, int startResult, int max) throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @return 1 if success.
	 * 
	 */
	public int deleteQueryAuditByQueryId(int queryId);

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param startResult
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return List of object of QueryAudit based on queryId and fixerId.
	 */
	public List<QueryAudit> getQueryRelatedMessages(int queryId, int fixerId, int startResult, int max)
			throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param memberId
	 *            - userId of a particular user(member).
	 * @param currentStatus
	 *            - status of a particular query.
	 * @return object of QueryAudit based on queryId,memberId and fixerId.
	 * 
	 */
	public QueryAudit getQueryAuditBasedOnStatus(Integer memberId, Integer fixerId, Integer queryId,
			String currentStatus) throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param memberId
	 *            - userId of a particular user(member).
	 * @param currentStatus
	 *            - status of a particular query.
	 * @param msgFrom
	 *            - message from user.
	 * @return object of QueryAudit based on queryId,memberId and fixerId.
	 * 
	 */
	public QueryAudit getQueryAuditBasedOnStatusAndMsgFrom(Integer memberId, Integer fixerId, Integer queryId,
			String currentStatus, String msgFrom) throws DataAccessException;

	/**
	 * @param queryId
	 *            - id of a particular Query.
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param startResult
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return List of object of QueryAudit based on queryId and fixerId.
	 * 
	 */
	public List<QueryAudit> getAllQueryRelatedMessages(int queryId, int fixerId, int startResult, int max)
			throws DataAccessException;

	/**
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param status
	 *            - status of a query.
	 * @return count of query resovled by fixer.
	 */
	public long findAllQueryCountByFixerId(int fixerId, String status) throws FixitException;

	/**
	 * @param fixerId
	 *            - userId of a particular user(fixer).
	 * @param status
	 *            - status of a query.
	 * @return List of object of QueryAuditbased on status and fixerId.
	 */
	public List<QueryAudit> findAllQueryFixerIdAndSatus(int fixerId, String status) throws FixitException;

	/**
	 * @param userId
	 *            - userId of a particular user(fixer).
	 * @param status
	 *            - status of a query.
	 * @return count of all query based on userId.
	 */
	public long findAllQueryUserIdAndSatus(int userId, String status) throws FixitException;

	/**
	 * @param startIndex
	 *            - the first row to be returned.
	 * @param maxResult
	 *            - maxResults the maximum number of rows to be returned.
	 * @return Set of all object of QueryAudit
	 */
	public Set<QueryAudit> findAllQueryAudit(Integer startIndex, Integer maxResult) throws DataAccessException;

	/**
	 * used to count all query audit.
	 * 
	 */
	public long findAllQueryAuditCount() throws DataAccessException;

	public long getUnreadMessageCount(int queryId, int fixerId,String userType)throws FixitException;

	public void updateMessagesToRead(Integer queryId, Integer fixerId,String userType)throws FixitException;;
}
