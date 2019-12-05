package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.vo.QueryFiles;
import com.fixit.utility.FixitException;

public interface QueryFilesDao extends JpaDao<QueryFiles> {
	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @return Set of object of query
	 */
	public Set<QueryFiles> findDocumentsByQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @param fileName
	 *            - list of file name associated with queries.
	 * @return 1 if success
	 */
	public int deleteDocumentsByQueryIds(Integer queryId, List<String> fileNames) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @param fileName
	 *            - file name associated with query
	 * @return object of QueryFiles.
	 */
	public QueryFiles findDocumentsByQueryIdandName(Integer queryId, String fileName) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @return 1 if success
	 */
	public int deleteAllDocumentsByQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * 
	 */
	public int deleteAllDataByQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @return 1 if success
	 */
	public Set<QueryFiles> findUrlLinksByQueryId(Integer queryId) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @param urlNames
	 *            - list of url of queries.
	 * @return 1 if success
	 */
	public int deleteUrlLinksByQueryIds(Integer queryId, List<String> urlNames) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @param urlNames
	 *            - url of a query.
	 * @return object of QueryFiles.
	 */
	public QueryFiles findUrlLinksByQueryIdandName(Integer queryId, String urlNames) throws DataAccessException;

	/**
	 * @param queryId
	 *            - query Id of a particular query.
	 * @return Integer value.
	 */
	public int deleteAllUrlLinksByQueryId(Integer queryId) throws DataAccessException;
	
	public Integer deleteQueryFilesByFileUniqueCode(Integer queryId, String fileUniqueCode) throws FixitException;


}
