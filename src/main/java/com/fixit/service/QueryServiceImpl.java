package com.fixit.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.dao.CategoryTypeDao;
import com.fixit.dao.FixerAccountingDao;
import com.fixit.dao.FixerRatingDao;
import com.fixit.dao.QueryAuditDao;
import com.fixit.dao.QueryCategoryDao;
import com.fixit.dao.QueryDao;
import com.fixit.dao.QueryFilesDao;
import com.fixit.dao.QueryFixersDao;
import com.fixit.dao.UserDao;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.PieChartData;
import com.fixit.domain.bo.QueryBo;
import com.fixit.domain.bo.QueryFilesBo;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.domain.vo.QueryCategory;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.User;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.TimeDiffUtility;

@Service("QueryService")
@Transactional
public class QueryServiceImpl implements QueryService {

	@Autowired
	QueryDao queryDao;

	@Autowired
	FixerRatingDao fixerRatingDao;

	@Autowired
	FixerAccountingDao fixerAccountingDao;

	@Autowired
	QueryAuditDao queryAuditDao;

	@Autowired
	QueryCategoryDao queryCategoryDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CategoryTypeDao categoryTypeDao;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryAppliedFixersService appliedFixersService;
	
	@Autowired
	private UserDeclineService userDeclineService;

	@Autowired
	private EmailServcie emailServcie;

	@Autowired
	private QueryFilesDao queryFilesDao;

	@Autowired
	private RandomConverter randomConvertor;

