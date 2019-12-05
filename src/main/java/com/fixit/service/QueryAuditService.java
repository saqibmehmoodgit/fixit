package com.fixit.service;

import java.util.List;
import java.util.Set;
import com.fixit.domain.bo.QueryAuditBo;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.utility.FixitException;

public interface QueryAuditService {
	/**
	 * 
	 * used to save query audit.
	 * @param queryAudit
	 *            - object of QueryAudit used to set
	 *            
	 *            @return QueryAudit
	 */
	public QueryAudit saveQueryAudit(QueryAudit queryAudit) throws FixitException;

	/**
	 * used to find unresolved issues based on fixerId.
	 * @param fixerId
	 *            - fixerId of a User(Fixer).
	 * 
	 * @return Integer
	 */
	public int findUnresolvedIssueByFIxerId(int fixerId) throws FixitException;

	/**
	 * used to save query detail message.
	 * @param queryAuditBo
	 *            - object of QueryAuditBo used to set
	 *            
	 *            @return QueryAuditBo
	 */
	public QueryAudit saveQueryDetailMessage(QueryAuditBo queryAudiotBo) throws FixitException;

	/**
	 * 
	 * used to get the query audit based on status.
	 * @param fixerId
	 *            - fixerId of a User(Fixer).
	 * @param memberId
	 *            - fixerId of a User(member).
	 * @param queryId
	 *            - queryId of a particular query.
	 * 
	 * @param currentStatus
	 *            - current status of a queryAudit.
	 *
	 *@return QueryAudit
	 */

	public QueryAudit getQueryAuditBasedOnStatus(Integer memberId, Integer fixerId, Integer queryId,
			String currentStatus) throws FixitException;

	/**
	 * 
	 * used to get the query detail messages.
	 * @param queryId
	 *            - queryId of a particular query.
	 * @param startIndex
	 *            - firstResult the first row to be returned
	 * @param max
	 *            - the maximum number of rows to be returned.
	 *            
	 *            @return List<QueryAudit>
	 */
	List<QueryAudit> getQueryDetailMessages(String queryId, int startIndex, int max) throws FixitException;


	/**
	 * used ot get the query audit based on satus and message from fixer.
	 * @param fixerId
	 *            - fixerId of a User(Fixer).
	 * @param memberId
	 *            - fixerId of a User(member).
	 * @param queryId
	 *            - queryId of a particular query.
	 * 
	 * @param currentStatus
	 *            - current status of a queryAudit.
	 * @param msgFrom - Take string as argument.
	 *
	 *@return Query Audit
	 */
	public QueryAudit getQueryAuditBasedOnStatusAndMsgFromFixer(Integer memberId, Integer fixerId, Integer queryId,
			String currentStatus, String msgFrom) throws FixitException;

	/**
	 * 
	 *used ot find all query count based on fixerId.
	 * @param fixerId
	 *            - fixerId of a User(Fixer).
	 * @param status
	 *            - status of query.
	 *            @return Long
	 */
	public long findAllQueryCountByFixerId(int fixerId, String status) throws FixitException;

	/**
	 * 
	 * used to find all queries based on userId and status.
	 * @param userId
	 *            - userId of a particular user.
	 * @param status
	 *            - status of query.
	 *            
	 *            @return Long
	 */
	public long findAllQueryUserIdAndSatus(int userId, String status) throws FixitException;

	/**
	 * 
	 * used to find all query audit data.
	 * @param startIndex
	 *            - firstResult the first row to be returned
	 * @param maxResults
	 *            - the maximum number of rows to be returned
	 *            
	 *            @return QueryAudit
	 */
	public Set<QueryAudit> findAllQueryAudit(Integer startIndex, Integer maxResult) throws FixitException;

	/**
	 * used to find the all queries count.
	 * 
	 * count all query audit
	 * 
	 * @return Long
	 */
	public long findAllQueryAuditCount() throws FixitException;
	List<QueryAudit> getQueryOpenDetailMessages(String queryId,int fixerId, int startIndex, int max) throws FixitException;

	public int updateMessagesToRead(Integer queryId, Integer fixerId,String userType);

}
