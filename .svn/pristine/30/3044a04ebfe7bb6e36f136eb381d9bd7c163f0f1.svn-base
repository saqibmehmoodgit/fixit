package com.fixit.web;

import java.io.InputStream;

import java.io.OutputStream;
import java.math.BigInteger;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixit.dao.QueryAuditDao;
import com.fixit.dao.QueryFilesDao;
import com.fixit.dao.UserDao;
import com.fixit.domain.bo.ChatMessageBo;
import com.fixit.domain.bo.ClosedQueryJson;
import com.fixit.domain.bo.FixerProfile;
import com.fixit.domain.bo.FixerRatingCountBo;
import com.fixit.domain.bo.FixerSetting;
import com.fixit.domain.bo.FrequencyDuration;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.NewPassword;
import com.fixit.domain.bo.PieChartData;
import com.fixit.domain.bo.QueryAuditBo;
import com.fixit.domain.bo.QueryBo;
import com.fixit.domain.bo.QueryData;
import com.fixit.domain.bo.Segment;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.UserCategoryBo;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.QueryFixers;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserCategory;
import com.fixit.domain.vo.UserDecline;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.service.CategoryTypeService;
import com.fixit.service.ChatGroupsService;
import com.fixit.service.ChatMessageService;
import com.fixit.service.ChatUserGroupService;
import com.fixit.service.CountryService;
import com.fixit.service.EmailServcie;
import com.fixit.service.FixerRatingService;
import com.fixit.service.IndustryTypeService;
import com.fixit.service.MessageNotificationService;
import com.fixit.service.QueryAppliedFixersService;
import com.fixit.service.QueryAuditService;
import com.fixit.service.QueryExpireService;
import com.fixit.service.QueryFixersService;
import com.fixit.service.QueryService;
import com.fixit.service.UserCategoryService;
import com.fixit.service.UserDeclineService;
import com.fixit.service.UserIndustryService;
import com.fixit.service.UserService;
import com.fixit.service.UserServiceImpl;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.FixitVariables;
import com.fixit.utility.PasswordEncode;
import com.fixit.utility.TimeDiffUtility;
import com.fixit.utility.TimeZoneList;
import com.fixit.utility.Utility;
import com.fixit.validation.FixerSettingsValidator;
import com.fixit.validation.NewPasswordValidator;

@Controller("FixerController")
public class FixerController {

	@Autowired
	private NewPasswordValidator newPasswordValidator;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ChatGroupsService chatGroupsService;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private FixerRatingService fixerRatingService;

	@Autowired
	private EmailServcie emailServcie;

	@Autowired
	private UserService userService;

	@Autowired
	private IndustryTypeService industryTypeService;

	@Autowired
	private UserIndustryService userIndustryService;
	@Autowired
	private UserCategoryService userCategoryService;

	@Autowired
	private CategoryTypeService categoryTypeService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private QueryExpireService queryExpireService;

	@Autowired
	private QueryAuditService queryAuditService;

	@Autowired
	private UserDeclineService userDeclineService;

	@Autowired
	private QueryFilesDao queryFilesDao;

	@Autowired
	private FixerSettingsValidator fixerSettingsValidator;

	@Autowired
	private QueryAuditDao queryAuditDao;

	@Autowired
	private CountryService countryService;

	@Autowired
	private ChatUserGroupService chatUserGroupService;

	@Autowired
	private QueryFixersService queryFixersService;

	@Autowired
	private QueryAppliedFixersService queryAppliedFixersService;

	@Autowired
	private MessageNotificationService messageNotificationService;

	@RequestMapping(value = "/fixer/fixerSetting", method = RequestMethod.POST)
	public ModelAndView fixerSetting(@ModelAttribute FixerSetting fixerSetting, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBo member = new UserBo();
		User user = (User) session.getAttribute("user");
		List<User> list;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
			int listSize = 0;
			try {
				if (!fixerSetting.getEmail().equals(user.getEmail())) {
					list = userService.checkExistingEmail(fixerSetting.getEmail());
					listSize = list.size();
				}

				if (listSize > 0) {
					mav.setViewName("redirect:/fixer/dashBoard?msgKey=FAE");

				} else {
					String imageUrl = fixerSetting.getImageUrl();
					if (imageUrl != null && !imageUrl.isEmpty()) {

						if (FileUpload.isValidProfileImage(imageUrl)) {
							boolean isInFixerFolder = FileUpload.moveFileFromprofileToFixerFolder(imageUrl, userId);
							if (isInFixerFolder) {
								FileUpload.deleteFileFromProfileImageFolder(imageUrl);
							}
						}

						if (FileUpload.isValidProfileImage(
								FileUpload.getFixerIconFileNameFromImage(imageUrl, fixerSetting.getUserName()))) {
							boolean isInFixerFolder = FileUpload.moveFileFromprofileToFixerFolder(
									FileUpload.getFixerIconFileNameFromImage(imageUrl, fixerSetting.getUserName()),
									userId);
							if (isInFixerFolder) {
								FileUpload.deleteFileFromProfileImageFolder(
										FileUpload.getFixerIconFileNameFromImage(imageUrl, fixerSetting.getUserName()));
							}
						}
					}

					user = userService.updateFixerSettings(fixerSetting, user);

					if (user != null) {
						if (null != fixerSetting.getNewPasword()) {
							if (!(fixerSetting.getNewPasword().trim().isEmpty()
									&& fixerSetting.getConfirmPassword().trim().isEmpty())
									&& (fixerSetting.getConfirmPassword().equals(fixerSetting.getNewPasword()))) {
								user = userService.saveUserNewPassword(user, fixerSetting.getNewPasword());
							}
						}
					}
					session.setAttribute("user", user);
					mav.setViewName("redirect:/fixer/dashBoard?msgKey=STS");
				}
			} catch (Exception e) {
				mav.setViewName("redirect:/fixer/dashBoard?msgKey=STF");

				e.printStackTrace();
			}
		}

