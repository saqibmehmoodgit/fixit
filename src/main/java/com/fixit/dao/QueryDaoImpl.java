package com.fixit.dao;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.vo.Query;

@Repository("QueryDao")
@Transactional
public class QueryDaoImpl extends AbstractJpaDao<Query> implements QueryDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { Query.class }));

	public QueryDaoImpl() {
		super();
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	@Override
	public boolean canBeMerged(Query o) {
		return true;
	}

	@Override
	public Query findQueryByQueryId(Integer queryId) throws DataAccessException {
		return findQueryByQueryId(queryId, -1, -1);
	}

	@Transactional
	public Query findQueryByQueryId(Integer queryId, int startResult, int maxRows) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryByQueryId", startResult, maxRows, queryId);
			return (Query) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Query getQueryByQueryHashCode(String hashcode) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("getQueryByHashCode", -1, -1, hashcode);
			return (Query) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}
	
	/*@Override
	@Transactional
	public Integer updateQueryCurrentStatusByQueryId(Integer queryId,String currentStatus ) throws DataAccessException {
		try {
			Query query = findQueryByQueryId(queryId);
			
			entityManager.getTransaction().begin();
			query.setCurrentStatus(currentStatus);
			entityManager.getTransaction().commit();
		} catch (NoResultException nre) {
			return 0;
		}
		return 1;
	}*/

	@Override
	public Set<Query> getQuestionsByFixerId(Integer userId, String type, List<String> list, int start, int max)
			throws DataAccessException {
		return getQuestionsByFixerId(userId, type, start, max, list);
	}

	@Transactional
	public Set<Query> getQuestionsByFixerId(Integer userId, String type, int startResult, int maxRows,
			List<String> list) throws DataAccessException {
		javax.persistence.Query query;

		try {

			query = createNamedQuery("getQuestionsByFixerId", startResult, maxRows, userId, list);

			
			return new HashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<Query> getQuestionByCustomerId(Integer userId, String type, List<String> list, int min, int max)
			throws DataAccessException {
		return getQuestionsByCustomerId(userId, type, list, min, max);
	}

	@Transactional
	public Set<Query> getQuestionsByCustomerId(Integer userId, String type, List<String> list, int startResult,
			int maxRows) throws DataAccessException {
		javax.persistence.Query query;

		try {

			query = createNamedQuery("getQuestionsByCustomerId", startResult, maxRows, userId, list);
			
			return new HashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<Query> getFixerStats(Integer fixerId) throws DataAccessException {
		
		return getFixerStatsByFixerId(fixerId, -1, -1);
	}

	@Transactional
	public Set<Query> getFixerStatsByFixerId(Integer fixerId, int startResult, int maxRows) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findFixerStats", startResult, maxRows, fixerId);
			
			return new HashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;

		}
	}

	@Override
	public Set<Query> getCustomerStats(Integer customerId) throws DataAccessException {
		

		return getCustomerStatsByCustomerId(customerId, -1, -1);
	}

	@Transactional
	public Set<Query> getCustomerStatsByCustomerId(Integer customerId, int startResult, int maxRows)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findUserStats", startResult, maxRows, customerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;

		}

	}

	@Override
	@Transactional
	public Set<Query> fixedIssueListByFixerId(int fixerId, int startResult, int maxRows) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("fixedIssueListByFixerId", startResult, maxRows, fixerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;

		}
	}
	

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedByFixer(String currentDate, int day, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM query WHERE fixer_id ='0' AND  "
					+ "  DATE_FORMAT(date_raised,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			query.setFirstResult(startIndex);
			query.setMaxResults(maxResult);
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedByFixerNative(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM query WHERE fixer_id ='0' AND  "
					+ "  DATE_FORMAT(date_raised,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<Query> openQueryIfNotNotifyAfterNotFixed(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM query WHERE current_status ='UN' AND  "
					+ "  DATE_FORMAT(last_update_by_user,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('"
					+ currentDate + "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public BigInteger findQueryNotAcceptedByFixerCount(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "SELECT count(*) FROM query WHERE fixer_id ='0' AND  "
					+ "  DATE_FORMAT(date_raised,'%Y-%m-%d') <= " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return (BigInteger) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Integer deleteAllQueryRelatedStuff(Integer queryId) throws DataAccessException {
		try {
			String nativeQuery = "delete from query_expire where query_id=" + queryId + ""
					+ "delete from query_files where query_id=" + queryId + "" + "delete from query_cat where query_id="
					+ queryId + "" + "delete from query_audit where query_id=" + queryId + ""
					+ "delete from fixer_rating where query_id=" + queryId + ""
					+ "delete from fixer_accounting where query_id=" + queryId + ""
					+ "delete from query where query_id=" + queryId + ";";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return new Integer(query.executeUpdate());
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedByIndividualFixer(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM query WHERE fixer_id >'0' AND current_status='O'   AND  date_accepted is null AND "
					+ "  DATE_FORMAT(date_raised,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<Query> findQueryInactiveFixers(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "select * from (SELECT * FROM query WHERE current_status = 'W' AND  "
					+ "	last_update_by_fixer is not null AND last_update_by_user is not null AND last_update_by_user > last_update_by_fixer AND  "
					+ "  DATE_FORMAT(last_update_by_fixer,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('"
					+ currentDate + "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s') UNION    "
					+ " SELECT * FROM query WHERE current_status = 'W' AND  " + "	last_update_by_fixer is null AND   "
					+ "  DATE_FORMAT(date_accepted,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s'))as tab order by date_raised";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryCustomerNotAnswer(String currentDate, int day) throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM query WHERE current_status = 'W' AND last_update_by_fixer is not null AND "
					+ "  last_update_by_fixer > last_update_by_user  AND "
					+ "  DATE_FORMAT(last_update_by_user,'%Y-%m-%d %H:%i:%s') <= " + " DATE_FORMAT(DATE_SUB('"
					+ currentDate + "',INTERVAL " + day + " DAY),'%Y-%m-%d %H:%i:%s')";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryFixerNotAccepted(String currentDate, int day, String startTime, String endTime)
			throws DataAccessException {
		try {
			String nativeQuery = "SELECT * FROM QUERY WHERE  fixer_id='0' AND "
					+ " DATE_FORMAT(date_raised,'%Y-%m-%d') = " + " DATE_FORMAT(DATE_SUB('" + currentDate
					+ "',INTERVAL " + day + " DAY),'%Y-%m-%d')  " + " AND  DATE_FORMAT('" + currentDate
					+ "','%T') between '" + startTime + "' AND '" + endTime + "' ";

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Set<Query> findQueryNotAccepted(int userId) throws DataAccessException {
		return findQueryNotAccepted(userId, -1, -1);
	}

	@Override
	public Set<Query> findQueryNotAccepted(int userId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryNotAccepted", startResult, max, userId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<Query> findQueryWorking(int userId) throws DataAccessException {
		return findQueryWorking(userId, -1, -1);
	}

	@Override
	public Set<Query> findQueryWorking(int userId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryWorking", startResult, max, userId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<Query> findQueryClosed(int userId) throws DataAccessException {
		return findQueryClosed(userId, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosed(int userId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryClosed", startResult, max, userId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (Exception nre) {
			return null;
		}
		
	}

	@Override
	public List<Query> findQueryClosed(int userId, Calendar startDate, Calendar endDate) throws DataAccessException {
		return findQueryClosed(userId, startDate, endDate, -1, -1);
	}

	@Override
	@Transactional
	public List<Query> findQueryClosed(int userId, Calendar startDate, Calendar endDate, int startResult, int max)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryClosedByMonth", startResult, max, userId);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			return new ArrayList<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryPendingResolution(int userId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryPendingResolution", startResult, max, userId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findUserStasCount(int userId, String currentStatus) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findUserStasCount", -1, -1, userId, currentStatus);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findUserClosedStasCount(int userId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findUserClosedStasCount", -1, -1, userId);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findUserPendingResolutionStasCount(int userId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findUserPendingResolutionStasCount", -1, -1, userId);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedFixer(int fixerId) throws DataAccessException {
		return findQueryNotAccepted(fixerId, -1, -1);
	}

	@Override
	public Set<Query> findQueryNotAcceptedFixer(int fixerId, int startResult, int max) throws DataAccessException {

		try {
			String sqlQuery = "select * from ( "
					+ " select tab1.*, group_concat(tab2.cat_id order by tab2.cat_id asc, '') as "
					+ "all_categories from query tab1, query_cat tab2 where ((tab1.fixer_id = 0 OR  tab1.fixer_id = "
					+ fixerId + ") and current_status='O') and tab1.query_id = tab2.query_id group by tab1.query_id) "
					+ "tab100 where tab100.all_categories = (select  group_concat(tab5.cat_id order by tab5.cat_id asc) category_id from user tab4, "
					+ "user_cat tab5 where tab4.user_id = tab5.user_id and tab4.user_type = 'F' and tab4.user_id = '"
					+ fixerId + "' group by tab5.user_id) order by date_raised DESC ";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "queryMapping");
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedFixerNative(int fixerId, int startResult, int max)
			throws DataAccessException {

		try {
			
			
			String sqlQuery = "Select *  from((select * from query where (current_status ='O' or current_status ='H') and  fixer_id='0' and query_id IN (select query_id from query_cat where cat_id IN (select cat_id from user_cat where user_id='"
					+ fixerId + "')) and query_id "
					+ "NOT IN (select query_id from query_applied_fixers where fixer_id = '" + fixerId
					+ "') and query_id NOT IN (select query_fixers_queryid from query_fixers) and query_id NOT IN (Select query_id from user_decline where user_id = '"
					+ fixerId
					+ "')) union (select * from query where query_id IN (select query_fixers_queryid from query_fixers where query_fixers_userid = '"
					+ fixerId + "') and query_id "
					+ "NOT IN (select query_id from query_applied_fixers where fixer_id = '" + fixerId
					+ "') and query_id NOT IN (Select query_id from user_decline where user_id = '" + fixerId
					+ "'))) as tab order by date_raised DESC";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "queryMapping");
			query.setFirstResult(startResult);
			query.setMaxResults(max);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedFixerNamed(List<Integer> categoryList) throws DataAccessException {
		return findQueryNotAcceptedFixerNamed(categoryList, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotAcceptedFixerNamed(List<Integer> categoryList, int startResult, int max)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findNotAcceptedQueryByFixerCategory", startResult, max);
			query.setParameter("categoryList", categoryList);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findQueryNotAcceptedFixerNamedCount(List<Integer> categoryList) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findNotAcceptedQueryByFixerCategoryCount", -1, -1);
			query.setParameter("categoryList", categoryList);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	public BigInteger findQueryNotAcceptedFixerCount(int fixerId) throws DataAccessException {

		try {
			// String sqlQuery = "select count(*) from ( "
			// + " select tab1.*, group_concat(tab2.cat_id order by tab2.cat_id
			// asc, '') as "
			// + "all_categories from query tab1, query_cat tab2 where
			// ((tab1.fixer_id = 0 OR tab1.fixer_id = "+fixerId+") and
			// current_status='O') and tab1.query_id = tab2.query_id group by
			// tab1.query_id) "
			// + "tab100 where tab100.all_categories = (select
			// group_concat(tab5.cat_id order by tab5.cat_id asc) category_id
			// from user tab4, "
			// + "user_cat tab5 where tab4.user_id = tab5.user_id and
			// tab4.user_type = 'F' and tab4.user_id = '"
			// + fixerId
			// + "' group by tab5.user_id) order by date_raised DESC ";

			/*
			 * String sqlQuery =
			 * "select count(*) from(select * from query where current_status ='O' and  fixer_id='0'"
			 * +
			 * " and query_id in ((select query_id from query_cat where cat_id IN"
			 * +" (select cat_id from user_cat where user_id='"+fixerId+"'))) "
			 * 
			 * + "and query_id" +
			 * " Not in(select query_fixers_queryid from query_fixers) union" +
			 * " (select * from query where current_status ='O' and  fixer_id='0'"
			 * +
			 * " and query_id in(select query_fixers_queryid from query_fixers where query_fixers_userid='"
			 * +fixerId+"')" + "and query_id" +
			 * " NOT IN(select query_id from query_applied_fixers where fixer_id = '"
			 * +fixerId+"')" + "))" +" as tab order by date_raised DESC;";
			 */
			String sqlQuery = "Select count(*)  from((select * from query where current_status ='O' and  fixer_id='0' and query_id IN (select query_id from query_cat where cat_id IN (select cat_id from user_cat where user_id='"
					+ fixerId + "')) and query_id "
					+ "NOT IN (select query_id from query_applied_fixers where fixer_id = '" + fixerId
					+ "') and query_id NOT IN (select query_fixers_queryid from query_fixers) "
					+ "and query_id NOT IN (Select query_id from user_decline where user_id = '" + fixerId
					+ "')) union (select * from query where query_id IN (select query_fixers_queryid from query_fixers where query_fixers_userid = '"
					+ fixerId + "') and query_id "
					+ "NOT IN (select query_id from query_applied_fixers where fixer_id = '" + fixerId
					+ "') and query_id NOT IN (Select query_id from user_decline where user_id = '" + fixerId
					+ "'))) as tab order by date_raised DESC";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery);
			return (BigInteger) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Set<Query> findQueryWorkingFixer(int fixerId) throws DataAccessException {
		return findQueryWorkingFixer(fixerId, -1, -1);
	}

	@Override
	public Set<Query> findQueryWorkingFixer(int fixerId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryWorkingFixer", startResult, max, fixerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<Query> findQueryClosedFixer(int fixerId) throws DataAccessException {
		return findQueryClosed(fixerId, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosedFixer(int fixerId, int startResult, int max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryClosedFixer", startResult, max, fixerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosedFixer(int fixerId, Calendar startDate, Calendar endDate)
			throws DataAccessException {
		return findQueryClosedFixer(fixerId, startDate, endDate, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> findQueryClosedFixer(int fixerId, Calendar startDate, Calendar endDate, int startResult, int max)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryClosedFixerMonth", startResult, max, fixerId);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryPendingResolutionFixer(int fixerId, int startResult, int max)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryPendingResolutionFixer", startResult, max,
					fixerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findFixerStasCount(int fixerId, String currentStatus) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findFixerStasCount", -1, -1, fixerId, currentStatus);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findFixerClosedStasCount(int fixerId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findFixerClosedStasCount", -1, -1, fixerId);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long findFixerPendingResolutionStasCount(int fixerId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findFixerPendingResolutionStasCount", -1, -1, fixerId);
			return (long) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public BigInteger findUnClaimedStatsByFixer(int fixerId) {
		try {
			String sqlQuery = "select count(*) from ( "
					+ " select tab1.*, group_concat(tab2.cat_id order by tab2.cat_id asc, '') as all_categories from query tab1, query_cat tab2 "
					+ " where tab1.fixer_id = 0 and tab1.query_id = tab2.query_id group by tab1.query_id) tab100 "
					+ " where tab100.all_categories = (select  group_concat(tab5.cat_id order by tab5.cat_id asc) category_id from user tab4,  "
					+ "  user_cat tab5 where tab4.user_id = tab5.user_id and tab4.user_type = 'F' and tab4.user_id = 135 group by tab5.user_id )";
			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery);
			return (BigInteger) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public int deleteQueryByQueryId(Integer queryId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("deleteQueryByQueryId", -1, -1, queryId);
			return query.executeUpdate();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public Set<Query> notFixedQuerybyFixedId(Integer fixerId) throws DataAccessException {
		return notFixedQuerybyFixedId(fixerId, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> notFixedQuerybyFixedId(Integer fixerId, Integer startResult, Integer max)
			throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("notFixedQuerybyFixedId", startResult, max, fixerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryByCustomerId(int customerId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryByCustomerId", -1, -1, customerId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<Query> notFixedAndInactiveQueries(String bcozOfInactivity, String bcozOfNotFixed, Integer startResult,
			Integer max) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findUnresolvedAndInactiveQueries", startResult, max,
					bcozOfInactivity, bcozOfNotFixed);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;

		}
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotSureCat(Integer catId) {
		return findQueryNotSureCat(catId, -1, -1);
	}

	@Override
	@Transactional
	public Set<Query> findQueryNotSureCat(Integer catId, Integer startResult, Integer max) {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryNotSureCat", startResult, max, catId);
			return new LinkedHashSet<Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;

		}
	}

	@Override
	@Transactional
	public long findQueryNotSureCatCount(Integer catId, Integer startResult, Integer max) {
		try {
			javax.persistence.Query query = createNamedQuery("findQueryNotSureCatCount", startResult, max, catId);
			return (long) query.getSingleResult();
		} catch (NoResultException nre) {
			return 0;

		}
	}

	@Override
	public Calendar findIssueOpenDate(Integer queryId) throws DataAccessException {
		javax.persistence.Query query = createNamedQuery("findIssueOpenDate", -1, -1, queryId);
		return (Calendar) query.getSingleResult();
	}

	@Override
	public Calendar findIssueCloseDate(Integer queryId) throws DataAccessException {
		javax.persistence.Query query = createNamedQuery("findIssueCloseDate", -1, -1, queryId);
		return (Calendar) query.getSingleResult();
	}

	@Override
	public Set<Query> findQueryByFixerIdAndStatus(Integer fixerId, String status) {
		javax.persistence.Query query = createNamedQuery("findQueryByFixerIdAndStatus", -1, -1, fixerId, status);
		return new LinkedHashSet<Query>(query.getResultList());
	}

	@Override
	public long findMemberQueryCount(int userId) throws DataAccessException {
		javax.persistence.Query query = createNamedQuery("findMemberQueryCount", -1, -1, userId);
		return (long) query.getSingleResult();

	}

	@Override
	public long findSumofQueryCreditByUserId(int userId) throws DataAccessException {
		try {
			javax.persistence.Query query = createNamedQuery("findSumofQueryCreditByUserId", -1, -1, userId);
			if(query.getSingleResult()==null){
			 return 0;	
			}else{
			  return (long) query.getSingleResult();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long findFixerClosedRequestsCountInDeadline(Integer userId) throws DataAccessException {
		
		try {
			javax.persistence.Query query = createNamedQuery("findFixerClosedRequestsCountInDeadline", -1, -1, userId);
			if(query.getSingleResult()==null){
				return 0;
			}else{
				return (long) query.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			return 0;

		}
	}

	@Override
	public Set<Query> findQueriesNearByDeadline(String currentDate) throws DataAccessException {
		
		try {
			String nativeQuery = "SELECT * from query WHERE current_status = 'W' and query_deadline_date >= DATE_FORMAT("
					+ currentDate + ",'%Y-%m-%d %H:%i:%s')";
			/*
			 * String nativeQuery =
			 * "SELECT * FROM query WHERE fixer_id ='0' AND  " +
			 * "  DATE_FORMAT(date_raised,'%Y-%m-%d %H:%i:%s') <= " +
			 * " DATE_FORMAT(DATE_SUB('" + currentDate + "',INTERVAL " + day +
			 * " DAY),'%Y-%m-%d %H:%i:%s')";
			 */

			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery, "queryMapping");
			return new HashSet<Query>(query.getResultList());
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public List<Object> findQueryByParentCatMember(Integer userId) throws DataAccessException {
		try {
			String nativeQuery = "select   count(DISTINCT result.query_id) as count ,(select cat_name from cat_type where cat_id= (select parent_id from cat_type where cat_id=result.cat_id)) as parentName  from  "
					+ "   (select query.query_id , query_cat.cat_id from query inner join query_cat on  "
					+ "  query.query_id=query_cat.query_id and query.customer_id='" + userId + "') as  "
					+ " result group by parentName  order by count DESC;";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<Object> findQueryByParentCatFixer(Integer fixerId) throws DataAccessException {
		try {
			String nativeQuery = "select   count(DISTINCT result.query_id) as count , (select cat_name from cat_type where parent_id >0 and cat_id= (select cat_id from cat_type where cat_id=result.cat_id)) as parentName  from "
					+ " (select query.query_id , query_cat.cat_id from query inner join query_cat on query.query_id=query_cat.query_id and query.current_status='C'  and query.fixer_id='"
					+ fixerId + "') as  " + " result group by parentName order by count DESC";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public List<Object> findQueryByCatIdFixer(Integer fixerId) throws DataAccessException {
		try {
			String nativeQuery = " select   count(DISTINCT result.query_id) as count ,(select cat_name from cat_type where parent_id>0 and  cat_id in  (result.cat_id)) as parentName  from  "
					+ " (select query.query_id , query_cat.cat_id from query inner join query_cat on   "
					+ " query.query_id=query_cat.query_id and query.current_status='C' "
					+ " and query.fixer_id='" + fixerId + "') as result    group by parentName order by count DESC";
			javax.persistence.Query query = entityManager.createNativeQuery(nativeQuery);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public long findSumofQueryCreditInProgAndFixedByUserId(int userId) throws DataAccessException {

		try {
			javax.persistence.Query query = createNamedQuery("findSumofQueryCreditInProgAndFixedByUserId", -1, -1, userId);
			if(query.getSingleResult()==null){
			 return 0;	
			}else{
			  return (long) query.getSingleResult();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	
	}
}
