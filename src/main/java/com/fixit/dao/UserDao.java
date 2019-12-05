package com.fixit.dao;

import java.util.List;
import java.util.Set;

import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import com.fixit.domain.bo.FixerAccountingUserGroup;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface UserDao extends JpaDao<User> {

	/**
	 * used to find user based on userId.
	 * 
	 * @param userId
	 *            -Take arguement as Integer.
	 * @return User
	 */
	public User findUserByUserId(Integer userId);

	/**
	 * used to get all the members.
	 * 
	 * @param startResult
	 *            - firstResult the first row to be returned
	 * @param maxRows
	 *            - the maximum number of rows to be returned.
	 * @return Set<User>
	 */
	public Set<User> getAllMembers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * used to get all the fixers.
	 * 
	 * @param startResult
	 *            - firstResult the first row to be returned
	 * @param maxRows
	 *            - the maximum number of rows to be returned.
	 * @return Set<User>
	 */
	public Set<User> getAllFixers(int startResult, int maxRows) throws DataAccessException;

	/**
	 * used to get user based on email or userName.
	 * 
	 * @param email
	 *            -Take String as an argument.
	 * @param userName
	 *            -Take String as an argument.
	 * @return List<User>
	 */
	public List<User> findUserByEmailOrUserName(String email, String userName) throws DataAccessException;

	/**
	 * used to find user by email for update settings.
	 * 
	 * @param email
	 *            -Take String as an argument.
	 * @param oldEmail
	 *            -Take String as an argument.
	 * @return List<User>
	 */
	public List<User> findUserByEmailForUpdateSettings(String email, String oldEmail) throws DataAccessException;

	/**
	 * used to find user by username for update settings.
	 * 
	 * @param userName
	 *            -Take String as an argument.
	 * @param oldUserName
	 *            -Take String as an argument.
	 * @return List<User>
	 */
	public List<User> findUserByUserNameForUpdateSettings(String userName, String oldUserName)
			throws DataAccessException;

	public Set<User> getAllMemberAndActivatedFixers(int startIndex, int maxResult) throws DataAccessException;

	/**
	 * used to get list of fixers with activated status.
	 * 
	 * @param currentStatus
	 *            -Take String as an argument.
	 * @param userType
	 *            -Take String as an argument.
	 * @param startResult
	 *            - firstResult the first row to be returned
	 * @param maxRows
	 *            - the maximum number of rows to be returned.
	 * @return Set<User>
	 */
	public Set<User> getListOfFixerWithActivtedStatus(String currentStatus, String userType, int startIndex,
			int maxResult) throws DataAccessException;

	/**
	 * used to find user by email.
	 * 
	 * @param email
	 *            -Take String as an argument.
	 * @return List<User>
	 */
	public List<User> findUserByEmail(String email) throws DataAccessException;

	/**
	 * used to delete user by userid.
	 * 
	 * @param userid
	 *            -Take Integer as an argument.
	 * @return List<User>
	 */
	public Integer deleteUserByUserId(Integer userid) throws DataAccessException;

	/**
	 * used to find user by userid.
	 * 
	 * @param userid
	 *            -Take Integer as an argument.
	 * @return User
	 */
	public User findUserByUserId(Integer userId, int startResult, int max);

	/**
	 * used to find all fixers.
	 * 
	 * @return Set<User>
	 */
	public Set<User> findAllFixer() throws DataAccessException;

	/**
	 * used to delete all member data.
	 * 
	 * @param userid
	 *            -Take Integer as an argument.
	 * @return User
	 */
	public Integer deleteMemberAllData(Integer userId) throws DataAccessException;

	/**
	 * 
	 * @param startResult
	 * @param maxRows
	 * @return
	 * @throws DataAccessException
	 */
	public Set<User> findAllFixer(int startResult, int maxRows) throws DataAccessException;

	/**
	 * 
	 * @param categories
	 * @return
	 * @throws DataAccessException
	 */
	public Set<User> findFixerByCategory(List<Integer> categories) throws DataAccessException;

	/**
	 * 
	 * @param categories
	 * @param startResult
	 * @param maxRows
	 * @return
	 * @throws DataAccessException
	 */
	public Set<User> findFixerByCategory(List<Integer> categories, int startResult, int maxRows)
			throws DataAccessException;

	public Set<User> findFixerWithStatus(int startResult, int maxRows, String fixerStatus) throws DataAccessException;

	public long findFixerCountWithStatus(int startResult, int maxRows, String fixerStatus) throws DataAccessException;

	public int updateFixerByAdmin(int userId, String fixerStatus) throws DataAccessException;

	public Set<Query> findAllNotSureQueries(int startResult, int maxRows, int fixerId) throws DataAccessException;

	public Set<Query> findAllReviewQueries(int startResult, int maxRows, String status) throws DataAccessException;

	public long findAllReviewQueriesCount(int startResult, int maxRows, String status) throws DataAccessException;

	public Set<User> findFixerWhoMatchQueryCat(Integer queryId);
	
	public Set<User> findNotIntrestedAndRemovedFixer(Integer queryId);

	public Set<User> getListOfFavouriteFixerByUserId(Integer userId, int startIndex, int maxResult);

	public Set<User> searchListOfUsersBasedOnSearchField(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException;

	public Set<User> searchListOfUsersBasedOnSearchFieldAndCategory(List<Integer> categories, String searchFieldText,
			int startIndex, int maxResult) throws DataAccessException;

	public Set<User> findAdminByUserType(String userType, int startResult, int maxRows) throws DataAccessException;

	public Set<User> findAllUserBasedOnIdList(Set<Integer> userIdList, int startResult, int maxRows)
			throws DataAccessException;

	public Integer searchListOfFixerUsingSearchTextCount(String searchFieldText) throws DataAccessException;

	public Long searchListOfUserUsingSearchTextCount(String searchFieldText) throws DataAccessException;

	public Long searchListOfUserFixersUsingSearchTextCount(String searchFieldText) throws DataAccessException;

	public Long searchListOfChatUserUsingSearchTextCount(String searchFieldText) throws DataAccessException;

	public Set<FixerAccountingUserGroup> searchListOfFixerUsingSearchText(String searchFieldText, int startIndex,
			int maxResult) throws DataAccessException;

	public Set<User> searchListOfUserUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException;

	public Set<User> searchListOfChatUserUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException;

	public Set<User> searchListOfUserFixersUsingSearchText(String searchFieldText, int startIndex, int maxResult)
			throws DataAccessException;

	public Set<User> searchListOfUsersBasedOnSearchFieldAndRating(String searchFieldText, String selectedRate,
			String country, int startIndex, int maxResult) throws DataAccessException;

	Set<User> searchListOfUsersBasedOnSearchFieldCategoryAndCountry(List<Integer> categories, String searchFieldText,
			String selectedRate, String country, int startIndex, int maxResult) throws DataAccessException;

	public List<Object> findFixerByFilter(String searchText, List<Integer> categories, String country, Integer offSet,
			Integer limit, Integer editQueryId) throws DataAccessException;

	public Set<User> findUserBySource(String source, int startIndex, int maxResult) ;
	public long findCountUserBySource(String source) throws DataAccessException;
	
	public Set<User> findUserBySourceAndName(String source,String searchText, int startIndex, int maxResult) ;
	public long findCountUserBySourceAndName(String source,String searchText) throws DataAccessException;
	
	public int updateFixerRatingByAdmin(int rating, int userId) throws DataAccessException;

}