		return mav;
	}

	@RequestMapping(value = "/fixer/dashBoard", method = RequestMethod.GET)
	public ModelAndView fixerDashBoardNew(HttpSession session,
			@RequestParam(value = "msgKey", required = false,defaultValue = "R") String msgKey) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		try {
			Calendar calendar = user.getCreatedAt();
			UserBo userBo = new UserBo();
			mav.addObject("userBo", userBo);
			String userSince = TimeDiffUtility.getUserFrom(calendar);
			if (userSince != null) {
				mav.addObject("userSince", userSince);
			}
			if (user != null) {
				userId = user.getUserId();
				int adminUserId = 0;
				Set<User> admin = userService.findAdminByStatus(DbConstants.ADMIN);
				for (User adminId : admin) {
					adminUserId = adminId.getUserId();
					break;
				}

				List<Integer> userIds = new LinkedList<Integer>();
				userIds.add(userId);
				userIds.add(adminUserId);
				Integer groupIdInteger = -1;
				ChatGroups chatGroups = null;

				ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
				if (chatUserGroup != null) {
					groupIdInteger = chatUserGroup.getChatGroups().getChatGroupId();

					mav.addObject("groupId", groupIdInteger);
					mav.addObject("isGroup", "Yes");
					long countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
					mav.addObject("countMsg", countMsg);

				} else {

					chatGroups = chatGroupsService.saveChatGroup(userId);
					chatUserGroupService.saveChatUserGroup(userIds, chatGroups);

					mav.addObject("groupId", chatGroups.getChatGroupId());
					mav.addObject("countMsg", 0);
				}
				
				Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();

				if (groupIdInteger != -1) {
					chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
					if (chatGroups != null) {
						chatMessageSet = chatMessageService
								.getAllChatMessagesBasedOnGroupId(chatGroups.getChatGroupId(), user);

					}
					mav.addObject("groupName", chatGroups.getChatGroupName());
				} else {

					mav.addObject("groupName", "no_group");

				}
				mav.addObject("messagesSet", chatMessageSet);

				long closedCount = queryService.findFixerClosedStasCount(userId);
				BigInteger unClaimedOpenCount = queryService.findQueryNotAcceptedFixerCount(userId);
				long inProgressCount = queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING);
				long appliedCountbyFixer = queryAppliedFixersService.getAppliedQueriesCount(userId);

				mav.addObject("inProgressCount", inProgressCount);
				mav.addObject("openRequestCount", unClaimedOpenCount);
				mav.addObject("fixedIssuesCount", closedCount);
				mav.addObject("appliedCountbyFixer", appliedCountbyFixer);

				FixerSetting fixerSetting = new FixerSetting();

				List<FixerRatingCountBo> count = fixerRatingService.findRatingByFixerIdAndRating(userId);
				mav.addObject("getBArs", count);
				mav.addObject("countryList", countryService.findAllCountry());

				
				mav.addObject("fixerSetting", fixerSetting);
				mav.addObject("userTimeZone", user.getTimeZone());
				mav.addObject("timeZoneList", TimeZoneList.timeZoneList());
				mav.addObject("categoryJSONByUser", objectMapper.writeValueAsString(categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId))));
				mav.addObject("industryByUser", industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
				mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
				mav.addObject("categoryByUser", categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId)));
				mav.addObject("industryJSON",
						objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
				mav.addObject("industry", industryTypeService.findAllIndustryType());
				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE));
				mav.addObject("allCategoryJson", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE)));
				mav.addObject("allCategory",
						(categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE)));
				mav.addObject("parentCategoryJson", objectMapper.writeValueAsString(
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
				mav.addObject("parentCategoryJson", objectMapper.writeValueAsString(
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
				long resolvedQueries = queryAuditService.findAllQueryCountByFixerId(userId, "C");
				if (resolvedQueries >= 0) {
					mav.addObject("resolvedQueries", resolvedQueries);
				}

				double resolvedInDeadline = queryService.findPercentageQueriesFixedWithInDeadline(userId);
				long responseTime = queryService.findAverageResponseTime(userId, "C");
				if (responseTime >= 0) {
					mav.addObject("responseTime", resolvedInDeadline);
				}
				double fixerRating = fixerRatingService.getAggregateRatingsOfFixer(userId, user.getRating());
				if (fixerRating >= 0) {
					mav.addObject("averageFixerRating", fixerRating);
				}

				

				List<PieChartData> chartDatas = queryService.findQueryByParentCatFixer(userId);
				mav.addObject("pieChartData", objectMapper.writeValueAsString(chartDatas));

			}
			String message = "";
			String msgType = "";
			switch (msgKey) {
			case "R":
				message = "";
				break;

			case "N":
				message = "Issue Created Successfully";
				msgType = "success";
				break;

			case "E":
				message = "Issue Updated Successfully";
				msgType = "success";
				break;

			case "D":
				message = "Issue Deleted Successfully";
				msgType = "success";
				break;

			case "C":
				message = "Issue Closed Successfully";
				msgType = "success";
				break;

			case "UN":
				message = "Issue NotFixed Successfully";
				msgType = "success";
				break;

			case "FR":
				message = "Fixer Rejected Successfully";
				msgType = "success";
				break;

			case "PS":
				message = "Your payment is successfull";
				msgType = "success";
				break;

			case "PNS":
				message = "Your payment is not successfull";
				msgType = "Error";
				break;

			case "CS":
				message = "Your don't have sufficient credits to post a request.";
				msgType = "Error";
				break;
			case "AC":
				message = "Issue Already InProgress";
				msgType = "Error";
				break;

			case "EX":
				message = "Email Already Expire";
				msgType = "Error";
				break;
			case "PSS":
				message = "Your profile has been saved successfully!";
				msgType = "success";
				break;
			case "PUF":
				message = "Profile update failed ";
				msgType = "Error";
				break;
			case "STS":
				message = "Your settings have been saved successfully.";
				msgType = "success";
				break;
			case "STF":
				message = "Setting update failed";
				msgType = "Error";
				break;
			case "FAE":
				message = "This Email or UserName has already registered as Fixer";
				msgType = "Error";
				break;

			}

			mav.addObject("message", message);
			mav.addObject("msgType", msgType);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("myUser", user);

		mav.setViewName("dashboard/fixerDashboard.jsp");
		return mav;
	}
	/*
	 * , @RequestParam String name,
	 * 
	 * @RequestParam String companyName, @RequestParam String location,
	 * 
	 * @RequestParam String linkedinProfile, @RequestParam String overviewText
	 */

	@RequestMapping(value = "/fixer/editProfile", method = RequestMethod.POST)
	public ModelAndView createEditFixerProfile(@ModelAttribute UserBo userBo, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		try {
			String firstName = "";
			String lastName = "";
			String city = "";
			String country = "";
			if (userBo.getName() != null || !userBo.getName().isEmpty()) {

				String[] names = userBo.getName().split("\\s+");
				firstName = names[0];
				int length = names.length;
				if (length > 1) {
					for (int i = 1; i < length; i++) {
						lastName += names[i] + " ";
					}
				}

			}
			
			user = (User) session.getAttribute("user");
			if (user != null) {
				int userId = user.getUserId();
				FixerProfile profile = new FixerProfile();
				profile.setFirstName(firstName);
				profile.setLastName(lastName);

				profile.setCompanyName(userBo.getCompanyName());
				profile.setCity(userBo.getCity());
				profile.setCountry(userBo.getCountry());
				profile.setLinkedinProfile(userBo.getLinkedinProfile());
				profile.setOverview(userBo.getOverview());

				profile.setUserId(user.getUserId());
				profile.setEmail(user.getEmail());

				if (userBo.getImageUrl() != null && !userBo.getImageUrl().isEmpty()) {
					profile.setImageUrl(userBo.getImageUrl());

					if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
						boolean isInFixerFolder = FileUpload.moveFileFromprofileToFixerFolder(profile.getImageUrl(),
								userId);
						if (isInFixerFolder) {
							FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
						}
					}

					if (FileUpload.isValidProfileImage(
							FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()))) {
						boolean isInFixerFolder = FileUpload.moveFileFromprofileToFixerFolder(
								FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()),
								userId);
						if (isInFixerFolder) {
							FileUpload.deleteFileFromProfileImageFolder(FileUpload
									.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()));
						}
					}
				}

				User updateUser = userService.updateFixerProfile(profile,
						request.getSession().getServletContext().getRealPath("/"));
				session.setAttribute("user", updateUser);
				mav.setViewName("redirect:/fixer/dashBoard?msgKey=PSS");

			}
		} catch (Exception e) {
			mav.setViewName("redirect:/fixer/dashBoard?msgKey=PUF");

			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/fixer/getFixerStats", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllFixerStats() {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("query", userService.getStats("F", 1));
			return Utility.getSuccessResponse("stats", map);
		} catch (Exception e) {
			return Utility.getFailureResponse("failed", "failed");
		}

	}

	@RequestMapping(value = "/fixer/fixerDashboard", method = RequestMethod.GET)
	public ModelAndView userDashboard() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("fixerDashboard.jsp");
		return mav;
	}

	

	@RequestMapping(value = "/fixer/getFixerQueries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCustomerQuestions(String type, int min, int max) {

		try {
			Set<QueryData> questionList = userService.getFixerQueriesStuff(0, type, min, max);
			
			Map<String, Object> map = new HashMap<>();
			map.put("fixerQuery", questionList);
			return Utility.getSuccessResponse("fixerQueries", map);
		} catch (Exception e) {

			return Utility.getFailureResponse("failed", "failed");
		}

	}

	@RequestMapping(value = "/fixerSignUp/step2", method = RequestMethod.GET)
	public ModelAndView createFixerProfileGet(@ModelAttribute("profile") FixerProfile fixerProfileStep1,
			HttpSession session) {

		User user = null;
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			
			user = (User) session.getAttribute("user");
			int userId = -1;
			List<Integer> userIds = new LinkedList<Integer>();
			if (user != null) {
				userId = user.getUserId();
				userIds.add(userId);
			}

			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			if (user != null) {
				mav.addObject("catsJSONByUser", objectMapper.writeValueAsString(categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId))));

				mav.addObject("industry", industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
				mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
				mav.addObject("category", categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId)));
			}
			mav.addObject("profile", fixerProfileStep1);
			mav.addObject("industry", industryTypeService.findAllIndustryType());
			if (fixerProfileStep1.getUserId() != null) {
				mav.addObject("industJSONByUser",
						objectMapper.writeValueAsString(industryTypeService.findIndustryTypeListFromIndstIds(
								userIndustryService.findUserIndustryByUserId(fixerProfileStep1.getUserId()))));
			}

			mav.addObject("industryJSON", objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
			mav.addObject("parentCategory", categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE));
			mav.addObject("allCategoryJSON", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE)));
			mav.addObject("allCategory", categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE));
			mav.addObject("parentCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
		} catch (FixitException e) {
			System.out.println();
		} catch (Exception e) {
			System.out.println();
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mav.addObject("CreateOrEdit", "Edit Profile");
			mav.addObject("Step", "");
			

		} else {
			mav.addObject("CreateOrEdit", "Create My  Profile");
			mav.addObject("Step", "STEP 2: Create Your Profile To Complete the Registration Process.");
			
		}
		mav.addObject("myUser", user);
		mav.setViewName("profile/fixerProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixerSignUp/step2", method = RequestMethod.POST)
	public ModelAndView createFixerProfilePost(@Valid @ModelAttribute("profile") FixerProfile profile,
			BindingResult result, Model model, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		// check user current session
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */
			mav.setViewName("redirect:/login");

		}

		User user = (User) session.getAttribute("user");
		List<Integer> userIds = new LinkedList<Integer>();
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
			profile.setUserId(userId);
			userIds.add(userId);
		}

		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e2) {
			
			e2.printStackTrace();
		}
		if (chatUserGroup != null) {
			mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			mav.addObject("isGroup", "Yes");
			getNotificationCount(mav, user.getUserId());
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		User existingUser = null;
		List<User> users = userDao.findUserByEmailOrUserName(profile.getEmail(), profile.getUserName());
		if (users.size() > 0) {
			existingUser = users.get(0);
		} else {
			existingUser = userDao.findUserByUserId(userId);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (result.hasErrors()) {

				List<FieldError> fieldErrors = result.getFieldErrors();
				List<String> listErrors = new ArrayList<>();
				for (int i = 0; i < result.getErrorCount(); i++) {
					listErrors.add(fieldErrors.get(i).getField());

				}

				mav.addObject("errorFields", objectMapper.writeValueAsString(listErrors));
				mav.addObject("parentCategories", objectMapper.writeValueAsString(
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
				mav.addObject("profile", profile);
				mav.addObject("user", userService.findUserById(userId));
				mav.addObject("profile", profile);
				mav.addObject("industry", industryTypeService.findAllIndustryType());

				if (profile.getCategories() == null) {
					mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
							.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));

				} else if (profile.getIndustries() == null) {
					mav.addObject("catsJSONByUser", objectMapper.writeValueAsString(categoryTypeService
							.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId))));
				} else {

				}
				
				mav.addObject("industryJSON",
						objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE));
				mav.addObject("allCategory", categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE));
				mav.addObject("allCategoryJSON", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE)));
				mav.addObject("parentCategoryJson", objectMapper.writeValueAsString(
						categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
				if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
					FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
				}

				if (FileUpload.isValidProfileImage(
						FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()))) {
					FileUpload.deleteFileFromProfileImageFolder(
							FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()));
				}

				mav.setViewName("profile/fixerProfile.jsp");
				return mav;
			}

			if (existingUser == null) {
				
				existingUser = userService.saveFixer(profile);
				profile.setUserId(existingUser.getUserId());
				userId = existingUser.getUserId();
				
				if(profile.isSocialLogin()&&profile.getSocialImageUrl()!=null?!profile.getSocialImageUrl().equals(""):false)
				{
					existingUser.setProfileIcon(profile.getImageUrl());
					existingUser = userService.updateFixerProfile(profile);
				}
				else
				{

					existingUser = userService.updateFixerProfile(profile,
							request.getSession().getServletContext().getRealPath("/"));
					try {
						if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
							boolean isInMemberFolder = FileUpload.moveFileFromprofileToFixerFolder(profile.getImageUrl(),
									userId);
							if (isInMemberFolder) {
								FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
							}
						}

						if (FileUpload.isValidProfileImage(
								FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()))) {
							boolean isInMemberFolder = FileUpload.moveFileFromprofileToFixerFolder(
									FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()),
									userId);
							if (isInMemberFolder) {
								FileUpload.deleteFileFromProfileImageFolder(FileUpload
										.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()));
							}
						}

					} catch (FixitException e1) {
						
						e1.printStackTrace();
					}
				}
			} else {
				if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
					boolean isInMemberFolder = FileUpload.moveFileFromprofileToFixerFolder(profile.getImageUrl(),
							userId);
					if (isInMemberFolder) {
						FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
					}
				}

				if (FileUpload.isValidProfileImage(
						FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()))) {
					boolean isInMemberFolder = FileUpload.moveFileFromprofileToFixerFolder(
							FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()),
							userId);
					if (isInMemberFolder) {
						FileUpload.deleteFileFromProfileImageFolder(
								FileUpload.getFixerIconFileNameFromImage(profile.getImageUrl(), profile.getUserName()));
					}
				}

				profile.setUserId(existingUser.getUserId());
				existingUser = userService.updateFixerProfile(profile,
						request.getSession().getServletContext().getRealPath("/"));
			}
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		

		try {
			mav.addObject("user", userService.findUserById(existingUser.getUserId()));
			mav.addObject("industry", industryTypeService.findIndustryTypeListFromIndstIds(
					userIndustryService.findUserIndustryByUserId(existingUser.getUserId())));
			mav.addObject("category", categoryTypeService.findCategoryTypeListFromcatIds(
					userCategoryService.findUserCategoryByUserId(existingUser.getUserId())));
		} catch (FixitException e) {
			
		}

		if (existingUser != null) {

			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			try {
				emailServcie.emailToAdminForFixerApproval(existingUser, base);
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
		}
		mav.setViewName("redirect:/login?newFixer");

		return mav;
	}

	// edit
	@RequestMapping(value = "/fixer/editProfile", method = RequestMethod.GET)
	public ModelAndView createEditFixerProfileGet(@ModelAttribute("profile") FixerProfile fixerProfileStep1,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			
			user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			if (user != null) {
				mav.addObject("catsJSONByUser", objectMapper.writeValueAsString(categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId))));

				mav.addObject("industry", industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
				mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
				mav.addObject("category", categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId)));
			}
			if (fixerProfileStep1 != null && user != null) {
				fixerProfileStep1.setUserId(user.getUserId());
				fixerProfileStep1.setUserName(user.getUserName());
				fixerProfileStep1.setEmail(user.getEmail());
				fixerProfileStep1.setFirstName(user.getFirstName());
				fixerProfileStep1.setOverview(user.getOverview());
				fixerProfileStep1.setTimeZone(user.getTimeZone());
				fixerProfileStep1.setCountry(user.getCountry());
			}
			mav.addObject("profile", fixerProfileStep1);
			mav.addObject("industry", industryTypeService.findAllIndustryType());
			if (fixerProfileStep1.getUserId() != null) {
				mav.addObject("industJSONByUser",
						objectMapper.writeValueAsString(industryTypeService.findIndustryTypeListFromIndstIds(
								userIndustryService.findUserIndustryByUserId(fixerProfileStep1.getUserId()))));
			}

			mav.addObject("industryJSON", objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
			mav.addObject("parentCategory", categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE));
			mav.addObject("allCategory", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITHOUT_NOT_SURE)));
			mav.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITHOUT_NOT_SURE)));
			User myUser = userService.findUserById(userId);
			String fixerImagePath = myUser.getProfilePhoto();
			Calendar currentTime = Calendar.getInstance();
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			mav.addObject("fixerImagePath", fixerImagePath);

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mav.addObject("CreateOrEdit", "Edit Profile");
			mav.addObject("Step", "");
			

		} else {
			mav.addObject("CreateOrEdit", "Create My  Profile");
			mav.addObject("Step", "STEP 2: Create Your Profile To Complete the Registration Process.");
			
		}
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "profile");
		mav.setViewName("profile/editFixerProfile.jsp");
		return mav;
	}

	

	@RequestMapping(value = "/fixer/myprofile", method = RequestMethod.GET)
	public ModelAndView myProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			User myUser = userService.findUserById(userId);
			Calendar currentTime = Calendar.getInstance();
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			String fixerImagePath = myUser.getProfilePhoto();
			mav.addObject("fixerImagePath", fixerImagePath);
			mav.addObject("user", myUser);

			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
			mav.addObject("category", categoryTypeService
					.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId)));
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		if (userId != -1) {
			try {
				double fixerRating = fixerRatingService.getAggregateRatingsOfFixer(userId, user.getRating());
				if (fixerRating >= 0) {
					mav.addObject("fixerRating", fixerRating);
				}
				long totalQuery = queryAuditService.findAllQueryCountByFixerId(userId, "W");
				long responseTime = queryService.findAverageResponseTime(userId, "C");
				long resolvedQueries = queryAuditService.findAllQueryCountByFixerId(userId, "C");
				if (resolvedQueries >= 0) {
					mav.addObject("resolvedQueries", resolvedQueries);
				}
				if (totalQuery >= 0) {
					mav.addObject("totalQuery", totalQuery);
					double resolvedPercentage = (resolvedQueries * 100) / 100;
					mav.addObject("resolvedPercentage", resolvedPercentage);
				}
				if (responseTime >= 0) {
					mav.addObject("responseTime", responseTime);
				}

			} catch (FixitException e) {
			
				e.printStackTrace();
			}
		}
		mav.addObject("User_Type", "fixer");
		mav.addObject("currentPage", "profile");
		mav.addObject("myUser", user);
		mav.setViewName("profile/myFixerProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixer/dashBoard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberDashboardAjax(@RequestParam int startIndex, @RequestParam int maxResult,
			HttpSession session) {
		Set<MemberDashboardBo> unclaimed = null;
		Set<MemberDashboardBo> inProgress = null;
		Set<MemberDashboardBo> closed = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			Calendar currentTime = Calendar.getInstance();
			unclaimed = userService.findFixerQueryNotAccepted(userId, currentTime, user.getTimeZone(), startIndex,
					maxResult);
			inProgress = userService.findFixerQueryWorking(userId, currentTime, user.getTimeZone(), startIndex,
					maxResult);
			closed = userService.findFixerQueryClosed(userId, currentTime, user.getTimeZone(), startIndex, maxResult);

		} catch (FixitException e) {
			return Utility.getFailureResponse();
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse("unClaimedFixerCount", unclaimed, "inProgressFixerCount", inProgress,
				"closedFixerCount", closed);
	}


	@RequestMapping(value = "/fixer/decline", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView fixerDecline(@RequestParam("queryId") Integer queryId, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
				Query query = queryService.findQueryByQueryId(queryId);
				UserDecline userDecline = userDeclineService.findDeclineQueryByAndUserId(userId, queryId);
				if (userDecline != null) {
					modelAndView.setViewName("redirect:/fixer/request?msgKey=R&status=Unclaimed");
					return modelAndView;
				}
				if (query.getCurrentStatus().equals(DbConstants.STATUS_WORKING)) {

					modelAndView.setViewName("redirect:/fixer/request?msgKey=R&status=Unclaimed");
					return modelAndView;
				}
				userDeclineService.saveUserDecline(userId, queryId);
			}
		} catch (Exception e) {
			modelAndView.setViewName("redirect:/fixer/request?msgKey=R&status=Unclaimed");
			return modelAndView;
		}
		modelAndView.setViewName("redirect:/fixer/request?msgKey=D&status=Unclaimed");
		return modelAndView;
	}

	@RequestMapping(value = "/fixer/fixerQueryDetailPage", method = RequestMethod.GET)
	public ModelAndView queryFixerDetailPage(@RequestParam String queryId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer memberId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			messagesSet = queryAuditService.getQueryDetailMessages(queryId, -1, -1);
			String messString;
			String[] messArr;
			for (QueryAudit audit : messagesSet) {
				audit.setAuditTime(TimeDiffUtility.timeToSpecificTimezone(audit.getAuditDate(), user.getTimeZone()));
				if (DbConstants.STATUS_WORKING_DOCUMENT.equals(audit.getStatus())) {
					messString = audit.getMessage();
					messArr = messString.split("/");
					audit.setDocumentFilename(messArr[messArr.length - 1]);
				}
			}
		} catch (FixitException e) {
			e.printStackTrace();
		}
		if (messagesSet.size() > 0) {
			Iterator<QueryAudit> iterator = messagesSet.iterator();
			if (iterator.hasNext()) {
				QueryAudit queryAudit = iterator.next();
				memberId = queryAudit.getCustomerId();
				mav.addObject("queryTitle", queryAudit.getQuery().getQueryTitle());
				mav.addObject("queryContent", queryAudit.getQuery().getQueryContent());
			}
		}
		User member = userService.findUserById(memberId);
		mav.addObject("memberName", member.getFirstName());
		mav.addObject("queryId", queryId);
		mav.addObject("myUser", user);
		mav.addObject("memberImgIcon", member.getProfileIcon());
		mav.addObject("messagesSet", messagesSet);
		mav.addObject("currentPage", "dashboard");
		mav.setViewName("dashboard/fixerQueryDetailPage.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixer/deleteFileEdit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFileFixerEdit(@RequestParam String fileName, @RequestParam String queryId,
			HttpSession session) {
		try {
			Query query = queryService.getQueryByHashCode(queryId);
			FileUpload.deleteFileFromQueryFolder(fileName, query.getUser().getUserId(), query.getQueryId());
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();

	}

	@ResponseBody
	@RequestMapping(value = "/fixer/sendMessage", method = RequestMethod.POST)
	public Map<String, Object> fixerSendMessage(@RequestParam String queryId, @RequestParam int customerId,
			@RequestParam int fixerId, @RequestParam String msgFrom, @RequestParam String message, HttpSession session,
			HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		QueryAudit queryAudit = null;
		QueryAuditBo queryAudiotBo = new QueryAuditBo();
		queryAudiotBo.setCustomerId(customerId);
		queryAudiotBo.setFixerId(fixerId);
		Query query = null;
		try {
			query = queryService.getQueryByHashCode(queryId);

			if (query != null) {
				queryAudiotBo.setQueryId(query.getQueryId());
			}

			queryAudiotBo.setMsgFrom(msgFrom);
			queryAudiotBo.setStatus("W");
			queryAudiotBo.setMessage(message);

			queryAudit = queryAuditService.saveQueryDetailMessage(queryAudiotBo);
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			emailServcie.emailToMemberChatMessage(queryAudit, user, base);

			List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
			messagesSet = queryAuditService.getQueryOpenDetailMessages(queryId, fixerId, -1, -1);
			String messString;
			String[] messArr;
			for (QueryAudit audit : messagesSet) {
				audit.setAuditTime(TimeDiffUtility.timeToSpecificTimezoneForChat(audit.getAuditDate(), user.getTimeZone()));
				if (DbConstants.STATUS_WORKING_DOCUMENT.equals(audit.getStatus())) {
					messString = audit.getMessage();
					messArr = messString.split("/");
					audit.setDocumentFilename(messArr[messArr.length - 1]);
				}
			}
			return Utility.getSuccessResponse("result", "success", "messagesSet", messagesSet);

		} catch (Exception e) {
			e.printStackTrace();
			return Utility.getSuccessResponse("response", "failed");
		}
	}

	@RequestMapping(value = "/fixer/closedQueryPage", method = RequestMethod.GET)
	public ModelAndView closedFixerQueryDetailPage(@RequestParam String queryId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer memberId = -1;
		Query query = null;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(queryId);
			messagesSet = queryAuditService.getQueryDetailMessages(queryId, -1, -1);
			String messString;
			String[] messArr;

			for (QueryAudit audit : messagesSet) {
				audit.setAuditTime(TimeDiffUtility.timeToSpecificTimezone(audit.getAuditDate(), user.getTimeZone()));
				if (DbConstants.STATUS_WORKING_DOCUMENT.equals(audit.getStatus())) {
					messString = audit.getMessage();
					messArr = messString.split("/");
					audit.setDocumentFilename(messArr[messArr.length - 1]);
				}
			}
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		if (messagesSet.size() > 0) {
			Iterator<QueryAudit> iterator = messagesSet.iterator();
			if (iterator.hasNext()) {
				QueryAudit queryAudit = iterator.next();
				memberId = queryAudit.getCustomerId();
				mav.addObject("queryTitle", queryAudit.getQuery().getQueryTitle());
				mav.addObject("queryContent", queryAudit.getQuery().getQueryContent());
			}
		}
		User member = userService.findUserById(memberId);
		QueryAudit reasonFromMember = null;
		try {
			reasonFromMember = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
					query.getFixerId(), query.getQueryId(), "UN");
			if (reasonFromMember != null) {
				mav.addObject("memberReason", reasonFromMember.getMessage());
			}
		} catch (FixitException e1) {
		
			e1.printStackTrace();
		}

		QueryAudit reasonFromFixer;
		try {
			reasonFromFixer = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
					query.getFixerId(), query.getQueryId(), "R");
			if (reasonFromFixer != null) {
				mav.addObject("fixerReason", reasonFromFixer.getMessage());
			}
		} catch (FixitException e1) {
		
			e1.printStackTrace();
		}

		mav.addObject("memberName", member.getFirstName());
		mav.addObject("fixerName", user.getFirstName());
		mav.addObject("queryId", queryId);
		mav.addObject("query", query);
		User admin = null;
		try {
			Set<User> admins = userService.findAdminByStatus("A");
			if (admins.size() > 0) {
				Iterator i = admins.iterator();
				while (i.hasNext()) {
					admin = (User) i.next();
				}
				if (admin != null) {
					mav.addObject("admin", admin);
				}
			}

		} catch (Exception e) {

		}
		mav.addObject("messagesSet", messagesSet);
		mav.addObject("currentPage", "dashboard");
		mav.addObject("memberImgIcon", member.getProfileIcon());
		mav.addObject("myUser", user);
		if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
			mav.addObject("Conflict", "No");
			mav.setViewName("dashboard/closedFixerQueryDetailPage.jsp");

		} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
			mav.addObject("Conflict", "Yes");
			mav.setViewName("dashboard/fixerPendingResolutionPage.jsp");

		} else if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {
			try {
				QueryAudit queryAudit = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
						query.getFixerId(), query.getQueryId(), "UI");
				if (queryAudit != null) {
					mav.addObject("memberReason", queryAudit.getMessage());
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("Conflict", "No");
			mav.setViewName("dashboard/unresolvedDetailPage.jsp");
		} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {
			try {
				QueryAudit queryAudit = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
						query.getFixerId(), query.getQueryId(), "UN");
				if (queryAudit != null) {
					mav.addObject("memberReason", queryAudit.getMessage());
				}
			} catch (FixitException e) {
			
				e.printStackTrace();
			}
			mav.addObject("Conflict", "No");
			mav.setViewName("dashboard/unresolvedDetailPage.jsp");
		} else {

		}
		return mav;
	}

	@RequestMapping(value = "/fixer/declineSelf", method = RequestMethod.GET)
	public ModelAndView fixerDeclineItself(@RequestParam String queryId, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				queryService.FixerAcceptedForNotAnswer(query.getQueryId());
				try {

					Set<User> fixers = userService.findFixerWhoMatchQueryCat(query.getQueryId());

					Iterator<User> iterator = fixers.iterator();
					while (iterator.hasNext()) {
						User fixer = iterator.next();
						if (userId == fixer.getUserId()) {
							fixers.remove(fixer);
						}
					}
					StringBuffer url = request.getRequestURL();
					String uri = request.getRequestURI();
					String ctx = request.getContextPath();
					String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
							query.getQueryId(), base);
					emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, query, queryExpireSet, base, null);
				} catch (Exception e) {

				}

				mav.setViewName("redirect:/fixer/dashBoard?msgKey=ICF");
			}
		} catch (FixitException e) {
			
			mav.setViewName("redirect:/fixer/dashBoard?msgKey=INCF");
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/fixer/fixerRaiseConflict", method = RequestMethod.POST)
	public ModelAndView fixerRaiseConflict(@RequestParam String queryId, @RequestParam String message,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				queryService.conflictRaisedByFixer(query, message);
				mav.setViewName("redirect:/fixer/dashBoard?msgKey=CSR");
			}
		} catch (FixitException e) {
			
			mav.setViewName("redirect:/fixer/dashBoard?msgKey=CNSR");
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/fixer/fixerQuestionDetailPage", method = RequestMethod.GET)
	public ModelAndView fixerQuestionDetailPage(@RequestParam String questionId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		User user = (User) session.getAttribute("user");
		MemberDashboardBo memberDashboardBo = null;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(questionId);
			memberDashboardBo = queryService.findQueryDetail(query, Calendar.getInstance(), user.getTimeZone());
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		
		mav.addObject("queryDetail", memberDashboardBo);
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "dashboard");
		mav.setViewName("dashboard/fixerQuestionDetailPage.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixerProfileImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFixerProfileImage(@RequestParam MultipartFile multipartFile,
			@RequestParam String userName) {
		String originalFileName = null;
		
		try {
			originalFileName = FileUpload.uploadFixerProfileImage(multipartFile, userName);
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility
				.getSuccessResponse(
						"originalFullFileName", DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
						+ DbConstants.PROFILE_FOLDER + DbConstants.SUFFIX + originalFileName,
						"originalFileName", originalFileName);

	}

	@RequestMapping(value = "/fixer/uploadDocDetailpage", method = RequestMethod.POST)
	@ResponseBody
	public String uploadDocDetailpage(@RequestParam MultipartFile file, @RequestParam String queryId,
			HttpSession session, HttpServletRequest request) {
		try {
			// FileUpload.deleteFileFromQueryFolder(fileName, userId, queryId);
			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}
			Query query = queryService.getQueryByHashCode(queryId);
			String virtualFileName = FileUpload.uploadDocumentQueryFolder(file, query.getUser().getUserId(),
					query.getQueryId());
			
			return virtualFileName;
		} catch (Exception e) {
			return "";
		}

	}

	@RequestMapping(value = "/fixer/uploadChatDocDetailpage", method = RequestMethod.POST)
	@ResponseBody
	public String uploadChatDocDetailpage(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "fixerId", required = false, defaultValue = "0") int fixerId,
			@RequestParam String queryId, @RequestParam String virtualFileName, HttpSession session,
			HttpServletRequest request) {
		try {
			if (file == null) {
				return "";
			}
			
			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}

			if (virtualFileName.contains("DOCTYPE html")) {
				virtualFileName = file.getOriginalFilename();
			}

			Query query = queryService.getQueryByHashCode(queryId);
			String docUrl = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX + DbConstants.DOCUMENTS_FOLDER
					+ DbConstants.SUFFIX + DbConstants.USER_FOLDER + query.getUser().getUserId() + DbConstants.SUFFIX
					+ DbConstants.QUERY_FOLDER + query.getQueryId() + DbConstants.SUFFIX + virtualFileName;

			QueryFiles queryFiles = new QueryFiles();
			queryFiles.setQuery(query);
			queryFiles.setFileName(virtualFileName);
			queryFiles.setFileType(DbConstants.DOCUMENT);
			queryFiles.setFileUrl(docUrl);
			queryFilesDao.store(queryFiles);

			QueryAudit audit = new QueryAudit();
			audit.setFixerId(fixerId);
			audit.setCustomerId(query.getUser().getUserId());
			audit.setMessage(docUrl);
			audit.setMsgFrom(DbConstants.FIXER);
			audit.setQuery(query);
			Calendar currentTime = Calendar.getInstance();
			audit.setAuditDate(currentTime);
			audit.setStatus(DbConstants.STATUS_WORKING_DOCUMENT);
			queryAuditService.saveQueryAudit(audit);
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			emailServcie.emailToMemberChatAttachMentMessage(audit, user, base, file);
			return docUrl;
		} catch (Exception e) {
			return "";
		}

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/fixer/exception?e=" + ex);
		return model;

	}

	@RequestMapping(value = "/fixer/exception", method = RequestMethod.GET)
	public ModelAndView takeToExceptionPage(@RequestParam Exception e) {
		ModelAndView model = new ModelAndView();
		model.addObject("errMsg", e.getMessage());
		model.setViewName("exception.jsp");
		return model;
	}

	@RequestMapping(value = "/fixer/download", method = RequestMethod.GET)
	public void download(@RequestParam("queryId") String queryId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Query query = queryService.getQueryByHashCode(queryId);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", query.getQueryTitle() + ".zip");
		response.setHeader(headerKey, headerValue);
		OutputStream outStream = response.getOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(outStream);

		Set<QueryFiles> queryFiles = queryFilesDao.findDocumentsByQueryId(query.getQueryId());
		for (QueryFiles file : queryFiles) {
			InputStream inputStream = FileUpload.downloadFileFromQueryFolder(file.getFileName(),
					query.getUser().getUserId(), query.getQueryId());
			ZipEntry ze = new ZipEntry(file.getFileName());
			
			zipOut.putNextEntry(ze);
			byte[] tmp = new byte[4 * 1024];
			int size = 0;
			while ((size = inputStream.read(tmp)) != -1) {
				zipOut.write(tmp, 0, size);
			}
			zipOut.flush();
			inputStream.close();
		}
		zipOut.close();

	}

	@RequestMapping(value = "/fixer/sendMessageReview", method = RequestMethod.POST)
	public ModelAndView fixerSendMessageReview(@RequestParam String queryId, @RequestParam int customerId,
			@RequestParam int fixerId, @RequestParam String msgFrom, @RequestParam String message,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(user.getUserId());
		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e2) {
			
			e2.printStackTrace();
		}
		if (chatUserGroup != null) {
			mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			mav.addObject("isGroup", "Yes");
			getNotificationCount(mav, user.getUserId());
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		QueryAudit queryAudit = null;
		QueryAuditBo queryAudiotBo = new QueryAuditBo();
		queryAudiotBo.setCustomerId(customerId);
		queryAudiotBo.setFixerId(fixerId);
		Query query = null;
		try {
			Integer queryIntegerId = Integer.parseInt(queryId);
			query = queryService.findQueryByQueryId(queryIntegerId);
		} catch (FixitException e1) {
		
			e1.printStackTrace();
		}
		if (query != null) {
			queryAudiotBo.setQueryId(query.getQueryId());
		}
		queryAudiotBo.setMsgFrom(msgFrom);
		queryAudiotBo.setStatus("IR");
		queryAudiotBo.setMessage(message);
		try {
			queryAudit = queryAuditService.saveQueryDetailMessage(queryAudiotBo);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		mav.addObject("status", "success");
		mav.addObject("myUser", user);
		mav.setViewName("dashboard/fixerPendingResolutionPage.jsp");
		return mav;
	}

	

	@RequestMapping(value = "/fixer/updateFixerAlert", method = RequestMethod.POST)
	public ModelAndView updateFixerAlert(@Validated @ModelAttribute("fixerAlert") UserBo userBo, BindingResult result,
			Model model, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			user = userService.updateUserAlert(userBo, user);
			if (user != null) {
				session.setAttribute("user", user);
			}
			model.addAttribute("userDetail", user);
			mav.addObject("userUpdate", "Your information has been updated!");
		} catch (Exception e) {
			mav.addObject("userUpdate", null);
			return null;

		}
		mav.addObject("currentPage", "settings");
		mav.addObject("member", user);
		mav.addObject("myUser", user);
		NewPassword newPassword = new NewPassword();
		mav.addObject("newPassword", newPassword);
		mav.setViewName("settings/fixerSettings.jsp");

		return mav;
	}

	@RequestMapping(value = "/fixer/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.addObject("myUser", user);
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(user.getUserId());
		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		if (chatUserGroup != null) {
			mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			mav.addObject("isGroup", "Yes");
			getNotificationCount(mav, user.getUserId());
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("privacy_policy.jsp");
		return mav;

	}

	@RequestMapping(value = "/fixer/userAgreement", method = RequestMethod.GET)
	public ModelAndView termAndCondition(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.addObject("myUser", user);
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(user.getUserId());
		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		if (chatUserGroup != null) {
			mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			mav.addObject("isGroup", "Yes");
			getNotificationCount(mav, user.getUserId());
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("term_condition.jsp");
		return mav;

	}

	@RequestMapping(value = "/fixer/saveNewPassword", method = RequestMethod.POST)
	public ModelAndView saveNewFixerPassword(@Validated @ModelAttribute("newPassword") NewPassword newPassword,
			BindingResult result, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		newPasswordValidator.setMyCurrentUser(user);
		newPasswordValidator.validate(newPassword, result);
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(user.getUserId());
		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e1) {
			
			e1.printStackTrace();
		}
		if (chatUserGroup != null) {
			mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			mav.addObject("isGroup", "Yes");
			getNotificationCount(mav, user.getUserId());
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		if (result.hasErrors()) {
			String exist = null;
			FieldError existed = result.getFieldError("passwordError");
			if (existed != null) {
				exist = existed.getDefaultMessage();
			}
			mav.addObject("passwordError", exist);
			mav.addObject("currentPage", "settings");
			mav.addObject("member", user);
			mav.addObject("myUser", user);

			mav.addObject("fixerAlert", user);
			mav.addObject("newPassword", newPassword);
			mav.addObject("timeZoneList", TimeZoneList.timeZoneList());

		} else {
			String exist = null;
			FieldError existed = result.getFieldError("passwordError");
			if (existed != null) {
				exist = existed.getDefaultMessage();
			}
			try {
				User updatedUser = userService.saveUserNewPassword(user, newPassword.getNewPassword());
				if (updatedUser != null) {
					session.setAttribute("user", null);
					session.setAttribute("user", updatedUser);
					mav.addObject("passwordError", "Your password has been updated.");
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}

			mav.addObject("newPassword", newPassword);
			mav.addObject("member", user);
			mav.addObject("myUser", user);
			mav.addObject("fixerAlert", user);
			mav.addObject("currentPage", "settings");
		}
		mav.setViewName("settings/fixerSettings.jsp");
		return mav;

	}

	@RequestMapping(value = "/fixer/fixerGroupDetail", method = RequestMethod.POST)
	public ModelAndView fixerGroupDetail(@RequestParam String groupId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		ChatGroups chatGroups = new ChatGroups();
		Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(user.getUserId());
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				getNotificationCount(mav, user.getUserId());
				messageNotificationService.updateMessageNotification(user.getUserId());
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			Integer groupIdInteger = Integer.parseInt(groupId);
			chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
			if (chatGroups != null) {
				chatMessageSet = chatMessageService.getAllChatMessagesBasedOnGroupId(chatGroups.getChatGroupId(), user);
			}

			mav.addObject("myImgIcon", user.getProfileIcon());
			mav.addObject("currentPage", "Message");
			mav.addObject("messagesSet", chatMessageSet);
			mav.addObject("groupId", groupId);
			mav.addObject("groupName", chatGroups.getChatGroupName());
			mav.addObject("myUserId", user.getUserId());
			mav.addObject("myUser", user);
			mav.setViewName("dashboard/fixerGroupDetailPage.jsp");

			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fixer/sendGroupMessage", method = RequestMethod.POST)

	public Map<String, Object> fixerSendGroupMessage(@RequestParam String message, @RequestParam String groupId,
			HttpSession session, HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		ChatGroups chatGroups = null;
		try {
			Integer groupIdInteger = Integer.parseInt(groupId);
			chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
			ChatMessage chatMessage = chatMessageService.saveChatMessage(message, user, chatGroups);
			Iterator<ChatUserGroup> itr = chatGroups.getChatUserGroup().iterator();
			while (itr.hasNext()) {
				ChatUserGroup chatGroup = itr.next();
				if ((int) chatGroup.getUserId() != (int) user.getUserId())
					messageNotificationService.saveMessageNotification(chatMessage, chatGroup.getUserId(), false);
			}
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";

			emailServcie.emailToAdminChatMessageByUser(message, user, base);

			Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();
			chatMessageSet = chatMessageService.getAllChatMessagesBasedOnGroupId(chatGroups.getChatGroupId(), user);
			return Utility.getSuccessResponse("chatMessageSet", chatMessageSet);
		} catch (Exception e) {
			e.printStackTrace();
			
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fixer/loadGroupMessage", method = RequestMethod.POST)
	public Map<String, Object> loadGroupMessage(@RequestParam String groupId, @RequestParam boolean visibleFlag,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		ChatGroups chatGroups = null;
		try {
			Integer groupIdInteger = Integer.parseInt(groupId);
			chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
			Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();
			chatMessageSet = chatMessageService.getAllChatMessagesBasedOnGroupId(chatGroups.getChatGroupId(), user);
			if (visibleFlag) {
				messageNotificationService.updateMessageNotification(user.getUserId());
			}
			return Utility.getSuccessResponse("chatMessageSet", chatMessageSet);
		} catch (Exception e) {
			e.printStackTrace();
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	private void getNotificationCount(ModelAndView modelAndView, int userId) {
		long countMsg = 0;
		try {
			countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("countMsg", countMsg);
	}

	@RequestMapping(value = "/fixer/inProgress1", method = RequestMethod.GET)
	public ModelAndView inProgress() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/fixerInProgress.jsp");
		return mav;
	}

	@RequestMapping(value = "/fixer/inProgress", method = RequestMethod.GET)
	public ModelAndView inProgressTab(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Calendar currentTime = Calendar.getInstance();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			long inProgressFixerCount = queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING);
			int totalPage = (int) Math.ceil((float) inProgressFixerCount / DbConstants.PAGE_SIZE);
			mav.addObject("inProgressFixerCount", objectMapper.writeValueAsString(inProgressFixerCount));
			mav.addObject("queryCount", inProgressFixerCount);
			mav.addObject("queryList",
					userService.findFixerQueryWorking(userId, currentTime, user.getTimeZone(), -1, -1));
			mav.addObject("inProgressNotification",
					queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
			mav.setViewName("/fixerInProgress.jsp");

			User myUser = userService.findUserById(userId);
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			String memberImagePath = myUser.getProfilePhoto();
			mav.addObject("memberImagePath", memberImagePath);
			mav.addObject("user", myUser);
			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fixer/fixerRating", method = RequestMethod.GET)
	public Map<String, List<FixerRatingCountBo>> getFixerrRatingStat(HttpSession session) {
		User user = (User) session.getAttribute("user");
		int fixerId = user.getUserId();

		Map<String, List<FixerRatingCountBo>> responseMap = new HashMap<String, List<FixerRatingCountBo>>();
		try {
			List<FixerRatingCountBo> count = fixerRatingService.findRatingByFixerIdAndRating(user.getUserId());
			

			responseMap.put("rating", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseMap;

	}
	
	@ResponseBody
	@RequestMapping(value = "/fixer/getReqStatus", method = RequestMethod.POST)
	public Map<String, Object> getReqStatusAjax(@RequestParam Integer queryId,
			HttpSession session) {
		try{
			Query query = queryService.findQueryByQueryId(queryId);
		
		
			return Utility.getSuccessResponse("queryData", query);
		
		}catch(FixitException e)
		{
			e.printStackTrace();
			return Utility.getFailureResponse(e.getMessage(),null);
		}
		
		
	}

	@RequestMapping(value = "/fixer/request", method = RequestMethod.GET)
	public ModelAndView fixerRequest(@RequestParam("msgKey") String msgKey,
			@RequestParam(value = "status", required = false, defaultValue = "InProgress") String status,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");

		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);

			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				mav.addObject("countMsg", countMsg);

			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
				mav.addObject("countMsg", 0);
			}
			Calendar currentTime = Calendar.getInstance();
			int startIndex;
			if (pageNo == 0) {
				startIndex = 0;
			} else {
				if (flag.equals("left")) {
					startIndex = (pageNo - 2) * DbConstants.PAGE_SIZE;
				} else {
					if (flag.equals("right")) {
						startIndex = (pageNo) * DbConstants.PAGE_SIZE;
					} else {
						startIndex = (pageNo - 1) * DbConstants.PAGE_SIZE;
					}
				}
			}

			int totalPage = 0;

			long closedCount = queryService.findFixerClosedStasCount(userId);
			BigInteger unClaimedOpenCount = queryService.findQueryNotAcceptedFixerCount(userId);
			long inProgressCount = queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING);
			long appliedCountbyFixer = queryAppliedFixersService.getAppliedQueriesCount(userId);

			mav.addObject("inProgressCount", inProgressCount);
			mav.addObject("openRequestCount", unClaimedOpenCount);
			mav.addObject("fixedIssuesCount", closedCount);
			mav.addObject("appliedCountbyFixer", appliedCountbyFixer);

			long pendingResolutionFixerCount = queryService.findFixerPendingResolutionStasCount(userId);
			mav.addObject("awaitingConfirmationCount", pendingResolutionFixerCount);
			switch (status) {
			case "Unclaimed":
				BigInteger count = unClaimedOpenCount;
				long unClaimedCount = count.longValue();
				totalPage = (int) Math.ceil((float) unClaimedCount / DbConstants.PAGE_SIZE);
				Set<MemberDashboardBo> unClaimedQueryBo = userService.findFixerQueryNotAccepted(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("queryCount", unClaimedCount);
				mav.addObject("queryCounts", unClaimedQueryBo.size());
				mav.addObject("currentPage", "Unclaimed");
				mav.addObject("queryList", unClaimedQueryBo);
				mav.addObject("inProgressNotification",
						queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
				mav.setViewName("dashboard/fixerOpenRequests.jsp");
				break;

			case "InProgress":
				long inProgressFixerCount = queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING);
				totalPage = (int) Math.ceil((float) inProgressFixerCount / DbConstants.PAGE_SIZE);
				Set<MemberDashboardBo> queryList = userService.findFixerQueryWorking(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("inProgressFixerCount", objectMapper.writeValueAsString(inProgressFixerCount));
				mav.addObject("queryCount", inProgressFixerCount);
				mav.addObject("queryCounts", queryList.size());
				mav.addObject("queryList", queryList);
				mav.addObject("currentPage", "InProgress");
				mav.addObject("inProgressNotification",
						queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				mav.addObject("allCategory", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.setViewName("dashboard/fixerInProgressRequests.jsp");
				break;

			case "Closed":
				long closedFixerCount = closedCount;
				totalPage = (int) Math.ceil((float) closedFixerCount / DbConstants.PAGE_SIZE);
				queryList = userService.findFixerQueryClosed(userId, currentTime, user.getTimeZone(), startIndex,
						DbConstants.PAGE_SIZE);
				mav.addObject("closedFixerCount", objectMapper.writeValueAsString(closedFixerCount));
				mav.addObject("queryCount", closedFixerCount);
				mav.addObject("queryCounts", queryList.size());
				mav.addObject("queryList", queryList);
				mav.addObject("currentPage", "Closed");
				mav.addObject("inProgressNotification",
						queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				mav.addObject("allCategory", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.setViewName("dashboard/fixerClosedRequests.jsp");
				break;

			case "Pending":
				pendingResolutionFixerCount = queryService.findFixerPendingResolutionStasCount(userId);
				totalPage = (int) Math.ceil((float) pendingResolutionFixerCount / DbConstants.PAGE_SIZE);
				mav.addObject("queryCount", pendingResolutionFixerCount);
				mav.addObject("queryList", userService.findFixerQueryPendingResolution(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE));
				mav.addObject("inProgressNotification",
						queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("currentPage", "Pending");
				break;

			case "Applied":
				unClaimedCount = appliedCountbyFixer;
				totalPage = (int) Math.ceil((float) unClaimedCount / DbConstants.PAGE_SIZE);
				unClaimedQueryBo = userService.findFixerQueryApplied(userId, currentTime, user.getTimeZone(),
						startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("queryCount", unClaimedCount);
				mav.addObject("queryCounts", unClaimedQueryBo.size());
				mav.addObject("queryList", unClaimedQueryBo);
				mav.addObject("inProgressNotification",
						queryService.findFixerStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("currentPage", "Applied");
				mav.setViewName("dashboard/fixerApplied.jsp");
				break;
			}

			switch (flag) {
			case "left":
				if (pageNo % DbConstants.PAGE_NO_DISPLAY == 1) {
					mav.addObject("startPage",
							(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1);
					if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1)
							* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
						mav.addObject("endPage", totalPage);
					} else {
						mav.addObject("endPage",
								(((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1) * DbConstants.PAGE_NO_DISPLAY);
					}

				} else {
					if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
						mav.addObject("startPage",
								(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1);
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							mav.addObject("endPage", totalPage);
						} else {
							mav.addObject("endPage",
									(((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY);
						}

					} else {
						mav.addObject("startPage",
								(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1);
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1))
								* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							mav.addObject("endPage", totalPage);
						} else {
							mav.addObject("endPage",
									(((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)) * DbConstants.PAGE_NO_DISPLAY);
						}

					}
				}
				mav.addObject("currentPageNo", pageNo - 1);

				break;

			case "right":
				if (pageNo > 0) {

					if (pageNo + DbConstants.PAGE_NO_DISPLAY > totalPage) {
						if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
							mav.addObject("startPage",
									(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1);
							mav.addObject("endPage", totalPage);
						} else {
							mav.addObject("startPage",
									(DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								mav.addObject("endPage", totalPage);
							} else {
								mav.addObject("endPage",
										((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY);
							}

						}
					} else {
						if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
							mav.addObject("startPage",
									(DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
							mav.addObject("endPage",
									((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY);
						} else {
							mav.addObject("startPage",
									(DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
							mav.addObject("endPage",
									((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY);
						}
					}
					mav.addObject("currentPageNo", pageNo + 1);

				} else {
					mav.addObject("startPage", 1);
					if (DbConstants.PAGE_NO_DISPLAY <= totalPage) {
						mav.addObject("endPage", DbConstants.PAGE_NO_DISPLAY);
					} else {
						mav.addObject("endPage", totalPage);
					}
					mav.addObject("currentPageNo", 1);

				}

				break;

			case "current":
				if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
					mav.addObject("startPage",
							(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1);
					if ((((pageNo / DbConstants.PAGE_NO_DISPLAY)) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
						mav.addObject("endPage", totalPage);
					} else {
						mav.addObject("endPage",
								((pageNo / DbConstants.PAGE_NO_DISPLAY)) * DbConstants.PAGE_NO_DISPLAY);
					}

				} else {
					mav.addObject("startPage",
							(DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
					if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
						mav.addObject("endPage", totalPage);
					} else {
						mav.addObject("endPage",
								((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY);
					}

				}
				mav.addObject("currentPageNo", pageNo);
				break;

			default:
				break;
			}

			mav.addObject("totalPage", totalPage);

			String message = "";
			String msgType = "";
			switch (msgKey) {
			case "R":
				message = "";
				break;

			case "AP":
				message = "You have successfully applied for this request. You can message the Member by clicking on \"More\".";
				msgType = "success";
				break;

			case "D":
				message = "You have successfully declined this request.";
				msgType = "success";
				break;

			case "AC":
				message = "Issue Already Claimed ";
				msgType = "Error";
				break;
			case "FRFSL":
				message = "Fixer Removed From The Selected List By Member";
				msgType = "Error";
				break;
			case "IAC":
				message = "Issue Already Closed ";
				msgType = "Error";
				break;

			case "IAR":
				message = "Issue Already In Review With Another Fixer ";
				msgType = "Error";
				break;

			case "IAD":
				message = "Issue Is Deleted By Member ";
				msgType = "Error";
				break;

			case "IUN":
				message = "Issue Marked As Not Fixed By Member For His Current Fixer ";
				msgType = "Error";
				break;

			case "IUI":
				message = "Issue Marked As Not Fixed  By Member For Inactivity Of His Current Fixer ";
				msgType = "Error";
				break;

			case "AD":
				message = "Issue Already Declined";
				msgType = "Error";
				break;

			case "EO":
				message = "Some error occured";
				msgType = "Error";
				break;

			case "ICF":
				message = "Issue Closed By This Fixer";
				msgType = "success";
				break;

			case "INCF":
				message = "Issue Not Closed By This Fixer because of some error";
				msgType = "error";
				break;

			case "CSR":
				message = "Conflict successfully raised";
				msgType = "success";
				break;

			case "CNSR":
				message = "Conflict not successfully raised beacuse of some error";
				msgType = "success";
				break;

			case "EX":
				message = "Email Already Expired";
				msgType = "Error";
				break;

			case "INP":
				message = "Request has been assigned successfully.";
				msgType = "success";
				break;

			}

			mav.addObject("message", message);
			mav.addObject("msgType", msgType);

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		mav.addObject("currentPage", "dashboard");
		mav.addObject("myUser", user);
		mav.addObject("status", status);

		return mav;
	}

	@RequestMapping(value = "/fixer/findMemberDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberSendGroupMessage(@RequestParam Integer memberId,
			@RequestParam(value = "queryId", required = false, defaultValue = "EMPTY") String queryId,

			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			if (null != queryId && (!queryId.equals("EMPTY") && !queryId.trim().isEmpty())) {
				List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
				messagesSet = queryAuditService.getQueryOpenDetailMessages(queryId, user.getUserId(), -1, -1);
				String messString;
				String[] messArr;
				for (QueryAudit audit : messagesSet) {
					audit.setAuditTime(
							TimeDiffUtility.timeToSpecificTimezoneForChat(audit.getAuditDate(), user.getTimeZone()));
					if (DbConstants.STATUS_WORKING_DOCUMENT.equals(audit.getStatus())) {
						messString = audit.getMessage();
						messArr = messString.split("/");
						audit.setDocumentFilename(messArr[messArr.length - 1]);
					}
				}

				UserBo userBo = userService.findMemberBoById(memberId);
				return Utility.getSuccessResponse("status", "success", "fixer", userBo, "messagesSet", messagesSet);
			} else {
				UserBo userBo = userService.findMemberBoById(memberId);

				return Utility.getSuccessResponse("status", "success", "fixer", userBo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@RequestMapping(value = "/fixer/apply", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView fixerApply(@RequestParam("queryId") Integer queryId, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {

			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}
			userId = user.getUserId();
			Query query = queryService.findQueryByQueryId(queryId);
			UserDecline userDecline = userDeclineService.findDeclineQueryByAndUserId(userId, queryId);
			if (userDecline != null) {
				modelAndView.setViewName("redirect:/fixer/request?msgKey=AD&status=Applied");
				return modelAndView;
			}

			List<QueryFixers> myQueryFixers = queryFixersService.getQueryFixersBasedOnQueryId(query.getQueryId(), -1,
					-1);
			if (myQueryFixers != null && myQueryFixers.size() > 0) {
				QueryFixers myQueryFixer = queryFixersService
						.findQueryFixersBasedOnQueryIdAndFixerId(query.getQueryId(), userId, -1, -1);
				if (myQueryFixer == null) {
					modelAndView.setViewName("redirect:/fixer/request?msgKey=FRFSL&status=Applied");
					return modelAndView;
				}
			}

			

			queryAppliedFixersService.saveAppliedFixer(user, query);
			User member = query.getUser();
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";

			List<QueryFixers> selectedFixer = queryFixersService.getQueryFixersBasedOnQueryId(queryId, -1, -1);
			int selectedFixerSize = selectedFixer.size();
			

			emailServcie.emailToMemberFixerApplyRequest(base, query, member, user);

			modelAndView.setViewName("redirect:/fixer/request?msgKey=AP&status=Applied");

			
		} catch (Exception e) {

		}
		return modelAndView;
	}

	@RequestMapping(value = "/fixer/updateCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCategory(HttpSession session,
			@RequestParam(value = "catIds[]") List<Integer> catIds) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<CategoryType> categorySet = new HashSet<CategoryType>();
		try {
			UserCategory userCategory;
			UserCategoryBo userCategoryBo = new UserCategoryBo();
			userCategoryService.deleteUserCategory(userId, catIds);
			for (int catId : catIds) {
				userCategory = userCategoryService.findUserCategoryByUserIdandcatId(userId, catId);
				if (userCategory == null) {
					userCategoryBo.setCatId(catId);
					userCategoryBo.setUserId(userId);
					userCategory = userCategoryService.saveUserCat(userCategoryBo);
				}
				categorySet.add(userCategory.getCategoryType());
			}
		} catch (Exception e) {
			return Utility.getFailureResponse("catIds", null);
		}

		return Utility.getSuccessResponse("catIds", categorySet);
	}

	@RequestMapping(value = "/fixer/updateIndustry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIndustry(HttpSession session,
			@RequestParam(value = "industryIds[]") List<Integer> industryIds) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<IndustryType> industrySet = new HashSet<IndustryType>();
		try {
			UserIndustry userIndustry = null;
			userIndustryService.deleteUserIndustry(userId, industryIds);
			for (int inds : industryIds) {
				userIndustry = userIndustryService.findUserIndustryByUserIdandIndstId(userId, inds);
				if (userIndustry == null) {
					userIndustry = new UserIndustry();
					userIndustry.setUser(user);
					userIndustry.setIndustryType(industryTypeService.findIndustryTypeByIndstId(inds));
					userIndustry = userIndustryService.saveIndustry(userIndustry);
				}
				industrySet.add(userIndustry.getIndustryType());
			}
		} catch (Exception e) {
			return Utility.getFailureResponse("industryIds", null);
		}
		return Utility.getSuccessResponse("industryIds", industrySet);
	}

	@RequestMapping(value = "/fixer/deleteIndustry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIndustry(HttpSession session, @RequestParam Integer indstId) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			int count = userIndustryService.deleteUserIndustryByUserIdandIndstId(userId, indstId);
			if (count < 1) {
				throw new FixitException("", "");
			}
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();
	}

	@RequestMapping(value = "/fixer/deleteCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCategory(HttpSession session, @RequestParam Integer catId) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			int count = userCategoryService.deleteUserCategoryBasedOnUserIdandCatId(userId, catId);
			if (count < 1) {
				throw new FixitException("", "");
			}
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();
	}

	@RequestMapping(value = "/fixer/denyAppliedJob", method = RequestMethod.GET)
	public ModelAndView denyAppliedJob(HttpSession session, Integer queryId) {
		ModelAndView modelAndView = null;
		User user = (User) session.getAttribute("user");

		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		try {
			queryAppliedFixersService.saveFixerDecline(userId, queryId);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		modelAndView = new ModelAndView();

		modelAndView.setViewName("redirect:/fixer/request?msgKey=D&status=Applied");
		return modelAndView;
	}

	@RequestMapping(value = "/fixer/updateMessageCounts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMessageCount(HttpSession session, @RequestParam Integer queryId,
			@RequestParam Integer fixerId) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		int count = 0;
		try {
			if (user.getUserType().equals("C"))
				count = queryAuditService.updateMessagesToRead(queryId, fixerId, "F");
			else
				count = queryAuditService.updateMessagesToRead(queryId, user.getUserId(), "C");

		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();
	}

	@RequestMapping(value = "/fixer/previousGiattChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> previousGiattChart(HttpSession session, @RequestParam String currentStartDate)
			throws FixitException {

		User user = (User) session.getAttribute("user");
		Calendar startDate = TimeDiffUtility.convertStringtoCalender(currentStartDate, "dd/MM/yyyy",
				user.getTimeZone());
		int currentMonth = startDate.get(Calendar.MONTH);
		startDate.set(Calendar.MONTH, currentMonth - 1);
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.DAY_OF_MONTH, 31);
		endDate.set(Calendar.MONTH, currentMonth - 1);
		ClosedQueryJson closedQueryJson = buildResponse(user, startDate, endDate);

		return Utility.getSuccessResponse("status", "success", "chartData", closedQueryJson);
	}

	@RequestMapping(value = "/fixer/nextGanttChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nextGanttChart(HttpSession session, @RequestParam String currentStartDate)
			throws FixitException {

		User user = (User) session.getAttribute("user");
		Calendar startDate = TimeDiffUtility.convertStringtoCalender(currentStartDate, "dd/MM/yyyy",
				user.getTimeZone());
		int currentMonth = startDate.get(Calendar.MONTH);
		startDate.set(Calendar.MONTH, currentMonth + 1);
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.DAY_OF_MONTH, 31);
		endDate.set(Calendar.MONTH, currentMonth + 1);
		ClosedQueryJson closedQueryJson = buildResponse(user, startDate, endDate);

		return Utility.getSuccessResponse("status", "success", "chartData", closedQueryJson);
	}

	private ClosedQueryJson buildResponse(User user, Calendar startDate, Calendar endDate) throws FixitException {

		startDate.set(Calendar.DAY_OF_MONTH, 1);
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);

		Set<Query> queries = queryService.findQueryClosedFixer(user.getUserId(), startDate, endDate);
		List<Segment> segments;
		ClosedQueryJson closedQueryJson = new ClosedQueryJson();
		List<FrequencyDuration> frequencyDurations = new ArrayList<FrequencyDuration>();
		closedQueryJson.setStartDate(
				TimeDiffUtility.timeToSpecificTimezoneWithChangeFormat(startDate, user.getTimeZone(), "dd/MM/yyyy"));
		closedQueryJson.setFrequencyDurations(frequencyDurations);

		Segment segment;
		;
		FrequencyDuration frequencyDuration;
		int count = 1;
		for (Query query : queries) {
			segment = new Segment();
			segments = new ArrayList<Segment>();
			frequencyDuration = new FrequencyDuration();
			segment.setTask(query.getQueryTitle());
			segment.setStart(TimeDiffUtility.findDayDiff(startDate, query.getDateRaised()));
			segment.setDuration(TimeDiffUtility.findDayDiff(startDate, query.getClosureDate())
					- TimeDiffUtility.findDayDiff(startDate, query.getDateRaised()));
			segment.setColor("#66e066");
			frequencyDuration.setCategory(count);
			segments.add(segment);
			frequencyDuration.setSegments(segments);
			frequencyDurations.add(frequencyDuration);
			count++;
		}
		return closedQueryJson;
	}

	@ResponseBody
	@RequestMapping(value = "/fixer/getComparePassword", method = RequestMethod.POST)
	public Map<String, Object> comparePassword(HttpSession session, String password) {
		User user = (User) session.getAttribute("user");
		String dbPassword = user.getPassword();
		boolean bool = PasswordEncode.comparePasswords(password, dbPassword);

		if (bool) {
			return Utility.getSuccessResponse();

		} else {
			return Utility.getFailureResponse();

		}

	}

	@ResponseBody
	@RequestMapping(value = "/fixer/updateMessageNotification", method = RequestMethod.POST)
	public Map<String, Object> updateMessageNotification(HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			messageNotificationService.updateMessageNotification(user.getUserId());
			return Utility.getSuccessResponse();
		} catch (Exception e) {
			e.printStackTrace();
			return Utility.getFailureResponse();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fixer/adminMessageNotification", method = RequestMethod.POST)
	public Map<String, Object> adminMessageNotification(HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			long countMsg = messageNotificationService.getAllNotificationBasedOnId(user.getUserId());
			return Utility.getSuccessResponse("countMsg", countMsg);
		} catch (Exception e) {
			e.printStackTrace();
			return Utility.getFailureResponse();
		}
	}

	@RequestMapping(value = "/fixer/memberLoadGroupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberLoadGroupMessage(@RequestParam Integer memberId,
			@RequestParam(value = "queryId", required = false, defaultValue = "EMPTY") String queryId,

			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
			messagesSet = queryAuditService.getQueryOpenDetailMessages(queryId, user.getUserId(), -1, -1);
			String messString;
			String[] messArr;
			for (QueryAudit audit : messagesSet) {
				audit.setAuditTime(TimeDiffUtility.timeToSpecificTimezoneForChat(audit.getAuditDate(), user.getTimeZone()));
				if (DbConstants.STATUS_WORKING_DOCUMENT.equals(audit.getStatus())) {
					messString = audit.getMessage();
					messArr = messString.split("/");
					audit.setDocumentFilename(messArr[messArr.length - 1]);
				}
			}

			
			return Utility.getSuccessResponse("status", "success", "messagesSet", messagesSet);

		} catch (Exception e) {
			e.printStackTrace();
			
			return Utility.getSuccessResponse("status", "failed");
		}

	}

}