	/**
	 * update Query
	 */
	@Override
	@Transactional
	public Query UpdateQuery(Query existingQuery) throws FixitException {
		try {
			existingQuery = queryDao.store(existingQuery);
			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(existingQuery.getFixerId());
			queryAudit.setMessage(DbConstants.MSG_WORKING);
			queryAudit.setMsgFrom(DbConstants.FIXER);
			queryAudit.setStatus(DbConstants.STATUS_WORKING);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);
		} catch (Exception e) {
			throw new FixitException("query.notUpdated", "query.notUpdated");
		}
		return existingQuery;
	}

	@Override
	@Transactional
	public Query UpdateQueryToClosed(Query existingQuery) throws FixitException {
		try {
			existingQuery = queryDao.store(existingQuery);

		} catch (Exception e) {
			throw new FixitException("query.notUpdated", "query.notUpdated");
		}
		return existingQuery;
	}

	/**
	 * save Query
	 */

	@Override
	@Transactional()
	public Query saveQuery(QueryBo queryBo, String path, Integer userId) throws FixitException {
		Query existingQuery = null;
		QueryAudit audit = null;
		
		try {
			existingQuery = queryDao.findQueryByQueryId(queryBo.getQueryId());
			if (existingQuery != null) {
				existingQuery.setFixerId(0);
				
				existingQuery.setQueryContent(queryBo.getQueryContent());
				existingQuery.setQueryTitle(queryBo.getQueryTitle());
				existingQuery.setQueryCredits(queryBo.getQueryCredits());
				Calendar queryDeadLineDate = TimeDiffUtility.convertStringtoCalender(queryBo.getQueryDeadlineDate(),"MM/dd/yyyy HH:mm:ss",queryBo.getTimeZone());
				existingQuery.setQueryDeadlineDate(queryDeadLineDate);
				
				
				
				Calendar dateRaised = Calendar.getInstance();
				existingQuery.setDateRaised(dateRaised);
				existingQuery = queryDao.store(existingQuery);
				if (existingQuery != null) {
					if (existingQuery != null) {

						// added query Category9

						// added query Audit
						audit = new QueryAudit();
						audit.setQuery(existingQuery);
						audit.setCustomerId(existingQuery.getUser().getUserId());

						audit.setFixerId(existingQuery.getFixerId());
						audit.setMessage(DbConstants.MSG_OPEN);
						audit.setMsgFrom(DbConstants.CUSTOMER);
						audit.setStatus(DbConstants.STATUS_OPEN);
						Calendar auditDate = Calendar.getInstance();
						audit.setAuditDate(auditDate);
						audit = queryAuditDao.store(audit);

						List<Integer> categories = queryBo.getCategories();
						queryCategoryDao.deleteQueryCategoryByQueryId(queryBo.getQueryId(), categories);
						QueryCategory queryCategory = null;
						
						for (Integer cat : categories) {
							queryCategory = queryCategoryDao.findQueryCategoryByQueryIdandCatId(queryBo.getQueryId(),
									cat);
							if (queryCategory == null) {
								queryCategory = new QueryCategory();
								queryCategory.setQuery(existingQuery);
								queryCategory.setCategoryType(categoryTypeDao.findCategoryTypeByCatId(cat));
								queryCategory = queryCategoryDao.store(queryCategory);
							}
						}
						
						List<String> filenames = queryBo.getDocuments();
						if (queryBo.getDocuments() != null && queryBo.getDocuments().size() > 0) {
							queryFilesDao.deleteDocumentsByQueryIds(queryBo.getQueryId(), filenames);
							QueryFiles queryFile = null;

							for (String fileName : filenames) {
								queryFile = queryFilesDao.findDocumentsByQueryIdandName(queryBo.getQueryId(), fileName);
								if (queryFile == null) {
									if (FileUpload.isValidFile(fileName, userId)) {
										FileUpload.moveFileFromUserToQueryFolder(fileName, userId,
												existingQuery.getQueryId());
										FileUpload.deleteFileFromUserFolder(fileName, userId);
										queryFile = new QueryFiles();
										queryFile.setFileName(fileName);
										queryFile.setFileUniqueCode("D" + TimeDiffUtility.getUniqueString());
										queryFile.setFileUrl(DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
												+ DbConstants.DOCUMENTS_FOLDER + DbConstants.SUFFIX
												+ DbConstants.USER_FOLDER + userId + DbConstants.SUFFIX
												+ DbConstants.QUERY_FOLDER + existingQuery.getQueryId()
												+ DbConstants.SUFFIX + fileName);
										queryFile.setQuery(existingQuery);
										queryFile.setFileType(DbConstants.DOCUMENT);
										queryFilesDao.store(queryFile);

										audit = new QueryAudit();
										audit.setFixerId(existingQuery.getFixerId());
										audit.setCustomerId(existingQuery.getUser().getUserId());
										audit.setMessage(DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
												+ DbConstants.DOCUMENTS_FOLDER + DbConstants.SUFFIX
												+ DbConstants.USER_FOLDER + userId + DbConstants.SUFFIX
												+ DbConstants.QUERY_FOLDER + existingQuery.getQueryId()
												+ DbConstants.SUFFIX + fileName);
										audit.setMsgFrom(DbConstants.CUSTOMER);
										audit.setQuery(existingQuery);
										audit.setStatus(DbConstants.STATUS_WORKING_DOCUMENT);
										Calendar currentTime = Calendar.getInstance();
										audit.setAuditDate(currentTime);
										queryAuditDao.store(audit);

									}
								}
							}
						} else {
							queryFilesDao.deleteAllDocumentsByQueryId(queryBo.getQueryId());
						}

						

					}

				}

			} else {
				existingQuery = new Query();
				existingQuery.setUser(userDao.findUserByUserId(userId));
				existingQuery.setFixerId(0);
				
				Calendar queryDeadLineDate = TimeDiffUtility.convertStringtoCalender(queryBo.getQueryDeadlineDate(),"MM/dd/yyyy HH:mm:ss",queryBo.getTimeZone());
				existingQuery.setQueryDeadlineDate(queryDeadLineDate);
				existingQuery.setQueryTitle(queryBo.getQueryTitle());
				existingQuery.setQueryContent(queryBo.getQueryContent());
				existingQuery.setQueryCredits(queryBo.getQueryCredits());
				existingQuery.setCurrentStatus(DbConstants.STATUS_OPEN);
				Calendar dateRaised = Calendar.getInstance();
				existingQuery.setDateRaised(dateRaised);
				existingQuery = queryDao.store(existingQuery);
				existingQuery.setHashcode(randomConvertor.alphaNumericGenerator(existingQuery.getQueryId()));

				if (existingQuery != null) {

					// added query Audit
					audit = new QueryAudit();
					audit.setQuery(existingQuery);
					audit.setCustomerId(existingQuery.getUser().getUserId());
					audit.setFixerId(existingQuery.getFixerId());
					audit.setMessage(DbConstants.MSG_OPEN);
					audit.setMsgFrom(DbConstants.CUSTOMER);
					audit.setStatus(DbConstants.STATUS_OPEN);
					Calendar auditDate = Calendar.getInstance();
					audit.setAuditDate(auditDate);
					audit = queryAuditDao.store(audit);

					// added query Category

					List<Integer> categories = queryBo.getCategories();
					QueryCategory queryCategory = null;
					
					for (Integer cat : categories) {
						queryCategory = new QueryCategory();
						
						queryCategory.setCategoryType(categoryTypeDao.findCategoryTypeByCatId(cat));
						
						queryCategory = queryCategoryDao.store(queryCategory);
						queryCategory.setQuery(existingQuery);
						
					}
					


					QueryFiles queryFiles = null;
					if (queryBo.getDocuments() != null && queryBo.getDocuments().size() > 0) {
						FileUpload.createQueryFolderOnServer(existingQuery.getQueryId(), userId);
						for (String docName : queryBo.getDocuments()) {
							if (FileUpload.isValidFile(docName, userId)) {
								FileUpload.moveFileFromUserToQueryFolder(docName, userId, existingQuery.getQueryId());
								FileUpload.deleteFileFromUserFolder(docName, userId);
								queryFiles = new QueryFiles();
								queryFiles.setQuery(existingQuery);
								queryFiles.setFileUniqueCode("D" + TimeDiffUtility.getUniqueString());
								queryFiles.setFileType(DbConstants.DOCUMENT);
								queryFiles.setFileName(docName);
								queryFiles.setFileUrl(DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
										+ DbConstants.DOCUMENTS_FOLDER + DbConstants.SUFFIX + DbConstants.USER_FOLDER
										+ userId + DbConstants.SUFFIX + DbConstants.QUERY_FOLDER
										+ existingQuery.getQueryId() + DbConstants.SUFFIX + docName);
								queryFilesDao.store(queryFiles);

								audit = new QueryAudit();
								audit.setFixerId(existingQuery.getFixerId());
								audit.setCustomerId(existingQuery.getUser().getUserId());
								audit.setMessage(DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
										+ DbConstants.DOCUMENTS_FOLDER + DbConstants.SUFFIX + DbConstants.USER_FOLDER
										+ userId + DbConstants.SUFFIX + DbConstants.QUERY_FOLDER
										+ existingQuery.getQueryId() + DbConstants.SUFFIX + docName);
								audit.setMsgFrom(DbConstants.CUSTOMER);
								audit.setQuery(existingQuery);
								audit.setStatus(DbConstants.STATUS_WORKING_DOCUMENT);
								Calendar currentTime = Calendar.getInstance();
								audit.setAuditDate(currentTime);
								queryAuditDao.store(audit);

							}
						}
					}

					

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new FixitException("", "");
		}

		return existingQuery;
	}

	@Override
	@Transactional
	public Set<Query> findQueryInactiveFixers(String currentDate, int day) throws FixitException {
		try {
			return queryDao.findQueryInactiveFixers(currentDate, day);
		} catch (Exception e) {
			throw new FixitException("", "");
		}

	}

	@Override
	@Transactional
	public Set<Query> findQueryCustomerNotAnswer(String currentDate, int day) throws FixitException {
		try {
			return queryDao.findQueryCustomerNotAnswer(currentDate, day);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAccepted(int userId) throws FixitException {
		try {
			return queryDao.findQueryNotAccepted(userId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryWorking(int userId) throws FixitException {
		try {
			return queryDao.findQueryWorking(userId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosed(int userId) throws FixitException {
		try {
			return queryDao.findQueryClosed(userId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	
	
	@Override
	@Transactional
	public List<Query> findQueryClosed(int userId,Calendar startDate , Calendar endDate) throws FixitException {
		try {
			return queryDao.findQueryClosed(userId,startDate,endDate);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	

	@Override
	@Transactional
	public Long findUserStasCount(int userId, String currentStatus) throws FixitException {
		try {
			return queryDao.findUserStasCount(userId, currentStatus);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Long findUserClosedStasCount(int userId) throws FixitException {
		try {
			return queryDao.findUserClosedStasCount(userId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Long findUserPendingResolutionStasCount(int userId) throws FixitException {
		try {
			return queryDao.findUserPendingResolutionStasCount(userId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedFixer(int fixerId) throws FixitException {
		try {
			return queryDao.findQueryNotAcceptedFixer(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryWorkingFixer(int fixerId) throws FixitException {
		try {
			return queryDao.findQueryWorkingFixer(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosedFixer(int fixerId) throws FixitException {
		try {
			return queryDao.findQueryClosedFixer(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	
	
	@Override
	@Transactional
	public Set<Query> findQueryClosedFixer(int fixerId ,Calendar startDate , Calendar endDate) throws FixitException {
		try {
			return queryDao.findQueryClosedFixer(fixerId,startDate,endDate);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	

	@Override
	@Transactional
	public Long findFixerStasCount(int fixerId, String currentStatus) throws FixitException {
		try {
			return queryDao.findFixerStasCount(fixerId, currentStatus);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Long findFixerClosedStasCount(int fixerId) throws FixitException {
		try {
			return queryDao.findFixerClosedStasCount(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Long findFixerPendingResolutionStasCount(int fixerId) throws FixitException {
		try {
			return queryDao.findFixerPendingResolutionStasCount(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Query findQueryByQueryId(Integer queryId) throws FixitException {
		try {
			return queryDao.findQueryByQueryId(queryId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Query getQueryByHashCode(String hashcode) throws FixitException {

		try {
			return queryDao.getQueryByQueryHashCode(hashcode);
		} catch (Exception e) {
			throw new FixitException("", "");
		}

	}

	@Override
	@Transactional
	public BigInteger findUnClaimedStatsByFixer(int fixerId) throws FixitException {
		try {
			return queryDao.findUnClaimedStatsByFixer(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}

	}

	@Override
	@Transactional
	public int deleteQueryByQueryId(Integer userId, Integer queryId) throws FixitException {
		try {
			Query existingQuery = queryDao.findQueryByQueryId(queryId);
			queryAuditDao.deleteQueryAuditByQueryId(queryId);
			queryCategoryDao.deleteQueryCategoryByQueryId(queryId);
			queryFilesDao.deleteAllDocumentsByQueryId(queryId);
			queryFilesDao.deleteAllUrlLinksByQueryId(queryId);
			appliedFixersService.deleteAppliedFixerByQueryId(queryId);
			// entry in Query Audit
			
			try {
				FileUpload.deleteQueryFolder(userId, queryId);
			} catch (Exception e) {
				
			}

			return queryDao.deleteQueryByQueryId(queryId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	
	@Override
	@Transactional
	public Query updateQueryCurrentStatus(Query updatedquery)throws FixitException
	{
		try {
			updatedquery = queryDao.store(updatedquery);
			
		} catch (Exception e) {
			throw new FixitException("query.notUpdated", "query.notUpdated");
		}
		return updatedquery;
	}

	@Override
	@Transactional
	public Integer deleteAllQueryRelatedStuff(Integer queryId) throws FixitException {
		try {
			Set<Query> queryList = findQueryByCustomerId(queryId);
			Iterator<Query> queryListIterator = queryList.iterator();
			while (queryListIterator.hasNext()) {
				Integer myQueryId = queryListIterator.next().getQueryId();
				queryDao.deleteQueryByQueryId(myQueryId);
				queryFilesDao.deleteAllDataByQueryId(myQueryId);
				queryCategoryDao.deleteQueryCategoryByQueryId(myQueryId);
				queryAuditDao.deleteQueryAuditByQueryId(myQueryId);
				fixerRatingDao.deleteRatingBasedOnQueryId(myQueryId);
				fixerAccountingDao.deleteDataBasedOnQueryId(myQueryId);
			}
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	@Transactional
	public Query fixedQueryByQueryId(Integer queryId) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);
			existingQuery.setCurrentStatus(DbConstants.STATUS_CLOSED);
			existingQuery.setClosureDate(currentTime);
			existingQuery.setLastUpdateByUser(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(existingQuery.getFixerId());
			queryAudit.setMessage(DbConstants.MSG_CLOSED);
			queryAudit.setMsgFrom(DbConstants.CUSTOMER);
			queryAudit.setStatus(DbConstants.STATUS_CLOSED);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;
	}

	@Override
	@Transactional
	public Query notFixedQuerybyQueryId(Integer queryId, String message) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);
			existingQuery.setCurrentStatus(DbConstants.STATUS_UNRESOLVED_NOTFIXED);
			existingQuery.setLastUpdateByUser(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(existingQuery.getFixerId());
			queryAudit.setMessage(message);
			queryAudit.setMsgFrom(DbConstants.CUSTOMER);
			queryAudit.setStatus(DbConstants.STATUS_UNRESOLVED_NOTFIXED);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;
	}

	@Override
	@Transactional
	public Set<Query> notFixedQuerybyFixedId(Integer fixerId) throws FixitException {
		try {
			return queryDao.notFixedQuerybyFixedId(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryByCustomerId(int customerId) throws FixitException {
		try {
			return queryDao.findQueryByCustomerId(customerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Query rejectFixer(Integer queryId) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);

			fixerId = existingQuery.getFixerId();
			existingQuery.setFixerId(DbConstants.ALL_FIXER);
			existingQuery.setCurrentStatus(DbConstants.STATUS_OPEN);
			existingQuery.setLastUpdateByFixer(null);
			existingQuery.setDateAccepted(null);
			existingQuery.setLastUpdateByUser(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(fixerId);
			queryAudit.setMessage(DbConstants.MSG_FIXER_REJECTED);
			queryAudit.setMsgFrom(DbConstants.CUSTOMER);
			queryAudit.setStatus(DbConstants.STATUS_FIXER_REJECTED);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

			userDeclineService.saveUserDecline(fixerId, queryId);

			queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(DbConstants.ALL_FIXER);
			queryAudit.setMessage(DbConstants.MSG_OPEN);
			queryAudit.setMsgFrom(DbConstants.CUSTOMER);
			queryAudit.setStatus(DbConstants.STATUS_OPEN);
			auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;

	}

	@Override
	@Transactional
	public Query FixerAcceptedForNotAnswer(Integer queryId) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);

			fixerId = existingQuery.getFixerId();
			existingQuery.setFixerId(DbConstants.ALL_FIXER);
			existingQuery.setCurrentStatus(DbConstants.STATUS_OPEN);
			existingQuery.setLastUpdateByFixer(null);
			existingQuery.setDateAccepted(null);
			existingQuery.setLastUpdateByUser(null);
			existingQuery.setDateRaised(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			
			Calendar auditDate = Calendar.getInstance();

			userDeclineService.saveUserDecline(fixerId, queryId);

			queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(DbConstants.ALL_FIXER);
			queryAudit.setMessage(DbConstants.MSG_OPEN);
			queryAudit.setMsgFrom(DbConstants.CUSTOMER);
			queryAudit.setStatus(DbConstants.STATUS_OPEN);
			auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;

	}

	@Override
	@Transactional
	public Query adminInFavourOfMember(Integer queryId) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);

			fixerId = existingQuery.getFixerId();
			existingQuery.setFixerId(DbConstants.ALL_FIXER);
			existingQuery.setCurrentStatus(DbConstants.STATUS_OPEN);
			existingQuery.setLastUpdateByFixer(null);
			existingQuery.setDateAccepted(null);
			existingQuery.setLastUpdateByUser(null);
			existingQuery.setDateRaised(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(fixerId);
			queryAudit.setMessage(DbConstants.MSG_CLOSED);
			queryAudit.setMsgFrom(DbConstants.ADMIN);
			queryAudit.setStatus(DbConstants.STATUS_CLOSED);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

			userDeclineService.saveUserDecline(fixerId, queryId);

			queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(DbConstants.ALL_FIXER);
			queryAudit.setMessage(DbConstants.MSG_OPEN);
			queryAudit.setMsgFrom(DbConstants.ADMIN);
			queryAudit.setStatus(DbConstants.STATUS_OPEN);
			auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;

	}

	@Override
	@Transactional
	public Query conflictRaisedByFixer(Query query, String message) throws FixitException {
		Query existingQuery = query;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			fixerId = existingQuery.getFixerId();
			existingQuery.setCurrentStatus(DbConstants.STATUS_REVIEW);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(fixerId);
			queryAudit.setMessage(message);
			queryAudit.setMsgFrom(DbConstants.FIXER);
			queryAudit.setStatus(DbConstants.STATUS_REVIEW);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}
		return existingQuery;
	}

	@Override
	@Transactional
	public Query setQueryStatusToReview(Integer queryId) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);

			fixerId = existingQuery.getFixerId();
			existingQuery.setCurrentStatus(DbConstants.STATUS_REVIEW);
		
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(fixerId);
			queryAudit.setMessage(DbConstants.MSG_REVIEW);
			queryAudit.setMsgFrom(DbConstants.FIXER);
			queryAudit.setStatus(DbConstants.STATUS_REVIEW);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

			

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;
	}

	@Override
	@Transactional
	public Query setQueryStatusToClosed(Integer queryId, String status) throws FixitException {
		Query existingQuery;
		Calendar currentTime = Calendar.getInstance();
		Integer fixerId;
		try {
			existingQuery = queryDao.findQueryByQueryId(queryId);

			fixerId = existingQuery.getFixerId();
			existingQuery.setCurrentStatus(status);
			
			existingQuery.setClosureDate(currentTime);
			existingQuery = queryDao.store(existingQuery);

			QueryAudit queryAudit = new QueryAudit();
			queryAudit.setQuery(existingQuery);
			queryAudit.setCustomerId(existingQuery.getUser().getUserId());
			queryAudit.setFixerId(fixerId);
			queryAudit.setMessage(DbConstants.MSG_CLOSED);
			queryAudit.setMsgFrom(DbConstants.ADMIN);
			queryAudit.setStatus(DbConstants.STATUS_CLOSED);
			Calendar auditDate = Calendar.getInstance();
			queryAudit.setAuditDate(auditDate);
			queryAudit = queryAuditDao.store(queryAudit);

		} catch (Exception e) {
			throw new FixitException("", "");
		}

		return existingQuery;
	}

	@Override
	public Set<Query> notFixedQueries(String bcozOfInactivity, String bcozOfNotFixed, Integer startResult, Integer max)
			throws FixitException {
		Set<Query> notFixedAndUnresolvedQueries = null;
		try {
			notFixedAndUnresolvedQueries = queryDao.notFixedAndInactiveQueries(bcozOfInactivity, bcozOfNotFixed,
					startResult, max);
			return notFixedAndUnresolvedQueries;
		} catch (Exception e) {
			return null;
		}
		

	}

	@Override
	@Transactional
	public Set<Query> findQueryNotSureCat(Integer catId) throws FixitException {
		try {
			return queryDao.findQueryNotSureCat(catId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedByFixer(String currentTime, Integer day, int startIndex, int maxResult)
			throws FixitException {
		try {
			return queryDao.findQueryNotAcceptedByFixer(currentTime, day, startIndex, maxResult);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public BigInteger findQueryNotAcceptedByFixerCount(String currentTime, Integer day) throws FixitException {
		try {
			return queryDao.findQueryNotAcceptedByFixerCount(currentTime, day);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public BigInteger findQueryNotAcceptedFixerCount(int fixerId) throws FixitException {
		try {
			return queryDao.findQueryNotAcceptedFixerCount(fixerId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	@Transactional
	public MemberDashboardBo findQueryDetail(Query query, Calendar currentTime, String timeZone) throws FixitException {
		long days, min, hrs, sec;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = new HashSet<String>();
		QueryFiles queryFile;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		Iterator<QueryFiles> iteratorQueryFiles;
		MemberDashboardBo memberDashboard = new MemberDashboardBo();
		queryFilesBo = new LinkedHashSet<QueryFilesBo>();
		queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
		for (QueryCategory queryCategory : queryCategories) {
			subCategory.add(
					categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId()).getCatgName());
		}
		memberDashboard.setSubCategory(subCategory);
		memberDashboard.setQueryId(query.getQueryId());
		memberDashboard.setQueryContent(query.getQueryContent());
		memberDashboard.setQueryTitle(query.getQueryTitle());
		memberDashboard.setHashcode(query.getHashcode());
		queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
		iteratorQueryFiles = queryFiles.iterator();
		while (iteratorQueryFiles.hasNext()) {
			queryFile = iteratorQueryFiles.next();
			queryFileBo = new QueryFilesBo();
			queryFileBo.setFileName(queryFile.getFileName());
			queryFileBo.setFileUrl(queryFile.getFileUrl());
			queryFileBo.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
			queryFilesBo.add(queryFileBo);
		}
		if (queryFilesBo.size() > 0) {
			memberDashboard.setAttachedDocumentsCount(1);
		} else {
			memberDashboard.setAttachedDocumentsCount(0);
		}
		memberDashboard.setAttachedDocuments(queryFilesBo);
		days = TimeDiffUtility.findDayDiff(query.getDateRaised(), currentTime);
		hrs = TimeDiffUtility.findHrsDiff(query.getDateRaised(), currentTime);
		min = TimeDiffUtility.findMinutesDiff(query.getDateRaised(), currentTime);
		sec = TimeDiffUtility.findSecondsDiff(query.getDateRaised(), currentTime);
		if (days != 0) {
			if (days == 1) {
				memberDashboard.setTimeDiff(days + " day");

			} else {
				memberDashboard.setTimeDiff(days + " days");

			}
		} else {
			if (hrs != 0) {
				if (hrs == 1) {
					memberDashboard.setTimeDiff(hrs + " hr");

				} else {
					memberDashboard.setTimeDiff(hrs + " hrs");

				}
				
			} else {
				if (min != 0) {
					if (min == 1) {

						memberDashboard.setTimeDiff(min + " minute");

					} else {

						memberDashboard.setTimeDiff(min + " minutes");

					}
				} else {
					memberDashboard.setTimeDiff(sec + " seconds");
				}
			}
		}
		
		/*if (days != 0) {
			memberDashboard.setTimeDiff(days + " day");
		} else {
			if (hrs != 0) {
				memberDashboard.setTimeDiff(hrs + " hrs");
			} else {
				if (min != 0) {
					memberDashboard.setTimeDiff(min + " minutes");
				} else {
					memberDashboard.setTimeDiff(sec + " seconds");
				}
			}
		}*/
		return memberDashboard;

	}

	@Override
	public long findAverageResponseTime(Integer fixerId, String status) throws DataAccessException {
		try {
			long totalTime = 0;
			long totalRecords = 0;
			long responseTime = 0;
			Set<Query> queryAuditSet = queryDao.findQueryByFixerIdAndStatus(fixerId, status);
			Iterator<Query> iterator = queryAuditSet.iterator();
			while (iterator.hasNext()) {
				Query query = iterator.next();
				Calendar acceptDate = queryDao.findIssueOpenDate(query.getQueryId());
				Calendar closeDate = queryDao.findIssueCloseDate(query.getQueryId());
				Date d1 = acceptDate.getTime();
				Date d2 = closeDate.getTime();
				long diff = d2.getTime() - d1.getTime();
				long x = diff / (1000 * 60 * 60);
				totalTime = totalTime + x;
				totalRecords = totalRecords + 1;
			}
			if (totalRecords != 0) {
				return (totalTime / totalRecords);
			}

		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long findMemberQueryCount(int userId) throws FixitException {
		long result = queryDao.findMemberQueryCount(userId);
		return result;
	}

	@Override
	public long findSumofQueryCreditByUserId(int userId) throws FixitException {
		long sum = queryDao.findSumofQueryCreditByUserId(userId);
		return sum;
	}

	@Override
	public double findPercentageQueriesFixedWithInDeadline(int userId) {
		double percentageCounts = 0;
		long deadlineCounts = queryDao.findFixerClosedRequestsCountInDeadline(userId);
		long totalFixedCounts = queryDao.findFixerClosedStasCount(userId);
		if(totalFixedCounts != 0)
		 percentageCounts = (deadlineCounts*100)/totalFixedCounts;
	
		return percentageCounts;
	}

	@Override
	public List<PieChartData> findQueryByParentCatMember(Integer userId) throws DataAccessException {
		try{
			List<Object> objects = queryDao.findQueryByParentCatMember(userId);
			List<PieChartData> pieChartDatas = new ArrayList<PieChartData>();
			PieChartData chartData;
			BigInteger bigInteger;
			int count = 0;
			int otherCount = 0;
			for (Object o : objects) {
				Object[] cols = (Object[]) o;
				if(count<10){
				chartData = new PieChartData();
				bigInteger = (BigInteger)cols[0];
				chartData.setPercentage(bigInteger.toString());
				chartData.setModule((String)cols[1]);
				pieChartDatas.add(chartData);
				}else{
					bigInteger = (BigInteger)cols[0];
					otherCount =  otherCount+Integer.valueOf(bigInteger.toString());
				}
				count++;
			}
			 if(otherCount>0){
		     chartData = new PieChartData();
		     chartData.setModule("Others");
		     chartData.setPercentage(String.valueOf(otherCount));
		     pieChartDatas.add(chartData);
			 }
			return pieChartDatas;
		}catch(Exception e){
			return null;
		}
		
	}
	
	@Override
	public List<PieChartData> findQueryByParentCatFixer(Integer userId) throws DataAccessException {
		try{

		List<Object> objects = queryDao.findQueryByCatIdFixer(userId);

			List<PieChartData> pieChartDatas = new ArrayList<PieChartData>();
			PieChartData chartData;
			BigInteger bigInteger;
			int count = 0;
			int otherCount = 0;
			for (Object o : objects) {
				Object[] cols = (Object[]) o;
				if(count<10){
				chartData = new PieChartData();
				bigInteger = (BigInteger)cols[0];
				chartData.setPercentage(bigInteger.toString());
				chartData.setModule((String)cols[1]);
				pieChartDatas.add(chartData);
				}else{
					bigInteger = (BigInteger)cols[0];
					otherCount =  otherCount+Integer.valueOf(bigInteger.toString());
				}
				count++;
			}
			 if(otherCount>0){
		     chartData = new PieChartData();
		     chartData.setModule("Others");
		     chartData.setPercentage(String.valueOf(otherCount));
		     pieChartDatas.add(chartData);
			 }
			return pieChartDatas;
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public Integer deleteQueryFilesByFileUniqueCode(Integer queryId, String fileUniqueCode) throws FixitException{
		int i = queryFilesDao.deleteQueryFilesByFileUniqueCode(queryId, fileUniqueCode);
		return i;
	}

	@Override
	public long findSumofQueryCreditInProgAndFixedByUserId(int userId) throws DataAccessException {
		long sum = queryDao.findSumofQueryCreditInProgAndFixedByUserId(userId);
		return sum;
	}



}
