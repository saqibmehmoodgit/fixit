package com.fixit.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fixit.domain.bo.FixerAccountingGroup;
import com.fixit.domain.bo.FixerAccountingUserGroup;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

@Repository("UserDao")
@Transactional
public class UserDaoImp extends AbstractJpaDao<User> implements UserDao {

	@PersistenceContext(unitName = "fixit")
	private EntityManager entityManager;

	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { User.class }));

	public UserDaoImp() {

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
	public boolean canBeMerged(User o) {
		return true;
	}

	/**
	 * findUserByUserId
	 */
	@Override
	@Transactional
	public User findUserByUserId(Integer userId) throws DataAccessException {
		return findUserByUserId(userId, -1, -1);

	}

	/***
	 * findUserByUserId
	 */

	@Transactional
	public User findUserByUserId(Integer userId, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findUserById", startResult, maxRows, userId);
			return (User) query.getSingleResult();
		} catch (Exception nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Integer deleteUserByUserId(Integer userid) throws DataAccessException {
		try {
			Query query = createNamedQuery("deleteUserByUserId", -1, -1, userid);
			return (Integer) query.executeUpdate();
		} catch (NoResultException nre) {
			return -1;
		}
	}

	@Override
	@Transactional
	public Set<User> findAdminByUserType(String userType, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAdminByUserType", startResult, maxRows, userType);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> findAllUserBasedOnIdList(Set<Integer> userIdList, int startResult, int maxRows)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("findAllUserBasedOnIdList", startResult, maxRows);
			query.setParameter("userIdList", userIdList);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<FixerAccountingUserGroup> searchListOfFixerUsingSearchText(String searchFieldText, int startIndex,
			int maxResult) throws DataAccessException {
		try {
			maxResult = startIndex + maxResult;
			String sqlQuery = "select fixer_accounting.fixer_id,count(fixer_accounting.fixer_id),sum(fixer_accounting.amount_paid),user.first_name,user.last_name ,user.email from fixer_accounting LEFT JOIN user on fixer_accounting.fixer_id=user.user_id where (fixer_accounting.status=0 and(user.user_name like '%"
					+ searchFieldText + "%' or user.email like '%" + searchFieldText + "%' or user.first_name like '%"
					+ searchFieldText + "%' or user.last_name like '%" + searchFieldText
					+ "%')) group by fixer_id limit " + startIndex + "," + maxResult;
			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "fixerAccountingUserGroup");
			Set<FixerAccountingUserGroup> groups = new LinkedHashSet<FixerAccountingUserGroup>();
			for (Object o : query.getResultList()) {
				Object[] cols = (Object[]) o;
				FixerAccountingUserGroup fixerAccountingUserGroup = new FixerAccountingUserGroup();
				fixerAccountingUserGroup.setFixer_id((Integer) cols[0]);
				fixerAccountingUserGroup.setCount((BigInteger) cols[1]);
				fixerAccountingUserGroup.setAmount_paid((Double) cols[2]);
				fixerAccountingUserGroup.setFirst_name((String) cols[3]);
				fixerAccountingUserGroup.setLast_name((String) cols[4]);
				fixerAccountingUserGroup.setEmail((String) cols[5]);
				groups.add(fixerAccountingUserGroup);
			}
			return groups;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> findAllFixer() throws DataAccessException {
		return findAllFixer(-1, -1);
	}

	@Override
	@Transactional
	public Set<User> searchListOfUserUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfUserUsingSearchText", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<User> searchListOfChatUserUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfChatUserUsingSearchText", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<User> searchListOfUserFixersUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfUserFixersUsingSearchText", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Set<User> findAllFixer(int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findAllFixer", startResult, maxRows);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> getListOfFavouriteFixerByUserId(Integer userId, int startIndex, int maxResult) {
		try {
			Query query = createNamedQuery("getListOfFavouriteFixerByFixerId", startIndex, maxResult, userId);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<User> findFixerByCategory(List<Integer> categories) throws DataAccessException {
		return findFixerByCategory(categories, -1, -1);
	}

	@Override
	@Transactional
	public Set<User> findFixerByCategory(List<Integer> categories, int startResult, int maxRows)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("findFixerByCategory", startResult, maxRows);
			query.setParameter("categoryList", categories);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<User> findUserByEmailOrUserName(String email, String userName) throws DataAccessException {
		try {
			Query query = createNamedQuery("findUserByEmailOrUserName", -1, -1, email, userName);
			return new ArrayList<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> getAllMembers(int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("getAllMembers", startResult, maxRows);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> getAllFixers(int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("getAllFixers", startResult, maxRows);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<User> findUserByEmailForUpdateSettings(String email, String oldEmail) throws DataAccessException {
		try {
			Query query = createNamedQuery("findUserByEmailForSettingsUpdate", -1, -1, email, oldEmail);
			return new ArrayList<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<User> findUserByUserNameForUpdateSettings(String userName, String oldUserName)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("findUserByUserNameForSettingsUpdate", -1, -1, userName, oldUserName);
			return new ArrayList<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> getAllMemberAndActivatedFixers(int startIndex, int maxResult) throws DataAccessException {
		try {
			Query query = createNamedQuery("getAllMemberAndActivatedFixers", startIndex, maxResult);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> getListOfFixerWithActivtedStatus(String currentStatus, String userType, int startIndex,
			int maxResult) throws DataAccessException {
		try {
			Query query = createNamedQuery("getListOfFixerWithActivtedStatus", startIndex, maxResult, currentStatus,
					userType);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> searchListOfUsersBasedOnSearchField(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("getFixersBasedOnSearch", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");

			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> searchListOfUsersBasedOnSearchFieldAndCategory(List<Integer> categories, String searchFieldText,
			int startIndex, int maxResult) throws DataAccessException {
		try {
			Query query = createNamedQuery("getFixersBasedOnSearchAndCategory", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			query.setParameter("categoryList", categories);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> searchListOfUsersBasedOnSearchFieldCategoryAndCountry(List<Integer> categories,
			String searchFieldText, String selectedRate, String country, int startIndex, int maxResult)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("getFixersBasedOnSearchCategoryAndCountry", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			query.setParameter("categoryList", categories);
			query.setParameter("country", "%" + country + "%");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}
	/*
	 * @Override
	 * 
	 * @Transactional public List<User>
	 * findUserByEmailOrUserNameForUpdateSettings(String email,String
	 * userName,String oldEmail, String oldUserName) throws DataAccessException{
	 * try { Query query =
	 * createNamedQuery("findUserByEmailOrUserNameForSettingsUpdate",-1,
	 * -1,email,userName); return new ArrayList<User>(query.getResultList()); }
	 * catch (NoResultException nre) { return null; } }
	 */

	@Override
	@Transactional
	public List<User> findUserByEmail(String email) throws DataAccessException {
		try {
			Query query = createNamedQuery("findUserByEmail", -1, -1, email);
			return new ArrayList<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> findFixerWithStatus(int startResult, int maxRows, String fixerStatus) throws DataAccessException {
		try {
			Query query = createNamedQuery("findFixerByStatus", startResult, maxRows, fixerStatus);
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	@Transactional
	public long findFixerCountWithStatus(int startResult, int maxRows, String fixerStatus) throws DataAccessException {
		try {
			Query query = createNamedQuery("findFixerCountByStatus", startResult, maxRows, fixerStatus);
			return (long) (query.getSingleResult());
		} catch (NoResultException nre) {
			return 0;
		}

	}

	@Override
	@Transactional
	public int updateFixerByAdmin(int userId, String fixerStatus) throws DataAccessException {
		
		try {
			Query query = createNamedQuery("updateUserStatus", -1, -1, userId, fixerStatus);
			return query.executeUpdate();
		} catch (NoResultException nre) {
			return -1;
		}
	}

	@Override
	@Transactional
	public Set<com.fixit.domain.vo.Query> findAllNotSureQueries(int startResult, int maxRows, int fixerId)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("findNotSureQueries", startResult, maxRows, fixerId);
			return new LinkedHashSet<com.fixit.domain.vo.Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}

	}

	@Override
	@Transactional
	public Set<com.fixit.domain.vo.Query> findAllReviewQueries(int startResult, int maxRows, String status)
			throws DataAccessException {
		try {
			Query query = createNamedQuery("findReviewQueries", startResult, maxRows, status);
			return new LinkedHashSet<com.fixit.domain.vo.Query>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public long findAllReviewQueriesCount(int startResult, int maxRows, String status) throws DataAccessException {
		try {
			Query query = createNamedQuery("findReviewQueriesCount", startResult, maxRows, status);
			return (long) (query.getSingleResult());
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	@Transactional
	public Set<User> findFixerWhoMatchQueryCat(Integer queryId) {
		try {
			
			
			String sqlQuery = "select * from user where fixer_status='A' and user_id IN (select user_id from user_cat where cat_id IN (select cat_id from query_cat where query_id='"
					+ queryId + "')) and user_id NOT IN (select user_id from user_decline where query_id='"+queryId+"') and user_id NOT IN (select fixer_id from query_applied_fixers where query_id='"+queryId+"'and status='D')";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "userMapping");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	@Transactional
	public Set<User> findNotIntrestedAndRemovedFixer(Integer queryId){
		try {
			
			
			String sqlQuery = "select * from user where user_id IN (select user_id from user_decline where query_id='"+queryId+"') or user_id  IN (select fixer_id from query_applied_fixers where query_id='"+queryId+"'and status='D')";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "userMapping");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Integer deleteMemberAllData(Integer userId) throws DataAccessException {
		try {

			String sqlQuery = "delete from user_indst where user_id=" + userId + ";"
					+ "delete from user_decline where user_id=" + userId + ";"
					+ "delete from verification where user_id=" + userId + ";"
					+ "delete from testimonial where user_id=" + userId + ";" + "delete from user_credit where user_id="
					+ userId + ";" + "delete from user_cat where user_id=" + userId + ";"
					+ "delete from users_accounting where user_id=" + userId + ";"
					+ "delete from favourite_fixer where member_id=" + userId + ";" + "delete from user where user_id="
					+ userId + ";";

			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery);
			return new Integer(query.executeUpdate());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer searchListOfFixerUsingSearchTextCount(String searchFieldText) throws DataAccessException {
		try {
			String sqlQuery = "select fixer_accounting.fixer_id,count(fixer_accounting.fixer_id),sum(fixer_accounting.amount_paid),user.first_name,user.last_name ,user.email from fixer_accounting LEFT JOIN user on fixer_accounting.fixer_id=user.user_id where (fixer_accounting.status=0 and(user.user_name like '%"
					+ searchFieldText + "%' or user.email like '%" + searchFieldText + "%' or user.first_name like '%"
					+ searchFieldText + "%' or user.last_name like '%" + searchFieldText + "%')) group by fixer_id";
			javax.persistence.Query query = entityManager.createNativeQuery(sqlQuery, "fixerAccountingUserGroup");
			List<FixerAccountingGroup> groups = query.getResultList();
			return (Integer) groups.size();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long searchListOfUserUsingSearchTextCount(String searchFieldText) throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfUserUsingSearchTextCount", -1, -1);
			return (Long) (query.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long searchListOfChatUserUsingSearchTextCount(String searchFieldText) throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfChatUserUsingSearchTextCount", -1, -1);
			return (Long) (query.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public Long searchListOfUserFixersUsingSearchTextCount(String searchFieldText) throws DataAccessException {
		try {
			Query query = createNamedQuery("searchListOfUserFixersUsingSearchTextCount", -1, -1);
			return (Long) (query.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Set<User> searchListOfUsersBasedOnSearchFieldAndRating(String searchFieldText, String selectedRate,
			String country, int startIndex, int maxResult) throws DataAccessException {
		
		try {
			Query query = createNamedQuery("getFixersBasedOnSearchCatRatingAndCountry", startIndex, maxResult);
			query.setParameter("name", "%" + searchFieldText + "%");
			query.setParameter("country", "%" + country + "%");
			return new LinkedHashSet<User>(query.getResultList());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<Object> findFixerByFilter(String searchText, List<Integer> categories, String country, Integer offSet,
			Integer limit, Integer editQueryId) throws DataAccessException {
		String sqlQuery = "select * from((select user.*,avg(star_rating) as rate , 1 as coloumId from user left join fixer_rating on user.user_id = fixer_rating.fixer_id where user.user_id IN ( "
				+ " select user_id from user_cat where cat_id  IN(:selectedValues)) AND user.country='" + country
				+ "' AND user.user_type='F' AND (user.first_name like '%" + searchText + "%' || user.last_name like '%"
				+ searchText + "%' || user.user_name like '%" + searchText
				+ "%' || CONCAT_WS(\" \", user.first_name, user.last_name) like '%" + searchText
				+ "%') group by user.user_id  ) UNION "
				+ "(select user.*,avg(star_rating) as rate , 2 as coloumId from user left join fixer_rating on user.user_id = fixer_rating.fixer_id where user.user_id IN ( "
				+ "select user_id from user_cat where cat_id  IN(:selectedValues)) AND user.country!='" + country
				+ "' AND user.user_type='F' AND (user.first_name like '%" + searchText + "%' || user.last_name like '%"
				+ searchText + "%' || user.user_name like '%" + searchText
				+ "%' || CONCAT_WS(\" \", user.first_name, user.last_name) like '%" + searchText
				+ "%') group by user.user_id  ) UNION "
				+ "(select user.*,avg(star_rating) as rate , 3 as coloumId from user left join fixer_rating on user.user_id = fixer_rating.fixer_id where user.user_id IN ( "
				+ "select user_id from user_cat where cat_id NOT IN(:selectedValues)) AND user.country='" + country
				+ "' AND user.user_type='F' AND (user.first_name like '%" + searchText + "%' || user.last_name like '%"
				+ searchText + "%' || user.user_name like '%" + searchText
				+ "%' || CONCAT_WS(\" \", user.first_name, user.last_name) like '%" + searchText
				+ "%') group by user.user_id) UNION "
				+ "(select user.*,avg(star_rating) as rate , 4 as coloumId from user left join fixer_rating on user.user_id = fixer_rating.fixer_id where user.user_id IN ( "
				+ "select user_id from user_cat where cat_id NOT IN(:selectedValues)) AND user.country!='" + country
				+ "' AND user.user_type='F' AND (user.first_name like '%" + searchText + "%' || user.last_name like '%"
				+ searchText + "%' || user.user_name like '%" + searchText
				+ "%' || CONCAT_WS(\" \", user.first_name, user.last_name) like '%" + searchText
				+ "%') group by user.user_id)) resultSet where  resultSet.user_id NOT IN (select user_id from user_decline where query_id='"+editQueryId+"') and resultSet.user_id NOT IN (select fixer_id from query_applied_fixers where query_id='"+editQueryId+"'and status='D')  group by resultSet.user_id order by coloumId,rate DESC LIMIT "
				+ offSet + "," + limit + "";
		Query query = entityManager.createNativeQuery(sqlQuery);
		query.setParameter("selectedValues", categories);
		
		return query.getResultList();
	}

	@Override
	@Transactional
	public Set<User> findUserBySource(String source, int startIndex, int maxResult) {
		try {
			Query query = createNamedQuery("findUserBySource", startIndex, maxResult, source);
			Set<User> userBySource = new LinkedHashSet<User>(query.getResultList());
			return userBySource;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> findUserBySourceAndName(String source, String searchText, int startIndex, int maxResult) {
		try {
			searchText = "%" + searchText + "%";
			Query query = createNamedQuery("findUserBySourceAndName", startIndex, maxResult, source, searchText);
			Set<User> userBySource = new LinkedHashSet<User>(query.getResultList());
			return userBySource;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public long findCountUserBySource(String source) throws DataAccessException {
		try {
			Query query = createNamedQuery("findCountUserBySource", -1, -1, source);
			return (Long)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public long findCountUserBySourceAndName(String source, String searchText) throws DataAccessException {
		try {
			searchText  = "%"+searchText+"%";
			Query query = createNamedQuery("findCountUserBySourceAndName", -1, -1, source, searchText);
			return (Long)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateFixerRatingByAdmin(int rating, int userId) throws DataAccessException {
		
		Query query =  createNamedQuery("updateFixerRatingByAdmin", -1, -1, rating, userId);
		int i = query.executeUpdate();
		return i;
	}

}
