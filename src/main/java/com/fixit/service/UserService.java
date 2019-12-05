package com.fixit.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fixit.domain.bo.AppliedFixersListBo;
import com.fixit.domain.bo.FixerProfile;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.bo.FixerSetting;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.MemberProfile;
import com.fixit.domain.bo.MemberSetting;
import com.fixit.domain.bo.MemberSignUpProfile;
import com.fixit.domain.bo.QueryData;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.UserGroupsList;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.User;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FixitException;

public interface UserService extends UserDetailsService {
	public User updateFixerSettings(FixerSetting fixerSetting, User user) throws FixitException;

	/**
	 * 
	 * used to get the fixer based on their status find the user based on userId
	 * 
	 * @param userId
	 *            - Take argument userId as Integer.
	 * @return return User.
	 */
	public User findUserById(Integer userId);

	/**
	 * 
	 * used to save the new password of User
	 * 
	 * @param user
	 *            - Take argument as User.
	 * @param password
	 *            -Take argument password as String.
	 * @return return User.
	 */
	public User saveUserNewPassword(User user, String password) throws FixitException;

	/**
	 * 
	 * used to save the new User
	 * 
	 * @param userBo
	 *            - Take argument as UserBo.
	 * @return return User.
	 */
	public User saveUser(MemberSignUpProfile userBo) throws FixitException;

	/**
	 * 
	 * used to save the new Fixer.
	 * 
	 * @param profile
	 *            - Take argument as FixerProfile.
	 * @return return User.
	 */
	public User saveFixer(FixerProfile profile) throws FixitException;

	/**
	 * 
	 * used to save the User.
	 * 
	 * @param user
	 *            - Take argument as user.
	 * @return return User.
	 */
	public User updateUser(User user) throws FixitException;

	/**
	 * 
	 * used to update the User Settings.
	 * 
	 * @param userBo
	 *            - Take argument as UserBo.
	 * @param user
	 *            - Take argument as User.
	 * @return return User.
	 */
	public User updateUserSettings(MemberSetting memberSetting, User user) throws FixitException;

	/**
	 * 
	 * used to update the User Alert.
	 * 
	 * @param userBo
	 *            - Take argument as UserBo.
	 * @param user
	 *            - Take argument as User.
	 * @return return User.
	 */
	public User updateUserAlert(UserBo userBo, User user) throws FixitException;

	/**
	 * 
	 * used to get statistics of based on userId.
	 * 
	 * @param type
	 *            - Take argument as String.
	 * @param userId
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<Query> getStats(String type, Integer userId);

	public Query insertData();

	/**
	 * 
	 * used to get fixer query data.
	 * 
	 * @param type
	 *            - Take argument as String.
	 * @param userId
	 *            - Take argument as Integer.
	 * @param offSet
	 *            - Take argument as Integer.
	 * @param noOfRecords
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<QueryData> getFixerQueriesStuff(Integer userId, String type, int offSet, int noOfRecords);

	/**
	 * 
	 * used to get customer query data.
	 * 
	 * @param type
	 *            - Take argument as String.
	 * @param userId
	 *            - Take argument as Integer.
	 * @param min
	 *            - Take argument as Integer.
	 * @param max
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	Set<QueryData> getCustomerQueriesStuff(Integer userId, String type, int min, int max);

	/**
	 * 
	 * used to check if already existed Email or UserName.
	 * 
	 * @param email
	 *            - Take argument as String.
	 * @param userName
	 *            - Take argument as String.
	 * @return return List.
	 */
	public List<User> checkExistingEmailOrUserName(String email, String userName) throws FixitException;

	/**
	 * 
	 * used to check existed email for settings update.
	 * 
	 * @param email
	 *            - Take argument as String.
	 * @param oldEmail
	 *            - Take argument as String.
	 * @return return List.
	 */
	public List<User> checkExistingEmailForSettingsUpdate(String email, String oldEmail) throws FixitException;

	/**
	 * 
	 * used to check existed userName for settings update.
	 * 
	 * @param email
	 *            - Take argument as String.
	 * @param oldUserName
	 *            - Take argument as String.
	 * @return return List.
	 */
	public List<User> checkExistingUserNameForSettingsUpdate(String userName, String oldUserName) throws FixitException;

	public Set<UserGroupsList> getAllMemberAndActivatedFixers(int startIndex, int maxResult) throws FixitException;

	/**
	 * 
	 * used to check existed email.
	 * 
	 * @param email
	 *            - Take argument as String.
	 * @return return List.
	 */
	public List<User> checkExistingEmail(String email) throws FixitException;

	/**
	 * 
	 * used to get customer list based on query list.
	 * 
	 * @param queries
	 *            - Take argument as Set.
	 * @return return Set.
	 */
	public Set<User> findCustomerListFromQueryList(Set<Query> queries) throws FixitException;

	/**
	 * 
	 * used to get favorite fixers list based on UserId.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param startIndex
	 *            - Take argument as Integer.
	 * @param maxResult
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<FixerSearchBo> getFavouriteFixersList(int userId, int startIndex, int maxResult) throws FixitException;

	/**
	 * 
	 * used to get favorite fixers list based on userId and searchFieldText.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param startIndex
	 *            - Take argument as Integer.
	 * @param maxResult
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<FixerSearchBo> searchListOfUsers(Integer userId, String searchFieldText, int startIndex, int maxResult)
			throws FixitException;

	/**
	 * 
	 * used to get favorite fixers list based on userId , searchFieldText and
	 * user categories.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param categories
	 *            - Take argument as List.
	 * @param startIndex
	 *            - Take argument as Integer.
	 * @param maxResult
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<FixerSearchBo> searchListOfUserswithCategory(Integer userId, List<Integer> categories,
			String searchFieldText, String selectedRate, String country, int startIndex, int maxResult)
			throws FixitException;

	/**
	 * 
	 * used to get fixers list based on query list.
	 * 
	 * @param queries
	 *            - Take argument as Set.
	 * @return return Set.
	 */
	public Set<User> findFixerListFromQueryList(Set<Query> queries) throws FixitException;

