
package com.fixit.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.bcel.generic.FADD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fixit.dao.CategoryTypeDao;
import com.fixit.dao.FavouriteFixerDao;
import com.fixit.dao.IndustryTypeDao;
import com.fixit.dao.QueryAppliedFixersDao;
import com.fixit.dao.QueryAuditDao;
import com.fixit.dao.QueryCategoryDao;
import com.fixit.dao.QueryDao;
import com.fixit.dao.QueryFilesDao;
import com.fixit.dao.UserCategoryDao;
import com.fixit.dao.UserCreditDao;
import com.fixit.dao.UserDao;
import com.fixit.dao.UserDeclineDao;
import com.fixit.dao.UserIndustryDao;
import com.fixit.dao.UsersAccountingDao;
import com.fixit.dao.VerificationDao;
import com.fixit.domain.bo.AppliedFixersListBo;
import com.fixit.domain.bo.FixerProfile;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.bo.FixerSetting;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.MemberProfile;
import com.fixit.domain.bo.MemberSetting;
import com.fixit.domain.bo.MemberSignUpProfile;
import com.fixit.domain.bo.QueryData;
import com.fixit.domain.bo.QueryFilesBo;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.UserGroupsList;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.FavouriteFixer;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryCategory;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.domain.vo.UsersAccounting;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.MailChimpApi;
import com.fixit.utility.PasswordEncode;
import com.fixit.utility.QueryCurrentStatus;
import com.fixit.utility.TimeDiffUtility;
import com.fixit.utility.TimeZoneList;
import com.fixit.utility.UserTypeEnum;
import com.google.gson.Gson;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final String MSG_BAD_CREDENTIALS = null;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FixerRatingService fixerRatingService;
	@Autowired
	private QueryDao queryDao;

	@Autowired
	private QueryAuditDao queryAuditDao;

	@Autowired
	private UserCreditDao userCreditDao;

	@Autowired
	private UsersAccountingDao usersAccountingDao;

	@Autowired
	private FavouriteFixerDao favouriteFixerDao;
	@Autowired
	private FavouriteFixerService favouriteFixerService;
	@Autowired
	private QueryCategoryDao queryCategoryDao;

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Autowired
	private QueryFilesDao queryFilesDao;

	@Autowired
	private UserCategoryDao userCategoryDao;

	@Autowired
	private IndustryTypeDao industryTypeDao;

	@Autowired
	private UserDeclineDao userDeclineDao;

	@Autowired
	private VerificationDao verificationDao;

	@Autowired
	private UserIndustryDao userIndustryDao;

	@Autowired
	private UserDeclineService userDeclineService;

	@Autowired
	private QueryFixersService queryFixersService;

	@Autowired
	QueryAppliedFixersDao queryFixerDao;
	@Autowired
	private QueryAppliedFixersService queryAppliedFixersService;
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private EmailServcie emailServcie;
	@Override
	@Transactional
	public User updateFixerSettings(FixerSetting fixerSetting, User user) throws FixitException {

		String imageUrl = fixerSetting.getImageUrl();
		if (imageUrl != null && !imageUrl.trim().isEmpty()) {
			user.setProfilePhoto(FileUpload.fixerProfilePath(imageUrl));
			user.setProfileIcon(FileUpload
					.fixerProfilePath(FileUpload.getFixerIconFileNameFromImage(imageUrl, fixerSetting.getUserName())));
		}

		user.setEmail(fixerSetting.getEmail());
		user.setUserName(fixerSetting.getUserName());
		user.setEmailAlert(fixerSetting.getEmailAlert());
		user.setPaypalId(fixerSetting.getPaypalId());
		user.setTimeZone(fixerSetting.getTimeZone());
		try {
			user = userDao.store(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FixitException("user.notUpdated", "user.notUpdated");
		}
		return user;

	}

	@Override
	public User findUserById(Integer userId) {

		return userDao.findUserByUserId(userId, -1, -1);
	}

	@Override
	@Transactional
	public UserBo findFixerBoById(Integer fixerId, int userId) {
		UserBo userFixer = new UserBo();
		try {
			User fixer = userDao.findUserByUserId(fixerId, -1, -1);

			userFixer.setFirstName(fixer.getFirstName());
			userFixer.setLastName(fixer.getLastName());
			userFixer.setCompanyName(fixer.getCompanyName());
			userFixer.setCountry(fixer.getCountry());
			userFixer.setLinkedinProfile(fixer.getLinkedinProfile());
			userFixer.setOverview(fixer.getOverview());

			FavouriteFixer favouriteFixer = favouriteFixerService.findFavFixerByIds(userId, fixerId);
			if (favouriteFixer != null) {
				userFixer.setFavouriteFixerStatus(DbConstants.FIXER_STATUS_FAVOURITE);
			} else {
				userFixer.setFavouriteFixerStatus(DbConstants.FIXER_STATUS_UNFAVOURITE);
			}
			long closedCount = queryDao.findFixerClosedStasCount(fixerId);
			userFixer.setFixedCounts(closedCount);

			Calendar calendar = fixer.getCreatedAt();
			String userSince = TimeDiffUtility.getUserFrom(calendar);

			Calendar currentTime = Calendar.getInstance();
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(fixer.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(fixer.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(fixer.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(fixer.getLastLogin(), currentTime));
			userFixer.setLastLogin(lastLogin + " ago");

			long deadlinecounts = queryDao.findFixerClosedRequestsCountInDeadline(fixer.getUserId());

			userFixer.setFixedUnderdeadline(deadlinecounts);

			List<String> userCats = new ArrayList<String>();
			Iterator<UserCategory> it = fixer.getFixerCategory().iterator();
			while (it.hasNext()) {
				UserCategory cat = (UserCategory) it.next();
				userCats.add(cat.getCategoryType().getCatgName());
			}
			userFixer.setCategoryList(userCats);
			
			userFixer.setCity(fixer.getCity());
			userFixer.setUserId(fixer.getUserId());
			userFixer.setProfilePhoto(fixer.getProfilePhoto());
			userFixer.setPaypalId(fixer.getPaypalId());
			userFixer.setUserId(fixer.getUserId());
			userFixer.setFixersSince(userSince);
			userFixer.setTimeZone(fixer.getTimeZone());
			double rating = fixerRatingService.getAggregateRatingsOfFixer(fixer.getUserId(), fixer.getRating());
			userFixer.setFixerRating(rating);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userFixer;
	}

	@Override
	public UserBo findMemberBoById(Integer userId) {
		UserBo userMember = new UserBo();
		try {
			User member = userDao.findUserByUserId(userId, -1, -1);

			userMember.setFirstName(member.getFirstName());
			userMember.setLastName(member.getLastName());
			userMember.setCompanyName(member.getCompanyName());
			userMember.setCountry(member.getCountry());
			userMember.setLinkedinProfile(member.getLinkedinProfile());
			userMember.setOverview(member.getOverview());
			userMember.setTimeZone(member.getTimeZone());
			Calendar calendar = member.getCreatedAt();
			String userSince = TimeDiffUtility.getUserFrom(calendar);
			List<String> userCats = new ArrayList<String>();

			Iterator<UserIndustry> it = member.getUserIndustry().iterator();

			while (it.hasNext()) {
				UserIndustry cat = (UserIndustry) it.next();
				userCats.add(cat.getIndustryType().getIndstName());
			}
			userMember.setCategoryList(userCats);
			
			userMember.setCity(member.getCity());
			userMember.setUserId(member.getUserId());
			userMember.setProfilePhoto(member.getProfilePhoto());

			userMember.setUserId(member.getUserId());
			userMember.setFixersSince(userSince);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMember;
	}

	@Override
	public Set<User> findAdminByStatus(String status) {
		try {
			return userDao.findAdminByUserType("A", -1, -1);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public Set<User> findAllUserBasedOnIdList(Set<Integer> userIdList, int startIndex, int maxResult)
			throws FixitException {
		try {
			return userDao.findAllUserBasedOnIdList(userIdList, startIndex, maxResult);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public User saveUser(MemberSignUpProfile userBo) throws FixitException {
		User member;
		try {
			member = new User();
			member.setEmail(userBo.getEmail());
			member.setUserType(DbConstants.CUSTOMER);
			member.setPassword(PasswordEncode.encodePassword(userBo.getPassword()));
			member.setLastLogin(Calendar.getInstance());
			member.setUserType(userBo.getUserType());
			member.setUserName(userBo.getUserName());
			member.setFirstName(userBo.getFirstName());
			member.setLastName(userBo.getLastName());
			member.setCompanyName(userBo.getCompanyName());
			member.setCity(userBo.getCity());
			member.setCountry(userBo.getCountry());
			member.setTimeZone(TimeZoneList.displayTimeZoneGMT(userBo.getTimeZone()));
			member.setEmailAlert(userBo.getSendEmail());
			member.setSource(userBo.getSource());
			member.setTrackCredit("N");
			// hardCoded
			member.setSubcPayment(DbConstants.PAYMENT_OPTION);
			member = userDao.store(member);
			if ("Y".equals(userBo.getSendEmail())) {
				String id = MailChimpApi.getListid();
				MailChimpApi.saveUserToMailChimp(member, id);
			}
		} catch (Exception e) {
			throw new FixitException("member.notsaved", "member.notsaved");
		}
		userDao.flush();
		return member;

	}

	@Transactional
	@Override
	public User saveUserNewPassword(User user, String password) throws FixitException {
		try {
			User myUser = findUserById(user.getUserId());
			String encodedPassword = PasswordEncode.encodePassword(password);
			myUser.setPassword(encodedPassword);
			userDao.store(myUser);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	@Transactional
	@Override
	public User saveFixer(FixerProfile fixerRegisterBo) throws FixitException {
		User fixer;
		try {
			fixer = new User();
			fixer.setEmail(fixerRegisterBo.getEmail());
			fixer.setUserType(DbConstants.FIXER);
			fixer.setPassword(fixerRegisterBo.getPassword());
			fixer.setLastLogin(Calendar.getInstance());
			fixer.setUserType(fixerRegisterBo.getUserType());
			fixer.setProfilePhoto(fixerRegisterBo.getImageUrl());
			fixer.setFirstName(fixerRegisterBo.getFirstName());
			fixer.setLastName(fixerRegisterBo.getLastName());
			fixer.setUserName(fixerRegisterBo.getUserName());
			fixer.setCity(fixerRegisterBo.getCity());
			fixer.setPaypalId(fixerRegisterBo.getPaypalId());
			fixer.setPhoneNumber(fixerRegisterBo.getMobileNumber());
			fixer.setCountry(fixerRegisterBo.getCountry());
			fixer.setTimeZone(TimeZoneList.displayTimeZoneGMT(fixerRegisterBo.getTimeZone()));
			fixer.setFixerStatus(fixerRegisterBo.getFixerStatus());
			fixer.setLinkedinProfile(fixerRegisterBo.getLinkedinProfile());
			fixer.setEmailAlert(fixerRegisterBo.getSendEmail());
			// hardCoded
			fixer.setSubcPayment(DbConstants.PAYMENT_OPTION);
			fixer = userDao.store(fixer);
			if ("Y".equals(fixerRegisterBo.getSendEmail())) {
				String id = MailChimpApi.getListid();
				MailChimpApi.saveUserToMailChimp(fixer, id);
			}

			userDao.flush();
		} catch (Exception e) {
			throw new FixitException("fixer.notsaved", "fixer.notsaved");
		}
		return fixer;
	}

	@Override
	@Transactional
	public User updateUser(User user) throws FixitException {
		try {
			user = userDao.store(user);
		} catch (Exception e) {
			throw new FixitException("user.notUpdated", "user.notUpdated");
		}
		return user;
	}

	@Override
	@Transactional
	public User updateUserSettings(MemberSetting memberSetting, User user) throws FixitException {

		String imageUrl = memberSetting.getImageUrl();

		if (imageUrl != null && !imageUrl.trim().isEmpty()) {
			user.setProfilePhoto(FileUpload.memberProfilePath(imageUrl));
			user.setProfileIcon(FileUpload
					.memberProfilePath(FileUpload.getmemberIconFileNameFromImage(imageUrl, user.getUserId())));
		}

		user.setEmail(memberSetting.getEmail());
		user.setUserName(memberSetting.getUserName());
		user.setEmailAlert(memberSetting.getEmailAlert());
		user.setTimeZone(memberSetting.getTimeZone());
		try {
			user = userDao.store(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FixitException("user.notUpdated", "user.notUpdated");
		}
		return user;

	}

	@Override
	@Transactional
	public User updateUserAlert(UserBo userBo, User user) throws FixitException {
		user.setEmailAlert(userBo.getEmailAlert());
		try {
			user = userDao.store(user);
		} catch (Exception e) {
			throw new FixitException("user.notUpdated", "user.notUpdated");
		}
		return user;

	}

	
	@Override
	public Set<Query> getStats(String type, Integer userId) {
		Set<Query> s = new LinkedHashSet();
		if (UserTypeEnum.F.toString().equals(type)) {
			s = queryDao.getFixerStats(userId);
			return s;
		} else if (UserTypeEnum.C.toString().equals(type)) {
			s = queryDao.getCustomerStats(userId);
			return s;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Query insertData() {
		Query existingQuery = new Query();
		existingQuery.setUser(userDao.findUserByUserId(3, -1, -1));
		existingQuery.setFixerId(3);
		existingQuery.setQueryContent("testing CRONE JOB");
		existingQuery.setCurrentStatus(DbConstants.STATUS_OPEN);
		Calendar dateRaised = Calendar.getInstance();
		existingQuery.setDateRaised(dateRaised);
		existingQuery = queryDao.store(existingQuery);
		return existingQuery;
	}

	@Override
	public Set<QueryData> getFixerQueriesStuff(Integer userId, String type, int offSet, int noOfRecords) {

		Set<QueryData> queryDataSet = new HashSet<QueryData>();
		
		List<String> list = new ArrayList<String>();

		if ("AllQuestions".equals(type)) {
			list.add(QueryCurrentStatus.C.toString());
			list.add(QueryCurrentStatus.D.toString());
			list.add(QueryCurrentStatus.O.toString());
			list.add(QueryCurrentStatus.R.toString());
			list.add(QueryCurrentStatus.W.toString());
		} else if ("Disscussion".equals(type)) {
			list.add(QueryCurrentStatus.W.toString());

		} else if ("PendingResolution".equals(type)) {
			list.add(QueryCurrentStatus.R.toString());
		} else if ("Closed".equals(type)) {
			list.add(QueryCurrentStatus.C.toString());
		} else {
			return null;
		}
		

		Set<Query> setQuery = queryDao.getQuestionsByFixerId(userId, type, list, offSet, noOfRecords);
		for (Query q : setQuery) {
			QueryData queryData = new QueryData();
			queryData.setCustomerId(q.getUser().getUserId());
			queryData.setFixerId(q.getFixerId());
			queryData.setQueryContent(q.getQueryContent());
			queryDataSet.add(queryData);
		}

		

		return queryDataSet;

	}

	@Override
	public Set<QueryData> getCustomerQueriesStuff(Integer userId, String type, int min, int max) {
		Set<QueryData> queryDataSet = new HashSet<QueryData>();
	
		List<String> list = new ArrayList<String>();

		if ("AllQuestions".equals(type)) {
			list.add(QueryCurrentStatus.C.toString());
			list.add(QueryCurrentStatus.D.toString());
			list.add(QueryCurrentStatus.O.toString());
			list.add(QueryCurrentStatus.R.toString());
			list.add(QueryCurrentStatus.W.toString());
		} else if ("Disscussion".equals(type)) {
			list.add(QueryCurrentStatus.W.toString());

		} else if ("PendingResolution".equals(type)) {
			list.add(QueryCurrentStatus.R.toString());
		} else if ("Closed".equals(type)) {
			list.add(QueryCurrentStatus.C.toString());
		} else {
			return null;
		}
	

		Set<Query> setQuery = queryDao.getQuestionByCustomerId(userId, type, list, min, max);
		for (Query q : setQuery) {
			QueryData queryData = new QueryData();
			queryData.setCustomerId(q.getUser().getUserId());
			queryData.setFixerId(q.getFixerId());
			queryData.setQueryContent(q.getQueryContent());

			queryDataSet.add(queryData);
		}

		

		return queryDataSet;
	}

	@Override
	@Transactional
	public List<User> checkExistingEmailOrUserName(String email, String userName) throws FixitException {
		List<User> userExist;
		userExist = userDao.findUserByEmailOrUserName(email, userName);
		return userExist;

	}

	@Override
	@Transactional
	public List<User> checkExistingEmail(String email) throws FixitException {
		List<User> userExist;
		userExist = userDao.findUserByEmail(email);
		return userExist;

	}

	@Override
	@Transactional
	public Integer deleteUserAllData(Integer userId) throws FixitException {
		Integer myReturnType = -1;
		try {

		
			Set<Query> queries = new LinkedHashSet<Query>();
			queries = queryDao.findQueryByCustomerId(userId);
			Set<UsersAccounting> userAccountingData = new LinkedHashSet<UsersAccounting>();
			userAccountingData = usersAccountingDao.getDataFromUserAccountingBasedOnUseriD(userId);
			if (queries.size() == 0) {
				if (userAccountingData.size() == 0) {
					userIndustryDao.deleteUserIndustryBasedOnUserId(userId);
					userCategoryDao.deleteUserCategoryBasedOnUserId(userId);
					userCreditDao.deleteUserCreditPoints(userId);

					userDao.deleteUserByUserId(userId);
					myReturnType = 1;

				}
			}

		} catch (Exception e) {
			throw e;
		}

		return myReturnType;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		
		List<User> l = null;
		User user;
		try {
			l = checkExistingEmailOrUserName(arg0, arg0);
		} catch (FixitException e) {
			return null;
		}
		if (l != null && l.size() > 0) {
			user = l.get(0);
		} else {
			return null;
		}
		UserDetails springUser;
		if (UserTypeEnum.F.toString().equals(user.getUserType())) {
			if (("R".equals(user.getFixerStatus())) || (user.getFixerStatus() == null)) {
				MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
				throw new InsufficientAuthenticationException(messages.getMessage(MSG_BAD_CREDENTIALS, "R"));

			}

			if ("D".equals(user.getFixerStatus())) {
				MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

				throw new InsufficientAuthenticationException(messages.getMessage(MSG_BAD_CREDENTIALS, "D"));

			}
		}

		try {
			List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
			if (UserTypeEnum.F.toString().equals(user.getUserType())) {
				result.add(new SimpleGrantedAuthority("ROLE_FIXER"));
			} else if (UserTypeEnum.C.toString().equals(user.getUserType())) {
				result.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			} else if (UserTypeEnum.A.toString().equals(user.getUserType())) {
				result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {

			}
			result.add(new SimpleGrantedAuthority("read"));
			result.add(new SimpleGrantedAuthority("write"));
			result.add(new SimpleGrantedAuthority("userid " + user.getUserId()));
			springUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					true, true, true, true, result);
			
			updateUser(user);
			return springUser;

		} catch (Exception exception) {
			Map<String, String> extraInfo = new HashMap<String, String>();
			extraInfo.put("status", "failed");
			throw new UsernameNotFoundException("Invalid Credentials", extraInfo);
		}
	}

	@Override
	public Set<User> findCustomerListFromQueryList(Set<Query> queries) throws FixitException {
		Set<User> customers;
		Query query;
		customers = new HashSet<User>();
		Iterator<Query> iterator = queries.iterator();
		while (iterator.hasNext()) {
			query = iterator.next();
			customers.add(query.getUser());
		}
		return customers;
	}

	@Override
	public Set<FixerSearchBo> getFavouriteFixersList(int userId, int startIndex, int maxResult) throws FixitException {
		Set<User> users = new HashSet<>();
		Set<FixerSearchBo> fixers = new HashSet<>();

		try {
			users = userDao.getListOfFavouriteFixerByUserId(userId, startIndex, maxResult);
			for (User u : users) {
				FixerSearchBo fixerSearchBo = new FixerSearchBo();
				fixerSearchBo.setUserName(u.getUserName());
				fixerSearchBo.setFirstName(u.getFirstName());
				fixerSearchBo.setLastName(u.getLastName());
				fixerSearchBo.setEmail(u.getEmail());
				List<String> userCats = new ArrayList<String>();
				for (UserCategory userCat : u.getFixerCategory()) {
					userCats.add(userCat.getCategoryType().getCatgName());
				}
				double startPoints = fixerRatingService.getAggregateRatingsOfFixer(u.getUserId(), u.getRating());
				fixerSearchBo.setFixerRating(startPoints);
				fixerSearchBo.setCategoryList(userCats);
				fixerSearchBo.setUserId(u.getUserId());
				fixerSearchBo.setProfilePhoto(u.getProfilePhoto());
				fixerSearchBo.setOverview(u.getOverview());
				fixers.add(fixerSearchBo);

			}
			return fixers;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Set<User> findFixerListFromQueryList(Set<Query> queries) throws FixitException {
		Set<User> fixer;
		Query query;
		fixer = new HashSet<User>();
		Iterator<Query> iterator = queries.iterator();
		while (iterator.hasNext()) {
			query = iterator.next();
			fixer.add(userDao.findUserByUserId(query.getFixerId()));
		}
		return fixer;
	}

	@Override
	@Transactional
	public User updateMemberProfile(MemberProfile profile, String pathImage) throws FixitException {
		User existingUser;

		try {
			existingUser = userDao.findUserByUserId(profile.getUserId());
			if (existingUser != null) {
				
				
				existingUser.setFirstName(profile.getFirstName());
				existingUser.setLastName(profile.getLastName());
				existingUser.setCompanyName(profile.getCompanyName());
				existingUser.setCity(profile.getCity());
				existingUser.setCountry(profile.getCountry());
				existingUser.setLinkedinProfile(profile.getLinkedinProfile());
				existingUser.setOverview(profile.getOverview());

				if (profile.getImageUrl() != null) {
					existingUser.setProfilePhoto(FileUpload.memberProfilePath(profile.getImageUrl()));
					existingUser.setProfileIcon(FileUpload.memberProfilePath(
							FileUpload.getmemberIconFileNameFromImage(profile.getImageUrl(), profile.getUserId())));
				}

				
				existingUser = userDao.store(existingUser);

				

			} else {
				throw new FixitException("user.notfound", "user.notfound");
			}
		} catch (FixitException e) {
			throw new FixitException(e.getExceptionCode(), e.getExceptionMsg());
		} catch (Exception e) {
			throw new FixitException("user.notupdated", "user.notupdated");
		}

		return existingUser;
	}

	@Override
	@Transactional
	public User updateMemberSignUpProfile(MemberSignUpProfile profile, String pathImage) throws FixitException {
		User existingUser;

		try {
			existingUser = userDao.findUserByUserId(profile.getUserId());
			if (existingUser != null) {
				List<Integer> categories;
				UserIndustry userIndustry;
				IndustryType industryType;
				existingUser.setProfilePhoto(FileUpload.memberProfilePath(profile.getImageUrl()));
				try {
					existingUser.setProfileIcon(
							FileUpload.memberProfilePath(FileUpload.getmemberIconFileNameFromImageBasedOnUserName(
									profile.getImageUrl(), profile.getUserName())));
				} catch (Exception e) {

				}
				existingUser.setOverview(profile.getOverview());
				existingUser.setSource(profile.getSource());
				// uploading Image
				
				existingUser = userDao.store(existingUser);

				categories = profile.getCategories();
				userIndustryDao.deleteUserIndustry(profile.getUserId(), categories);
				for (int indstId : categories) {
					userIndustry = userIndustryDao.findUserIndustryByUserIdandIndstId(profile.getUserId(), indstId);
					if (userIndustry == null) {
						industryType = industryTypeDao.findIndustryTypeByIndstId(indstId);
						userIndustry = new UserIndustry();
						userIndustry.setUser(existingUser);
						userIndustry.setIndustryType(industryType);
						userIndustryDao.store(userIndustry);
					}
				}
			} else {
				throw new FixitException("user.notfound", "user.notfound");
			}
		} catch (FixitException e) {
			throw new FixitException(e.getExceptionCode(), e.getExceptionMsg());
		} catch (Exception e) {
			throw new FixitException("user.notupdated", "user.notupdated");
		}

		return existingUser;
	}

	@Override
	@Transactional
	public User updateFixerProfile(FixerProfile profile, String pathImage1) throws FixitException {
		User existingUser;

		try {
			existingUser = userDao.findUserByUserId(profile.getUserId());
			if (existingUser != null) {
				List<Integer> categories = new ArrayList<Integer>();
			
				// UserIndustry userIndustry;
				UserCategory userCategory;
				// IndustryType industryType;
				CategoryType categoryType;

				// uploading Image

				if (profile.getImageUrl() != null) {
					existingUser.setProfilePhoto(FileUpload.fixerProfilePath(profile.getImageUrl()));
					existingUser.setProfileIcon(FileUpload.fixerProfilePath(
							FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName())));
				}
				existingUser.setFirstName(profile.getFirstName());
				existingUser.setLastName(profile.getLastName());
				existingUser.setCompanyName(profile.getCompanyName());
				existingUser.setCity(profile.getCity());
				existingUser.setCountry(profile.getCountry());
				existingUser.setLinkedinProfile(profile.getLinkedinProfile());
				existingUser.setOverview(profile.getOverview());

				existingUser = userDao.store(existingUser);

				if (null != profile.getCategories()) {
					categories = profile.getCategories();

					
					int x = userCategoryDao.deleteUserCategory(profile.getUserId(), categories);
					
					for (int catId : categories) {
						userCategory = userCategoryDao.findUserCategoryByUserIdandcatId(profile.getUserId(), catId);

						

						if (userCategory == null) {
							categoryType = categoryTypeDao.findCategoryTypeByCatId(catId);

							
							userCategory = new UserCategory();
							userCategory.setUser(existingUser);
							userCategory.setCategoryType(categoryType);
							userCategoryDao.store(userCategory);
							
						}
					}
				}
			} else {
				throw new FixitException("user.notfound", "user.notfound");
			}
		} catch (FixitException e) {
			throw new FixitException(e.getExceptionCode(), e.getExceptionMsg());
		} catch (Exception e) {
			throw new FixitException("user.notupdated", "user.notupdated");
		}

		return existingUser;
	}
	
	@Override
	@Transactional
	public User updateFixerProfile(FixerProfile profile) throws FixitException {
		User existingUser;

		try {
			existingUser = userDao.findUserByUserId(profile.getUserId());
			if (existingUser != null) {
				List<Integer> categories = new ArrayList<Integer>();
				
				// UserIndustry userIndustry;
				UserCategory userCategory;
				// IndustryType industryType;
				CategoryType categoryType;

				// uploading Image

				
				existingUser.setFirstName(profile.getFirstName());
				existingUser.setLastName(profile.getLastName());
				existingUser.setCompanyName(profile.getCompanyName());
				existingUser.setCity(profile.getCity());
				existingUser.setCountry(profile.getCountry());
				existingUser.setLinkedinProfile(profile.getLinkedinProfile());
				existingUser.setOverview(profile.getOverview());

				existingUser = userDao.store(existingUser);

				if (null != profile.getCategories()) {
					categories = profile.getCategories();

					
					int x = userCategoryDao.deleteUserCategory(profile.getUserId(), categories);
					
					for (int catId : categories) {
						userCategory = userCategoryDao.findUserCategoryByUserIdandcatId(profile.getUserId(), catId);

					

						if (userCategory == null) {
							categoryType = categoryTypeDao.findCategoryTypeByCatId(catId);

							
							userCategory = new UserCategory();
							userCategory.setUser(existingUser);
							userCategory.setCategoryType(categoryType);
							userCategoryDao.store(userCategory);
							
						}
					}
				}
			} else {
				throw new FixitException("user.notfound", "user.notfound");
			}
		} catch (FixitException e) {
			throw new FixitException(e.getExceptionCode(), e.getExceptionMsg());
		} catch (Exception e) {
			throw new FixitException("user.notupdated", "user.notupdated");
		}

		return existingUser;
	}

	
	@Override
	public String uploadProfileImage(MultipartFile image, String path, String folderName) throws FixitException {
		String saveDirectory = null;
		String fileName = null;
		try {
			MultipartFile imageFile = image;
			saveDirectory = path + folderName + "\\";
			if (null != imageFile && imageFile.getSize() > 0) {
				fileName = imageFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					imageFile.transferTo(new File(saveDirectory + fileName));
					
				}
			}
			return saveDirectory + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return "image.not.uploaded";
			
		}

	}

	public Set<MemberDashboardBo> findMemberQueryNotAccepted(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> unClaimed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;

		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryNotAccepted(userId, startIndex, maxResult);
		Set<QueryFiles> queryFiles;
		Set<QueryFilesBo> queryFilesBo;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		QueryFilesBo queryFileBo;
		Iterator<QueryFiles> iteratorQueryFiles;
		
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			Gson gson = new Gson();
			String catJson = gson.toJson(subCategory);
			memberDashboard.setSubCatJson(catJson);
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setHashcode(query.getHashcode());
			if (query.getQueryDeadlineDate() != null)
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy HH:mm:ss"));
			Map<String, String> selecedFixers = new HashMap<String, String>();
			selecedFixers = queryFixersService.getQueryFixersIdsAndNamesBasedOnQueryIdNoBrackets(query.getQueryId(), -1,
					-1);
			if ("true".equals(selecedFixers.get("selectedFixers"))) {
				memberDashboard.setSelectedTrue(true);

			} else {
				memberDashboard.setSelectedTrue(false);
			}
			memberDashboard.setFixersIds(selecedFixers.get("fixersId"));
			memberDashboard.setFixersNames(selecedFixers.get("fixersNames"));
			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo.setFileUniqueCode(queryFile.getFileUniqueCode());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
				queryFilesBo.add(queryFileBo);
			}
			if (queryFilesBo.size() > 0) {
				memberDashboard.setAttachedDocumentsCount(1);
			} else {
				memberDashboard.setAttachedDocumentsCount(0);
			}
			memberDashboard.setAttachedDocuments(queryFilesBo);

			if (query.getQueryAppliedFixers() != null)
				memberDashboard.setInterestedFixers(query.getQueryAppliedFixers());
			if (query.getQueryAppliedFixers() == null)
				memberDashboard.setInterestedFixersCount(0);
			else {
				long appliedFixerCount = queryAppliedFixersService
						.countAppliedFixerByQueryIdAndStatus(query.getQueryId(), "A");
				memberDashboard.setInterestedFixersCount(appliedFixerCount);
			}
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
			unClaimed.add(memberDashboard);
		}
		return unClaimed;
	}

	public Set<MemberDashboardBo> findMemberQueryWorking(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> inProgress = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		long days, min, hrs, sec;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;

		Iterator<QueryFiles> iteratorQueryFiles;
		Set<Query> queries = queryDao.findQueryWorking(userId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			if (query.getQueryDeadlineDate() != null){
				
				String deadLineDate = TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
						query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy 'at' hha z");
				deadLineDate = deadLineDate.replace("AM", "am").replace("PM","pm");
				memberDashboard
				.setQueryDeadlineDate(deadLineDate);
	
				
				

			}
			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), query.getFixerId(), "F");
			memberDashboard.setUnreadMessageCount(unreadCount);

			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
						if(min == 1 ){
							memberDashboard.setTimeDiff(min + " minute");

						}else{
							memberDashboard.setTimeDiff(min + " minutes");

						}
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			inProgress.add(memberDashboard);
		}
		return inProgress;
	}

	public Set<MemberDashboardBo> findMemberQueryPendingResolution(Integer userId, Calendar currentTime,
			String timeZone, int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> closed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		Iterator<QueryFiles> iteratorQueryFiles;
		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryPendingResolution(userId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setMemberId(query.getUser().getUserId());
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			if (query.getQueryDeadlineDate() != null)
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy hh a z"));
			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
				queryFilesBo.add(queryFileBo);
			}
			if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Inactive");
			} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Not Fixed");
			} else if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Closed");
			} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Review");
			} else {
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
				if(days == 1){
					memberDashboard.setTimeDiff(days + " day");

				}else{
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
						if(min==1){
							memberDashboard.setTimeDiff(min + " minute");

						}else{
							
							memberDashboard.setTimeDiff(min + " minutes");

						}

					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			closed.add(memberDashboard);
		}
		return closed;
	}

	public Set<MemberDashboardBo> findMemberQueryClosed(Integer userId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> closed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		Iterator<QueryFiles> iteratorQueryFiles;
		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryClosed(userId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}

			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			memberDashboard.setStarRating(query.getFixerRating().getStarRating());
			memberDashboard.setQueryRating(query.getFixerRating().getStarRating());
			memberDashboard.setQueryStartDate(TimeDiffUtility
					.timeToSpecificTimezoneWithChangeFormat(query.getDateAccepted(), timeZone, "MM/dd/yy"));
			memberDashboard.setQueryEndDate(TimeDiffUtility
					.timeToSpecificTimezoneWithChangeFormat(query.getClosureDate(), timeZone, "MM/dd/yy"));
			if (query.getQueryDeadlineDate() != null)
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy hh a z"));

			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), query.getFixerId(), "F");
			memberDashboard.setUnreadMessageCount(unreadCount);

			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
				queryFilesBo.add(queryFileBo);
			}

			String difference = "";
			try {
				days = TimeDiffUtility.findDayDiff(query.getDateAccepted(), query.getClosureDate());
				hrs = TimeDiffUtility.findHrsDiff(query.getDateAccepted(), query.getClosureDate());
				min = TimeDiffUtility.findMinutesDiff(query.getDateAccepted(), query.getClosureDate());
				sec = TimeDiffUtility.findSecondsDiff(query.getDateAccepted(), query.getClosureDate());

				if (days != 0) {
					if (days == 1) {
						difference = days + " Day";
					} else {
						difference = days + " Days";
					}
				} else {
					if (hrs != 0) {

						if (hrs == 1) {
							difference = hrs + " Hour";
						} else {
							difference = hrs + " Hours";
						}
					} else {
						if (min != 0) {

							if (min == 1) {
								difference = min + " Minute";
							} else {
								difference = min + " Minutes";
							}
						} else {
							difference = sec + " Seconds";

						}
					}
				}
			} catch (Exception e) {

			}

			if (difference == "") {
				memberDashboard.setDateTimeDiffernce("Default Time");
			} else {
				memberDashboard.setDateTimeDiffernce(difference);
			}

			if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Inactive");
			} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Not Fixed");
			} else if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Closed");
			} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Review");
			} else {
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
						memberDashboard.setTimeDiff(min + " minutes");
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			closed.add(memberDashboard);
		}
		return closed;
	}

	public Set<MemberDashboardBo> findFixerQueryNotAccepted(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> unClaimed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		Iterator<QueryFiles> iteratorQueryFiles;
		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryNotAcceptedFixerNative(fixerId, startIndex, maxResult);
		Set<Integer> declineIds = userDeclineService.findDeclineQueryByUserId(fixerId);
		for (Query query : queries) {
			if (declineIds.contains(query.getQueryId())) {
				continue;
			}
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setMemberId(query.getUser().getUserId());
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			memberDashboard.setCurrentStatus(query.getCurrentStatus());

			int queryValueDollar = query.getQueryCredits() * DbConstants.PAID_AMOUNT_PER_ISSUE;
			memberDashboard.setQueryValue("$" + String.valueOf(queryValueDollar));
			UserBo member = findMemberBoById(query.getUser().getUserId());
			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), fixerId, "C");
			memberDashboard.setUnreadMessageCount(unreadCount);
			memberDashboard.setMember(member);
			if (query.getQueryDeadlineDate() != null) {
				

				int deadLineHours = query.getQueryCredits() * 12;

				memberDashboard.setQueryDeadlineDate("" + deadLineHours);

			}

			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
				if(days == 1 ){
					memberDashboard.setTimeDiff(days + " day");

				}else{
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
						memberDashboard.setTimeDiff(min + " minutes");
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			unClaimed.add(memberDashboard);
		}
		return unClaimed;
	}

	public Set<MemberDashboardBo> findFixerQueryWorking(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> inProgress = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Iterator<QueryFiles> iteratorQueryFiles;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryWorkingFixer(fixerId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setMemberId(query.getUser().getUserId());
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());

			int queryValueDollar = query.getQueryCredits() * DbConstants.PAID_AMOUNT_PER_ISSUE;
			memberDashboard.setQueryValue("$" + String.valueOf(queryValueDollar));

			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			if (query.getQueryDeadlineDate() != null) {
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy hh a z"));

				
			}
			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), query.getFixerId(), "C");
			memberDashboard.setUnreadMessageCount(unreadCount);
			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
				if(days == 1){
					memberDashboard.setTimeDiff(days + " day");

				}else{
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
						if(min == 1){
							memberDashboard.setTimeDiff(min + " minute");

						}else{
							memberDashboard.setTimeDiff(min + " minutes");

						}
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			inProgress.add(memberDashboard);
		}
		return inProgress;
	}

	public Set<MemberDashboardBo> findFixerQueryPendingResolution(Integer fixerId, Calendar currentTime,
			String timeZone, int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> closed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Iterator<QueryFiles> iteratorQueryFiles;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		long days, min, hrs, sec;
		Set<Query> queries = queryDao.findQueryPendingResolutionFixer(fixerId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setHashcode(query.getHashcode());
			if (query.getQueryDeadlineDate() != null)
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy hh a z"));
			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), fixerId, "C");
			memberDashboard.setUnreadMessageCount(unreadCount);
			if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Inactive");
			} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("NotFixed");
			} else if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Closed");
			} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Conflict");
			} else {
			}

			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
				if(days == 1){
					memberDashboard.setTimeDiff(days + " day");

				}else{
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
						if(min == 1){
							memberDashboard.setTimeDiff(min + " minute");

						}else{
							memberDashboard.setTimeDiff(min + " minutes");

						}
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			closed.add(memberDashboard);
		}
		return closed;
	}

	public Set<MemberDashboardBo> findFixerQueryClosed(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		try {
			Set<MemberDashboardBo> closed = new LinkedHashSet<MemberDashboardBo>();
			MemberDashboardBo memberDashboard;
			Set<QueryFiles> queryFiles;
			Set<QueryCategory> queryCategories;
			Set<String> subCategory = null;
			QueryFiles queryFile;
			Iterator<QueryFiles> iteratorQueryFiles;
			Set<QueryFilesBo> queryFilesBo;
			QueryFilesBo queryFileBo;
			long days, min, hrs, sec;
			Set<Query> queries = queryDao.findQueryClosedFixer(fixerId, startIndex, maxResult);
			for (Query query : queries) {
				memberDashboard = new MemberDashboardBo();
				subCategory = new HashSet<String>();
				queryFilesBo = new LinkedHashSet<QueryFilesBo>();
				queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
				for (QueryCategory queryCategory : queryCategories) {
					subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
							.getCatgName());
				}
				memberDashboard.setMemberId(query.getUser().getUserId());
				memberDashboard.setSubCategory(subCategory);
				memberDashboard.setQueryId(query.getQueryId());
				memberDashboard.setQueryContent(query.getQueryContent());
				memberDashboard.setQueryTitle(query.getQueryTitle());
				memberDashboard.setQueryCredits(query.getQueryCredits());

				int queryValueDollar = query.getQueryCredits() * DbConstants.PAID_AMOUNT_PER_ISSUE;
				memberDashboard.setQueryValue("$" + String.valueOf(queryValueDollar));

				memberDashboard.setHashcode(query.getHashcode());
				memberDashboard.setFixerId(query.getFixerId());
				if (query.getQueryDeadlineDate() != null) {
					memberDashboard
							.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
									query.getQueryDeadlineDate(), timeZone, "MM/dd/yyyy hh a z"));
					
				}
				long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), query.getFixerId(), "C");

				memberDashboard.setUnreadMessageCount(unreadCount);
				memberDashboard.setQueryRating(query.getFixerRating().getStarRating());
				String difference = "";
				try {
					days = TimeDiffUtility.findDayDiff(query.getDateAccepted(), query.getClosureDate());
					hrs = TimeDiffUtility.findHrsDiff(query.getDateAccepted(), query.getClosureDate());
					min = TimeDiffUtility.findMinutesDiff(query.getDateAccepted(), query.getClosureDate());
					sec = TimeDiffUtility.findSecondsDiff(query.getDateAccepted(), query.getClosureDate());

					if (days != 0) {
						if (days == 1) {
							difference = days + " Day";
						} else {
							difference = days + " Days";
						}
					} else {
						if (hrs != 0) {

							if (hrs == 1) {
								difference = hrs + " Hour";
							} else {
								difference = hrs + " Hours";
							}
						} else {
							if (min != 0) {

								if (min == 1) {
									difference = min + " Minute";
								} else {
									difference = min + " Minutes";
								}
							} else {
								difference = sec + " Seconds";

							}
						}
					}
				} catch (Exception e) {

				}

				if (difference == "") {
					memberDashboard.setDateTimeDiffernce("Default Time");
				} else {
					memberDashboard.setDateTimeDiffernce(difference);
				}
				if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
					memberDashboard.setCurrentStatus("Inactive");
				} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
					memberDashboard.setCurrentStatus("NotFixed");
				} else if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
					memberDashboard.setCurrentStatus("Closed");
				} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
					memberDashboard.setCurrentStatus("Conflict");
				} else {
				}

				queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
				iteratorQueryFiles = queryFiles.iterator();
				while (iteratorQueryFiles.hasNext()) {
					queryFile = iteratorQueryFiles.next();
					queryFileBo = new QueryFilesBo();
					queryFileBo.setFileName(queryFile.getFileName());
					queryFileBo.setFileUrl(queryFile.getFileUrl());
					queryFileBo.setDocUploadDate(
							TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
					if(days == 1){
						memberDashboard.setTimeDiff(days + " day");

						
					}else{
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
							if(min==1){
								memberDashboard.setTimeDiff(min + " minute");

							}else{
								memberDashboard.setTimeDiff(min + " minutes");

							}
						} else {
							memberDashboard.setTimeDiff(sec + " seconds");
						}
					}
				}
				closed.add(memberDashboard);
			}
			return closed;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public Set<User> findFixerWhoMatchQueryCat(Integer queryId) throws FixitException {
		try {
			return userDao.findFixerWhoMatchQueryCat(queryId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}
	
	@Override
	@Transactional
	public Set<User> findNotIntrestedAndRemovedFixer(Integer queryId) throws FixitException{
		try {
			return userDao.findNotIntrestedAndRemovedFixer(queryId);
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	public List<User> checkExistingEmailForSettingsUpdate(String email, String oldEmail) throws FixitException {
		List<User> userExist;
		userExist = userDao.findUserByEmailForUpdateSettings(email, oldEmail);
		return userExist;
	}

	@Override
	public List<User> checkExistingUserNameForSettingsUpdate(String userName, String oldUserName)
			throws FixitException {
		List<User> userExist;
		userExist = userDao.findUserByUserNameForUpdateSettings(userName, oldUserName);
		return userExist;
	}

	@Override
	@Transactional
	public Set<UserGroupsList> getAllMemberAndActivatedFixers(int startIndex, int maxResult) throws FixitException {
		Set<User> users = new LinkedHashSet<User>();
		Set<UserGroupsList> userGroupList = new LinkedHashSet<UserGroupsList>();

		try {
			users = userDao.getAllMemberAndActivatedFixers(startIndex, maxResult);
			Iterator<User> iterator = users.iterator();
			while (iterator.hasNext()) {
				User user = iterator.next();
				UserGroupsList userGroups = new UserGroupsList();
				userGroups.setUserId(user.getUserId());
				userGroups.setUserName(user.getUserName());
				userGroupList.add(userGroups);
			}

			return userGroupList;
		} catch (Exception e) {
			throw new FixitException();
		}
	}

	@Override
	@Async
	public Set<FixerSearchBo> searchListOfUsers(Integer userId, String searchFieldText, int startIndex, int maxResult)
			throws FixitException {
		Set<User> users = new HashSet<>();
		User myUser = null;
		Set<FixerSearchBo> fixers = new HashSet<>();
		try {
			myUser = findUserById(userId);
			users = userDao.searchListOfUsersBasedOnSearchField(searchFieldText, startIndex, maxResult);
			for (User u : users) {
				FixerSearchBo fixerSearchBo = new FixerSearchBo();
				fixerSearchBo.setUserName(u.getUserName());
				fixerSearchBo.setFirstName(u.getFirstName());
				fixerSearchBo.setLastName(u.getLastName());
				fixerSearchBo.setEmail(u.getEmail());
				fixerSearchBo.setCompanyName(u.getCompanyName());
				fixerSearchBo.setLinkedinProfile(u.getLinkedinProfile());

				List<String> userCats = new ArrayList<String>();
				for (FavouriteFixer f : myUser.getFavouriteFixer()) {
					if (u.getUserId().equals(f.getFixerId())) {
						fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_FAVOURITE);
					}
				}

				for (UserCategory userCat : u.getFixerCategory()) {
					userCats.add(userCat.getCategoryType().getCatgName());
				}
				double startPoints = fixerRatingService.getAggregateRatingsOfFixer(u.getUserId(), u.getRating());
				if (startPoints >= 0) {
					fixerSearchBo.setFixerRating(startPoints);
				} else {
					fixerSearchBo.setFixerRating(0);
				}
				fixerSearchBo.setCategoryList(userCats);
				fixerSearchBo.setProfilePhoto(u.getProfilePhoto());
				fixerSearchBo.setUserId(u.getUserId());
				fixerSearchBo.setOverview(u.getOverview());
				fixers.add(fixerSearchBo);
			}
			return fixers;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Async
	public Set<FixerSearchBo> searchListOfUserswithCategory(Integer userId, List<Integer> categories,
			String searchFieldText, String selectedRate, String country, int startIndex, int maxResult)
			throws FixitException {
		Set<User> users = new HashSet<>();
		User myUser = null;
		if (selectedRate.equals(""))
			selectedRate = "0";
		Set<FixerSearchBo> fixers = new HashSet<>();
		try {
			myUser = findUserById(userId);
			users = userDao.searchListOfUsersBasedOnSearchFieldCategoryAndCountry(categories, searchFieldText,
					selectedRate, country, startIndex, maxResult);
			for (User u : users) {
				FixerSearchBo fixerSearchBo = new FixerSearchBo();
				fixerSearchBo.setUserName(u.getUserName());
				fixerSearchBo.setFirstName(u.getFirstName());
				fixerSearchBo.setLastName(u.getLastName());
				fixerSearchBo.setEmail(u.getEmail());
				fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_UNFAVOURITE);
				List<String> userCats = new ArrayList<String>();
				for (FavouriteFixer f : myUser.getFavouriteFixer()) {
					if (u.getUserId().equals(f.getFixerId())) {
						fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_FAVOURITE);
					}
				}

				for (UserCategory userCat : u.getFixerCategory()) {
					userCats.add(userCat.getCategoryType().getCatgName());
				}
				double startPoints = fixerRatingService.getAggregateRatingsOfFixer(u.getUserId(), u.getRating());
				if (startPoints >= 0) {
					fixerSearchBo.setFixerRating(startPoints);
				} else {
					fixerSearchBo.setFixerRating(0);
				}
				fixerSearchBo.setCategoryList(userCats);
				fixerSearchBo.setProfilePhoto(u.getProfilePhoto());
				fixerSearchBo.setUserId(u.getUserId());
				fixerSearchBo.setOverview(u.getOverview());

				fixerSearchBo.setCompanyName(u.getCompanyName());
				fixerSearchBo.setCountry(u.getCountry());
				fixerSearchBo.setLinkedinProfile(u.getLinkedinProfile());

				Calendar calendar = u.getCreatedAt();
				String userSince = TimeDiffUtility.getUserFrom(calendar);

				
				fixerSearchBo.setCity(u.getCity());
				fixerSearchBo.setUserId(u.getUserId());

				fixerSearchBo.setPaypalId(u.getPaypalId());

				fixerSearchBo.setFixersSince(userSince);

				if (fixerSearchBo.getFixerRating() >= Integer.parseInt(selectedRate))
					fixers.add(fixerSearchBo);

			}
			return fixers;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Set<FixerSearchBo> searchListOfUserWithRatingAndCountry(int userId, String searchFieldText,
			String selectedRate, String country, int startIndex, int maxResult) throws FixitException {
		Set<User> users = new HashSet<>();
		User myUser = null;
		Set<FixerSearchBo> fixers = new HashSet<>();
		if (selectedRate.equals(""))
			selectedRate = "0";
		try {
			myUser = findUserById(userId);
			users = userDao.searchListOfUsersBasedOnSearchFieldAndRating(searchFieldText, selectedRate, country,
					startIndex, maxResult);
			for (User u : users) {
				FixerSearchBo fixerSearchBo = new FixerSearchBo();
				fixerSearchBo.setUserName(u.getUserName());
				fixerSearchBo.setFirstName(u.getFirstName());
				fixerSearchBo.setLastName(u.getLastName());
				fixerSearchBo.setEmail(u.getEmail());
				List<String> userCats = new ArrayList<String>();
				fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_UNFAVOURITE);
				for (FavouriteFixer f : myUser.getFavouriteFixer()) {
					if (u.getUserId().equals(f.getFixerId())) {
						fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_FAVOURITE);
					}
				}

				for (UserCategory userCat : u.getFixerCategory()) {
					userCats.add(userCat.getCategoryType().getCatgName());
				}
				double startPoints = fixerRatingService.getAggregateRatingsOfFixer(u.getUserId(), u.getRating());
				if (startPoints >= 0) {
					fixerSearchBo.setFixerRating(startPoints);
				} else {
					fixerSearchBo.setFixerRating(0);
				}
				fixerSearchBo.setCategoryList(userCats);
				fixerSearchBo.setProfilePhoto(u.getProfilePhoto());
				fixerSearchBo.setUserId(u.getUserId());
				fixerSearchBo.setOverview(u.getOverview());

				fixerSearchBo.setCompanyName(u.getCompanyName());
				fixerSearchBo.setCountry(u.getCountry());
				fixerSearchBo.setLinkedinProfile(u.getLinkedinProfile());

				Calendar calendar = u.getCreatedAt();
				String userSince = TimeDiffUtility.getUserFrom(calendar);

				
				fixerSearchBo.setCity(u.getCity());
				fixerSearchBo.setUserId(u.getUserId());

				fixerSearchBo.setPaypalId(u.getPaypalId());

				fixerSearchBo.setFixersSince(userSince);

				if (fixerSearchBo.getFixerRating() >= Integer.parseInt(selectedRate))
					fixers.add(fixerSearchBo);

			}
			return fixers;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Set<MemberDashboardBo> findFixerQueryApplied(Integer fixerId, Calendar currentTime, String timeZone,
			int startIndex, int maxResult) throws Exception {
		Set<MemberDashboardBo> closed = new LinkedHashSet<MemberDashboardBo>();
		MemberDashboardBo memberDashboard;
		Set<QueryFiles> queryFiles;
		Set<QueryCategory> queryCategories;
		Set<String> subCategory = null;
		QueryFiles queryFile;
		Iterator<QueryFiles> iteratorQueryFiles;
		Set<QueryFilesBo> queryFilesBo;
		QueryFilesBo queryFileBo;
		long days, min, hrs, sec;
		Set<Query> queries = queryFixerDao.findQuerApplied(fixerId, startIndex, maxResult);
		for (Query query : queries) {
			memberDashboard = new MemberDashboardBo();
			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			if (query.getQueryDeadlineDate() != null) {
				
				int deadLineHours = query.getQueryCredits() * 12;
				memberDashboard.setQueryDeadlineDate("" + deadLineHours);
			}
			memberDashboard.setMemberId(query.getUser().getUserId());
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			int queryValueDollar = query.getQueryCredits() * DbConstants.PAID_AMOUNT_PER_ISSUE;
			memberDashboard.setQueryValue("$" + String.valueOf(queryValueDollar));
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setFixerId(query.getFixerId());
			UserBo member = findMemberBoById(query.getUser().getUserId());
			memberDashboard.setMember(member);
			long unreadCount = queryAuditDao.getUnreadMessageCount(query.getQueryId(), fixerId, "C");
			memberDashboard.setUnreadMessageCount(unreadCount);
			if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Inactive");
			} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("NotFixed");
			} else if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Closed");
			} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
				memberDashboard.setCurrentStatus("Conflict");
			} else {
			}

			queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
			iteratorQueryFiles = queryFiles.iterator();
			while (iteratorQueryFiles.hasNext()) {
				queryFile = iteratorQueryFiles.next();
				queryFileBo = new QueryFilesBo();
				queryFileBo.setFileName(queryFile.getFileName());
				queryFileBo.setFileUrl(queryFile.getFileUrl());
				queryFileBo
						.setDocUploadDate(TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), timeZone));
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
				if(days == 1){
					
					memberDashboard.setTimeDiff(days + " day");

				}else{
					
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
						if(min == 1){
							memberDashboard.setTimeDiff(min + " minute");

						}else{
							memberDashboard.setTimeDiff(min + " minutes");

						}
					} else {
						memberDashboard.setTimeDiff(sec + " seconds");
					}
				}
			}
			closed.add(memberDashboard);
		}
		return closed;
	}

	@Override
	@Transactional
	public List<FixerSearchBo> findFixerByFilter(String searchText, Integer userId, List<Integer> categories,
			String country, Integer offSet, Integer limit, Integer editQueryId) throws FixitException {
		try {
			List<FixerSearchBo> fixerSearchBos = new ArrayList<FixerSearchBo>();
			FixerSearchBo fixerSearchBo;
			User u;
			User myUser = findUserById(userId);
			List<Object> objects = userDao.findFixerByFilter(searchText, categories, country, offSet, limit, editQueryId);
			for (Object object : objects) {
				Object[] cols = (Object[]) object;
				u = findUserById((Integer) cols[0]);
				fixerSearchBo = new FixerSearchBo();
				fixerSearchBo.setUserName(u.getUserName());
				fixerSearchBo.setFirstName(u.getFirstName());
				fixerSearchBo.setLastName(u.getLastName());
				fixerSearchBo.setEmail(u.getEmail());
				long closedCount = queryDao.findFixerClosedStasCount(u.getUserId());
				fixerSearchBo.setFixedCounts(closedCount);

				Calendar currentTime = Calendar.getInstance();
				String lastLogin = TimeDiffUtility.findDiffString(
						TimeDiffUtility.findDayDiff(u.getLastLogin(), currentTime),
						TimeDiffUtility.findHrsDiff(u.getLastLogin(), currentTime),
						TimeDiffUtility.findMinutesDiff(u.getLastLogin(), currentTime),
						TimeDiffUtility.findSecondsDiff(u.getLastLogin(), currentTime));
				fixerSearchBo.setLastLogin(lastLogin + " ago");

				long deadlinecounts = queryDao.findFixerClosedRequestsCountInDeadline(u.getUserId());
				fixerSearchBo.setFixedUnderdeadline(deadlinecounts);

				List<String> userCats = new ArrayList<String>();
				fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_UNFAVOURITE);
				for (FavouriteFixer f : myUser.getFavouriteFixer()) {
					if (u.getUserId().equals(f.getFixerId())) {
						fixerSearchBo.setFavUnFavstatus(DbConstants.FIXER_STATUS_FAVOURITE);
					}
				}

				for (UserCategory userCat : u.getFixerCategory()) {
					userCats.add(userCat.getCategoryType().getCatgName());
				}
				int fixerRatingByAdmin = u.getRating();
				double startPoints = fixerRatingService.getAggregateRatingsOfFixer(u.getUserId(),fixerRatingByAdmin);
				
				
				
				if (startPoints >= 0) {
					fixerSearchBo.setFixerRating(startPoints);
				} else {
					fixerSearchBo.setFixerRating(0);
				}
				fixerSearchBo.setCategoryList(userCats);
				fixerSearchBo.setProfilePhoto(u.getProfilePhoto());
				fixerSearchBo.setUserId(u.getUserId());
				fixerSearchBo.setOverview(u.getOverview());

				fixerSearchBo.setCompanyName(u.getCompanyName());
				fixerSearchBo.setCountry(u.getCountry());
				fixerSearchBo.setLinkedinProfile(u.getLinkedinProfile());
				fixerSearchBo.setTimeZone(u.getTimeZone());

				Calendar calendar = u.getCreatedAt();
				String userSince = TimeDiffUtility.getUserFrom(calendar);

				
				fixerSearchBo.setCity(u.getCity());
				fixerSearchBo.setUserId(u.getUserId());

				fixerSearchBo.setPaypalId(u.getPaypalId());

				fixerSearchBo.setFixersSince(userSince);
				fixerSearchBos.add(fixerSearchBo);
			}
			return fixerSearchBos;
		} catch (Exception e) {
			throw new FixitException("", "");
		}
	}

	@Override
	public Set<User> findUserBySource(String source, int startIndex, int maxResult) {
		Set<User> user = userDao.findUserBySource(source, startIndex, maxResult);
		return user;
	}

	@Override
	public Set<User> findUserBySourceAndName(String source, String searchText, int startIndex, int maxResult) {
		Set<User> user = userDao.findUserBySourceAndName(source, searchText, startIndex, maxResult);
		return user;
	}

	@Override
	public long findCountUserBySource(String source) throws DataAccessException {
		long count = userDao.findCountUserBySource(source);
		return count;
	}

	@Override
	public long findCountUserBySourceAndName(String source, String searchText) throws DataAccessException {
		long count = userDao.findCountUserBySourceAndName(source, searchText);
		return count;
	}

	@Transactional
	@Override
	public List<AppliedFixersListBo> setAppliedFixerList(Set<User> selectedFixer, Integer userId) {
	
		List<User> selectedFixerSet = new LinkedList<User>(selectedFixer);
		List<AppliedFixersListBo> listfixers = new ArrayList<AppliedFixersListBo>();
		try {
			for (int i = 0; i < selectedFixerSet.size(); i++) {
				AppliedFixersListBo fixer = new AppliedFixersListBo();
				UserBo userFixer = new UserBo();
				userFixer.setFirstName(selectedFixerSet.get(i).getFirstName());
				userFixer.setLastName(selectedFixerSet.get(i).getLastName());
				userFixer.setCompanyName(selectedFixerSet.get(i).getCompanyName());
				userFixer.setCountry(selectedFixerSet.get(i).getCountry());
				userFixer.setLinkedinProfile(selectedFixerSet.get(i).getLinkedinProfile());
				userFixer.setOverview(selectedFixerSet.get(i).getOverview());
				int fixerId = selectedFixerSet.get(i).getUserId();

				FavouriteFixer favouriteFixer = favouriteFixerService.findFavFixerByIds(userId, fixerId);
				if (favouriteFixer != null) {
					userFixer.setFavouriteFixerStatus(DbConstants.FIXER_STATUS_FAVOURITE);
				} else {
					userFixer.setFavouriteFixerStatus(DbConstants.FIXER_STATUS_UNFAVOURITE);
				}

				Calendar calendar = selectedFixerSet.get(i).getCreatedAt();
				String userSince = TimeDiffUtility.getUserFrom(calendar);
				List<String> userCats = new ArrayList<String>();
				Iterator<UserCategory> it = selectedFixerSet.get(i).getFixerCategory().iterator();
				while (it.hasNext()) {
					UserCategory cat = (UserCategory) it.next();
					userCats.add(cat.getCategoryType().getCatgName());
				}
				userFixer.setCategoryList(userCats);
				
				userFixer.setCity(selectedFixerSet.get(i).getCity());
				userFixer.setUserId(selectedFixerSet.get(i).getUserId());
				userFixer.setProfilePhoto(selectedFixerSet.get(i).getProfilePhoto());
				userFixer.setUserId(selectedFixerSet.get(i).getUserId());
				userFixer.setFixersSince(userSince);
				long closedCount = queryService.findFixerClosedStasCount(selectedFixerSet.get(i).getUserId());
				userFixer.setFixedCounts(closedCount);
				userFixer.setTimeZone(selectedFixerSet.get(i).getTimeZone());
				Calendar lastLoginCAl = selectedFixerSet.get(i).getLastLogin();
				Calendar currentTime = Calendar.getInstance();
				String lastLogin = TimeDiffUtility.findDiffString(
						TimeDiffUtility.findDayDiff(lastLoginCAl, currentTime),
						TimeDiffUtility.findHrsDiff(lastLoginCAl, currentTime),
						TimeDiffUtility.findMinutesDiff(lastLoginCAl, currentTime),
						TimeDiffUtility.findSecondsDiff(lastLoginCAl, currentTime));
				userFixer.setLastLogin(lastLogin + " ago");
				double resolvedInDeadline = queryService
						.findPercentageQueriesFixedWithInDeadline(selectedFixerSet.get(i).getUserId());
				userFixer.setFixedUnderdeadline(resolvedInDeadline);

				double rating = fixerRatingService.getAggregateRatingsOfFixer(selectedFixerSet.get(i).getUserId(), selectedFixerSet.get(i).getRating());
				userFixer.setFixerRating(rating);
				fixer.setUserFixer(userFixer);

				listfixers.add(fixer);

			}
			return listfixers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<String> findAllParentCategoryByCatId(List<Integer> catId) {
		try {
			List<String> catName = categoryTypeDao.findAllParentCategoryByCatId(catId);
			return catName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Transactional
	@Override
	public void fixerapproval(Query query, HttpServletRequest request, User fixer) {
		
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		try {
			Set<QueryAppliedFixers> queryAppliedFixersSet = new HashSet<QueryAppliedFixers>();
			QueryAppliedFixers queryAppliedFixers = 	queryAppliedFixersService.saveAppliedFixer(fixer, query);
			queryAppliedFixersSet.add(queryAppliedFixers);
			
			query = queryService.findQueryByQueryId(query.getQueryId());
			Calendar currentTime = Calendar.getInstance();
			query.setFixerId(fixer.getUserId());
			query.setCurrentStatus(DbConstants.STATUS_WORKING);
			query.setDateAccepted(currentTime);
		  
			query = queryService.UpdateQuery(query);
			
			
			
			query.setQueryAppliedFixers(queryAppliedFixersSet);
			emailServcie.emailToFixerMemberApproveRequest(base, query, fixer);
		

		} catch (FixitException e) {
			e.printStackTrace();
		}

	}


}