	/**
	 * 
	 * used to update the member profile.
	 * 
	 * @param profile
	 *            - Take argument as MemberProfile.
	 * @param pathImage
	 *            - Take argument as String.
	 * @return return User.
	 */
	public User updateMemberProfile(MemberProfile profile, String pathImage) throws FixitException;

	public User updateMemberSignUpProfile(MemberSignUpProfile profile, String pathImage) throws FixitException;

	/**
	 * 
	 * used to update the profile image.
	 * 
	 * @param image
	 *            - Take argument as MultipartFile.
	 * @param path
	 *            - Take argument as String.
	 * @param folderName
	 *            -Take argument as String.
	 * @return return User.
	 */
	public String uploadProfileImage(MultipartFile image, String path, String folderName) throws FixitException;

	/**
	 * 
	 * used to update the fixer profile.
	 * 
	 * @param profile
	 *            - Take argument as FixerProfile.
	 * @param pathImage
	 *            - Take argument as String.
	 * @return return User.
	 */
	public User updateFixerProfile(FixerProfile profile, String pathImage) throws FixitException;
	
	/**
	 * 
	 * used to update the fixer profile.
	 * 
	 * @param profile
	 *            - Take argument as FixerProfile.
	 * @return return User.
	 */
	
	public User updateFixerProfile(FixerProfile profile) throws FixitException;

	/**
	 * 
	 * used to delete the member all data.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @return return Integer.
	 */
	public Integer deleteUserAllData(Integer userId) throws FixitException;

	/**
	 * 
	 * used to find the member queries that are not accepted.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calender.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findMemberQueryNotAccepted(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the member queries that are working.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calender.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findMemberQueryWorking(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the member queries that are closed.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calender.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findMemberQueryClosed(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the member queries that are pending resolution.
	 * 
	 * @param userId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calendar.
	 * @param timeZone
	 *            -Take argument timezone as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findMemberQueryPendingResolution(Integer userId, Calendar currentTime,
			String timeZone, int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the fixer queries that are not accepted.
	 * 
	 * @param fixerId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calendar.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findFixerQueryNotAccepted(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the fixer queries that are working.
	 * 
	 * @param fixerId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calendar.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findFixerQueryWorking(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the fixer queries that are closed.
	 * 
	 * @param fixerId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calendar.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findFixerQueryClosed(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the fixer queries that are pending resolution.
	 * 
	 * @param fixerId
	 *            - Take argument as Integer.
	 * @param currentTime
	 *            - Take argument as Calendar.
	 * @param timeZone
	 *            -Take argument as String.
	 * @param startIndex
	 *            -Take argument as Integer.
	 * @param maxResult
	 *            -Take argument as Integer.
	 * @return return Set.
	 */
	public Set<MemberDashboardBo> findFixerQueryPendingResolution(Integer fixerId, Calendar currentTime,
			String timeZone, int startIndex, int maxResult) throws Exception;

	/**
	 * 
	 * used to find the fixers whoc matches with the query category.
	 * 
	 * @param queryId
	 *            - Take argument as Integer.
	 * @return return Set.
	 */
	public Set<User> findFixerWhoMatchQueryCat(Integer queryId) throws FixitException;

	public Set<User> findNotIntrestedAndRemovedFixer(Integer queryId) throws FixitException;
	/**
	 * 
	 * used to find the admin of the application.
	 * 
	 * @param status
	 *            - Take argument as String.
	 * @return return Set.
	 */
	public Set<User> findAdminByStatus(String status) throws FixitException;

	public Set<User> findAllUserBasedOnIdList(Set<Integer> userIdList, int startIndex, int maxResult)
			throws FixitException;

	public Set<FixerSearchBo> searchListOfUserWithRatingAndCountry(int userId, String searchFieldText,
			String selectedRate, String country, int i, int j) throws FixitException;

	UserBo findFixerBoById(Integer fixerId, int userId);

	UserBo findMemberBoById(Integer userId);

	Set<MemberDashboardBo> findFixerQueryApplied(Integer fixerId, Calendar currentTime, String timeZone, int startIndex,
			int maxResult) throws Exception;

	public List<FixerSearchBo> findFixerByFilter(String searchText, Integer userId, List<Integer> categories,
			String country, Integer offSet, Integer limit,Integer editQueryId) throws FixitException;

	public Set<User> findUserBySource(String source, int startIndex, int maxResult);
	public long findCountUserBySource(String source) throws DataAccessException;

	public Set<User> findUserBySourceAndName(String source, String searchText, int startIndex, int maxResult);
	public long findCountUserBySourceAndName(String source,String searchText) throws DataAccessException;
	public List<AppliedFixersListBo> setAppliedFixerList(Set<User> selectedFixer,Integer userId)  ;
	public List<String> findAllParentCategoryByCatId(List<Integer> catId) ;
	public void fixerapproval( Query query, HttpServletRequest request, User fixer) ;


}
