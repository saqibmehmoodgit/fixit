package com.fixit.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;
import com.fixit.dao.CategoryTypeDao;
import com.fixit.dao.QueryCategoryDao;
import com.fixit.dao.QueryFilesDao;
import com.fixit.dao.UserDao;
import com.fixit.domain.bo.AppliedFixersListBo;
import com.fixit.domain.bo.ChatMessageBo;
import com.fixit.domain.bo.ClosedQueryJson;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.bo.FrequencyDuration;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.MemberProfile;
import com.fixit.domain.bo.MemberSetting;
import com.fixit.domain.bo.MemberSignUpProfile;
import com.fixit.domain.bo.NewPassword;
import com.fixit.domain.bo.PieChartData;
import com.fixit.domain.bo.QueryAuditBo;
import com.fixit.domain.bo.QueryBo;
import com.fixit.domain.bo.QueryData;
import com.fixit.domain.bo.QueryFilesBo;
import com.fixit.domain.bo.RequestAreas;
import com.fixit.domain.bo.Segment;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.UsersAccountingBo;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.Country;
import com.fixit.domain.vo.FixerAccounting;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.IndustryType;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.domain.vo.QueryCategory;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.UserIndustry;
import com.fixit.domain.vo.UsersAccounting;
import com.fixit.service.BrainTreeIntegrationService;
import com.fixit.service.CategoryTypeService;
import com.fixit.service.ChatGroupsService;
import com.fixit.service.ChatMessageService;
import com.fixit.service.ChatUserGroupService;
import com.fixit.service.CountryService;
import com.fixit.service.EmailServcie;
import com.fixit.service.FavouriteFixerService;
import com.fixit.service.FixerAccountingService;
import com.fixit.service.FixerRatingService;
import com.fixit.service.IndustryTypeService;
import com.fixit.service.MessageNotificationService;
import com.fixit.service.PayPalIntegrationService;
import com.fixit.service.PayPalSinglePaymentService;
import com.fixit.service.QueryAppliedFixersService;
import com.fixit.service.QueryAuditService;
import com.fixit.service.QueryExpireService;
import com.fixit.service.QueryFixersService;
import com.fixit.service.QueryService;
import com.fixit.service.UserCategoryService;
import com.fixit.service.UserCreditService;
import com.fixit.service.UserDeclineService;
import com.fixit.service.UserIndustryService;
import com.fixit.service.UserService;
import com.fixit.service.UsersAccountingService;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.FixitVariables;
import com.fixit.utility.PasswordEncode;
import com.fixit.utility.TimeDiffUtility;
import com.fixit.utility.TimeZoneList;
import com.fixit.utility.Utility;
import com.fixit.validation.MemberSettingsValidator;
import com.fixit.validation.MemberStepTwoValidation;
import com.fixit.validation.NewPasswordValidator;
import com.fixit.validation.PostQuestionValidation;

@Controller("UserController")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private NewPasswordValidator newPasswordValidator;

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Autowired
	private FixerRatingService fixerRatingService;

	@Autowired
	private UserCategoryService userCategoryService;

	@Autowired
	private ChatGroupsService chatGroupsService;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private FavouriteFixerService favouriteFixerService;

	@Autowired
	private BrainTreeIntegrationService brainTreeIntegrationService;

	@Autowired
	private MemberSettingsValidator memberSettingsValidator;

	@Autowired
	private UserDeclineService userDeclineService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageNotificationService messageNotificationService;

	@Autowired
	private QueryExpireService queryExpireService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private UserCreditService userCreditService;

	@Autowired
	private MemberStepTwoValidation memberStepTwoValidation;

	@Autowired
	private IndustryTypeService industryTypeService;

	@Autowired
	private UserIndustryService userIndustryService;

	@Autowired
	private ChatUserGroupService chatUserGroupService;

	@Autowired
	private CategoryTypeService categoryTypeService;

	@Autowired
	private PostQuestionValidation postQuestionValidation;

	@Autowired
	private QueryCategoryDao queryCategoryDao;

	@Autowired
	private QueryAuditService queryAuditService;

	@Autowired
	private EmailServcie emailServcie;

	@Autowired
	private QueryFilesDao queryFilesDao;

	@Autowired
	private PayPalIntegrationService payPalIntegrationService;

	@Autowired
	private PayPalSinglePaymentService payPalSinglePaymentService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private QueryFixersService queryFixersService;

	private static final int BUFFER_SIZE = 4096;

	@Autowired
	private FixerAccountingService fixerAccountingService;

	@Autowired
	private QueryAppliedFixersService queryAppliedFixersService;

	@Autowired
	private UsersAccountingService usersAccountingService;

	

	/**
	 * postQuestion GET
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/newRequest", method = RequestMethod.GET)
	public ModelAndView request(HttpSession session) {
		ModelAndView modelAndView = null;
		User user = (User) session.getAttribute("user");

		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		modelAndView = new ModelAndView();
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(userId);
		ChatUserGroup chatUserGroup = null;
		try {
			chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
		} catch (FixitException e1) {
			
			e1.printStackTrace();
		}
		if (chatUserGroup != null) {
			modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
			modelAndView.addObject("isGroup", "Yes");
			long countMsg = 0;
			try {
				countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			modelAndView.addObject("countMsg", countMsg);
		} else {
			modelAndView.addObject("isGroup", "No");
			modelAndView.addObject("groupId", -1);
		}
		long points = userCreditService.getPoints(userId);
		

		ObjectMapper objectMapper = new ObjectMapper();
		try {

			modelAndView = new ModelAndView();
			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			modelAndView.addObject("inProgressCount", inProgressCount);
			modelAndView.addObject("openRequestCount", unClaimedCount);
			modelAndView.addObject("fixedIssuesCount", fixedIssuesCount);

			QueryBo query = new QueryBo();
			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			query.setQueryMode("CREATE");
			modelAndView.addObject("query", query);
			modelAndView.addObject("userId", userId);

			modelAndView.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategory",
					categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("CREATE"));
			modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));
			modelAndView.addObject("documents", objectMapper.writeValueAsString(query.getDocuments()));
			modelAndView.addObject("countryList", (countryService.findAllCountry()));
			modelAndView.addObject("myUserJson", objectMapper.writeValueAsString(user));
			modelAndView.addObject("parentCategory",
					categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("parentCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
		} catch (Exception e) {

		}
		Set<CategoryType> categoryTypes = null;
		try {
			categoryTypes = categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE);
		} catch (FixitException e) {
			e.printStackTrace();
		}
		Set<QueryFiles> files = new HashSet<QueryFiles>();

		modelAndView.addObject("documents", files);
		modelAndView.addObject("selectedFixer", "");
		modelAndView.addObject("points", points);
		modelAndView.addObject("allCategories", categoryTypes);
		modelAndView.addObject("myUser", user);

		modelAndView.addObject("currentPage", "newRequest");
		modelAndView.setViewName("/request.jsp");
		return modelAndView;
	}

	/**
	 * postQuestion GET
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/editQuestion", method = RequestMethod.GET)
	public ModelAndView editPostQuestion(@RequestParam("queryId") String queryId, HttpSession session) {
		Map<String, String> selecedFixers = new HashMap<String, String>();
		ModelAndView modelAndView = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			modelAndView = new ModelAndView();
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				modelAndView.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				modelAndView.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				modelAndView.addObject("countMsg", countMsg);
			} else {
				modelAndView.addObject("isGroup", "No");
				modelAndView.addObject("groupId", -1);
			}

			Query queryVo = queryService.getQueryByHashCode(queryId);
			
			if (DbConstants.STATUS_WORKING.toString().equals(queryVo.getCurrentStatus())) {
				modelAndView.addObject("currentPage", "dashboard");
				modelAndView.setViewName("redirect:/member/request?msgKey=AC");
				return modelAndView;
			}
			QueryBo query = new QueryBo();
			query.setQueryId(queryVo.getQueryId());
			if (queryVo.getFixerId() > 0) {
				query.setFixerId(queryVo.getFixerId());
			}
			query.setQueryTitle(queryVo.getQueryTitle());
			query.setQueryContent(queryVo.getQueryContent());
			query.setCurrentStatus(queryVo.getCurrentStatus());
			query.setFixerId(queryVo.getFixerId());
			query.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormat(
					queryVo.getQueryDeadlineDate(), user.getTimeZone(), "dd/mm/yyyy HH:mm:ss"));
			query.setQueryMode("EDIT");
			selecedFixers = queryFixersService.getQueryFixersIdsAndNamesBasedOnQueryId(queryVo.getQueryId(), -1, -1);
			if ("true".equals(selecedFixers.get("selectedFixers"))) {
				modelAndView.addObject("selectedFixer", true);
			} else {
				modelAndView.addObject("selectedFixer", false);
			}
			query.setFixersIds(selecedFixers.get("fixersId"));
			query.setFixersNames(selecedFixers.get("fixersNames"));

			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			Set<QueryCategory> queryCategories = queryCategoryDao.findQueryCategoryByQueryId(queryVo.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				catTypeSet.add(categoryTypeService.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId()));
			}

			
			modelAndView.addObject("query", query);
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("parentCategory",
					categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("allCategory", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

			modelAndView.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			// categoryId List
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("EDIT"));
			modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));
			Set<QueryFiles> files = queryFilesDao.findDocumentsByQueryId(queryVo.getQueryId());
			List<String> fileNames = new ArrayList<String>();

			
			if (files.size() > 0) {
				modelAndView.addObject("documents", objectMapper.writeValueAsString(files));
			} else {
				modelAndView.addObject("documents", null);
			}

		
		} catch (Exception e) {

		}
		Set<CategoryType> categoryTypes = null;
		try {
			categoryTypes = categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		modelAndView.addObject("allCategories", categoryTypes);
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("currentPage", "dashboard");
		modelAndView.setViewName("profile/askQuestion.jsp");
		return modelAndView;
	}

	/**
	 * postQuestion GET
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/copyQuestion", method = RequestMethod.GET)
	public ModelAndView copyClosedQuestion(@RequestParam("queryId") String queryId, HttpSession session) {
		Map<String, String> selecedFixers = new HashMap<String, String>();
		ModelAndView modelAndView = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<CategoryType> categoryTypes = null;

		ObjectMapper objectMapper = new ObjectMapper();
		try {

			modelAndView = new ModelAndView();
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			long points = userCreditService.getPoints(userId);
			modelAndView.addObject("points", points);
		
			Query queryVo = queryService.getQueryByHashCode(queryId);
			QueryBo query = new QueryBo();
			query.setQueryTitle(queryVo.getQueryTitle());
			query.setQueryContent(queryVo.getQueryContent());
			query.setCurrentStatus(queryVo.getCurrentStatus());
			query.setQueryMode("CREATE");
			selecedFixers = queryFixersService.getQueryFixersIdsAndNamesBasedOnQueryId(queryVo.getQueryId(), -1, -1);
			if ("true".equals(selecedFixers.get("selectedFixers"))) {
				modelAndView.addObject("selectedFixer", true);
			} else {
				modelAndView.addObject("selectedFixer", false);
			}
			query.setFixersIds(selecedFixers.get("fixersId"));
			query.setFixersNames(selecedFixers.get("fixersNames"));

			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			Set<QueryCategory> queryCategories = queryCategoryDao.findQueryCategoryByQueryId(queryVo.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				catTypeSet.add(categoryTypeService.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId()));
			}

			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			modelAndView.addObject("inProgressCount", inProgressCount);
			modelAndView.addObject("openRequestCount", unClaimedCount);
			modelAndView.addObject("fixedIssuesCount", fixedIssuesCount);

			modelAndView.addObject("query", query);
			modelAndView.addObject("userId", userId);

			modelAndView.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategory",
					categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("CREATE"));
			modelAndView.addObject("queryCategories", catTypeSet);
			modelAndView.addObject("countryList", (countryService.findAllCountry()));
			modelAndView.addObject("parentCategory",
					categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("parentCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));

			modelAndView.addObject("myUserJson", objectMapper.writeValueAsString(user));
			modelAndView.addObject("allCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

		
			Set<QueryFiles> files = queryFilesDao.findDocumentsByQueryId(queryVo.getQueryId());
			categoryTypes = categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE);

			
			if (files.size() > 0) {

				modelAndView.addObject("documents", files);
			} else {
				modelAndView.addObject("documents", null);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		modelAndView.addObject("allCategories", categoryTypes);
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("currentPage", "dashboard");
		modelAndView.setViewName("/request.jsp");
		return modelAndView;
	}

	/**
	 * postQuestion POST
	 * 
	 * @param queryBo
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/member/askQuestion", method = RequestMethod.POST)
	public ModelAndView postQuestion(@Valid @ModelAttribute("query") QueryBo query, BindingResult result, Model model,
			HttpServletRequest request, HttpSession session) {
		Query savedQuery = null;
		Set<User> selectedFixersSet = new HashSet<User>();

		ModelAndView modelAndView = new ModelAndView();
		Set<Integer> fixerIdList = new HashSet<Integer>();
		Set<Integer> fixerNameList = new HashSet<Integer>();
		String fixersIdJson = query.getFixersIds();
		
		String fixersIdJsonArr[] = fixersIdJson.split(",");
		
		if (fixersIdJsonArr != null && fixersIdJsonArr.length > 0 && !(fixersIdJson.equals(""))) {
			modelAndView.addObject("selectedFixer", true);
			for (String s : fixersIdJsonArr) {
				fixerIdList.add(Integer.parseInt(s));
			}
		} else {
			modelAndView.addObject("selectedFixer", false);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		try {
			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			modelAndView.addObject("inProgressCount", inProgressCount);
			modelAndView.addObject("openRequestCount", unClaimedCount);
			modelAndView.addObject("fixedIssuesCount", fixedIssuesCount);
			long points = userCreditService.getPoints(userId);
			

			modelAndView.addObject("points", points);
			savedQuery = queryService.saveQuery(query, request.getSession().getServletContext().getRealPath("/"),
					userId);
			List<String> listCatName = new ArrayList<String>();
			List<Integer> categories = query.getCategories();

			for (Integer cat : categories) {
				CategoryType categoryType = categoryTypeDao.findCategoryTypeByCatId(cat);
				String catName = categoryType.getCatgName();
				listCatName.add(catName);
			}

			modelAndView.addObject("queryCategories", listCatName);

			List<String> filenames = query.getDocuments();
			List<String> fileList = new ArrayList<String>();
			if (filenames != null) {
				for (String fileName : filenames) {

					String awsFilePath = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX + DbConstants.DOCUMENTS_FOLDER
							+ DbConstants.SUFFIX + DbConstants.USER_FOLDER + userId + DbConstants.SUFFIX
							+ DbConstants.QUERY_FOLDER + savedQuery.getQueryId() + DbConstants.SUFFIX + fileName;

					fileList.add(awsFilePath);
				}
			}
			if (savedQuery != null) {
				queryFixersService.deleteQueryFixersBasedOnQueryId(savedQuery.getQueryId());
				selectedFixersSet = userService.findAllUserBasedOnIdList(fixerIdList, -1, -1);
				
				Set<User> removedfixers = userService.findNotIntrestedAndRemovedFixer(savedQuery.getQueryId());
				if(removedfixers!=null && removedfixers.size()>0)
				{
					for(User fixer:removedfixers)
					{
						for (Iterator<User> iterator = selectedFixersSet.iterator(); iterator.hasNext();) {
						    User selectedFixer =  iterator.next();
						    if (fixer.getUserId()== selectedFixer.getUserId()) {
						        iterator.remove();
						    }       
						}
					}
					
				}
				
				queryFixersService.saveQueryFixers(selectedFixersSet, savedQuery);
				boolean notSureCatFlag = false;
				List<Integer> catIds = query.getCategories();
				if (catIds.contains(DbConstants.NOT_SURE_CAT_ID)) {
					notSureCatFlag = true;
				}

				if (notSureCatFlag) {
					Set<User> users = new HashSet<User>();
					users.add(user);
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(users, user.getUserId(),
							savedQuery.getQueryId(), base);
					
					emailServcie.emailToMemberNotSureCat(savedQuery, queryExpireSet, base);
					
				} else {
					if (fixersIdJsonArr.length > 0 && !(fixersIdJson.equals("[]")) && selectedFixersSet != null
							&& selectedFixersSet.size() > 0) {

						Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(selectedFixersSet,
								user.getUserId(), savedQuery.getQueryId(), base);
						
						
						if(selectedFixersSet.size()>0)
						{
						emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(selectedFixersSet, savedQuery,
								queryExpireSet, base, fileList);
						}
						
					} else {
						Set<User> fixers = userService.findFixerWhoMatchQueryCat(savedQuery.getQueryId());

						Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
								savedQuery.getQueryId(), base);
						
						emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, savedQuery, queryExpireSet,
								base, fileList);
						
					}
					}
			}

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		modelAndView.addObject("myUser", user);

		if (query.getQueryMode().equals("EDIT")) {
			modelAndView.addObject("selectedQueryId", query.getQueryId());
			
			modelAndView.addObject("modeType", "EDIT");
			List<AppliedFixersListBo> appliedFixersListBo = new ArrayList<AppliedFixersListBo>();

			if (selectedFixersSet != null) {
				appliedFixersListBo = userService.setAppliedFixerList(selectedFixersSet, userId);

				modelAndView.addObject("selectedFixersSetLen", selectedFixersSet.size());
			} else {
				modelAndView.addObject("selectedFixersSetLen", 0);

			}

			modelAndView.addObject("appliedFixersListBo", appliedFixersListBo);

			

			modelAndView.setViewName("redirect:/member/request?msgKey=E");
		} else {
			
			modelAndView.addObject("modeType", "CREATE");
			List<AppliedFixersListBo> appliedFixersListBo = new ArrayList<AppliedFixersListBo>();
			if (selectedFixersSet != null) {
				appliedFixersListBo = userService.setAppliedFixerList(selectedFixersSet, userId);
				int selectedFixersSetLen = selectedFixersSet.size();
				modelAndView.addObject("selectedFixersSetLen", selectedFixersSetLen);

				
			} else {
				modelAndView.addObject("selectedFixersSetLen", 0);

			}
			try
			{
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			modelAndView.addObject("openRequestCount", unClaimedCount);
			}catch(FixitException e)
			{
				e.printStackTrace();
			}
			modelAndView.addObject("queryTitle", query.getQueryTitle());
			modelAndView.addObject("appliedFixersListBo", appliedFixersListBo);
			// modelAndView.setViewName("redirect:/member/request?msgKey=N");
			modelAndView.setViewName("postedRequest.jsp");
		}
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("currentPage", "dashboard");
		return modelAndView;
	}

	/**
	 * upload Document
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/uploadDocument", method = RequestMethod.POST)
	public ModelAndView uploadDocument(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String fileName) {
		ModelAndView modelAndView = new ModelAndView();
		
		return modelAndView;
	}

	

	@RequestMapping(value = "/customer/userDashboard", method = RequestMethod.GET)
	public ModelAndView userDashboard() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userDashboard.jsp");
		return mav;
	}

	@RequestMapping("/customer/getUserStats")
	@ResponseBody
	public Map<String, Object> getAllStats(HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("query", userService.getStats("C", userId));
			return Utility.getSuccessResponse("stats", map);
		} catch (Exception e) {
			return Utility.getFailureResponse("failed", "failed");
		}

	}

	@RequestMapping(value = "/customer/getCustomerQueries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCustomerQuestions(String type, int min, int max, HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			Set<QueryData> questionList = userService.getCustomerQueriesStuff(userId, type, min, max);
			Map<String, Object> map = new HashMap<>();
			map.put("customerQuery", questionList);
			return Utility.getSuccessResponse("CustomerQueries", map);
		} catch (Exception e) {
			return Utility.getFailureResponse("failed", "failed");
		}
	}

	@RequestMapping(value = "/member/profile", method = RequestMethod.GET)
	public ModelAndView createProfileGet(@ModelAttribute("memberProfile") MemberSignUpProfile profile,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		List<Integer> userIds = new LinkedList<Integer>();
		if (user != null) {
			userId = user.getUserId();
			userIds.add(userId);
		}
		try {

			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}

			mav.addObject("user", userService.findUserById(userId));
			mav.addObject("profile", profile);
			mav.addObject("profileCategories", objectMapper.writeValueAsString(profile.getCategories()));

			mav.addObject("industry", industryTypeService.findAllIndustryType());
			mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
			mav.addObject("industryJSON", objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mav.addObject("CreateOrEdit", "Edit Profile");
			mav.addObject("Step", "");
			/* The user is logged in :) */
			

		} else {
			mav.addObject("CreateOrEdit", "Create My  Profile");
			mav.addObject("Step", "STEP 2: Create Your Profile To Complete the Registration Process.");
			
		}
		mav.addObject("myUser", user);
		mav.setViewName("profile/memberProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/profile", method = RequestMethod.POST)
	public ModelAndView createProfilePost(@Valid @ModelAttribute("memberProfile") MemberSignUpProfile profile,
			BindingResult result, Model model, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// check current login user
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */
			mav.setViewName("redirect:/login");
		}

		User mySignUpUser = new User();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		memberStepTwoValidation.validate(profile, result);
		List<Integer> userIds = new LinkedList<Integer>();

		if (user != null) {
			userId = user.getUserId();
			profile.setUserId(userId);
			userIds.add(userId);
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

				mav.addObject("user", userService.findUserById(userId));
				// profile.setCategories(null);
				mav.addObject("profile", profile);
				mav.addObject("profileCategories", objectMapper.writeValueAsString(profile.getCategories()));
				mav.addObject("industry", industryTypeService.findAllIndustryType());
				mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
				mav.addObject("industryJSON",
						objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
				if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
					FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
				}
				mav.setViewName("profile/memberProfile.jsp");
				return mav;
			}
			mySignUpUser = userService.saveUser(profile);
			profile.setUserId(mySignUpUser.getUserId());
			userId = mySignUpUser.getUserId();
			if (mySignUpUser != null) {
				Long points = new Long(0);
				userCreditService.saveUserCredits(mySignUpUser, points);
				session.setAttribute("user", mySignUpUser);
			}
			userService.updateMemberSignUpProfile(profile, request.getSession().getServletContext().getRealPath("/"));
			
			session.setAttribute("user", null);
			if (FileUpload.isValidProfileImage(profile.getImageUrl())) {
				boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(profile.getImageUrl(), userId);
				if (isInMemberFolder) {
					FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
				}
			}
			if (FileUpload.isValidProfileImage(FileUpload
					.getmemberIconFileNameFromImageBasedOnUserName(profile.getImageUrl(), profile.getUserName()))) {
				boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(FileUpload
						.getmemberIconFileNameFromImageBasedOnUserName(profile.getImageUrl(), profile.getUserName()),
						userId);
				if (isInMemberFolder) {
					FileUpload.deleteFileFromProfileImageFolder(
							FileUpload.getmemberIconFileNameFromImageBasedOnUserName(profile.getImageUrl(),
									profile.getUserName()));
				}
			}
			userService.updateMemberSignUpProfile(profile, request.getSession().getServletContext().getRealPath("/"));

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}

		try {
			mav.addObject("user", userService.findUserById(userId));
			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));

			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
				
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
		} catch (FixitException e) {
			
		}

		mav.setViewName("redirect:/login?newMember");
		return mav;
	}

	// edit
	@RequestMapping(value = "/member/editProfile", method = RequestMethod.GET)
	public ModelAndView createEditProfileGet(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			MemberProfile profile = new MemberProfile();
			User myUser = userService.findUserById(userId);
			profile.setOverview(myUser.getOverview());
			profile.setTimeZone(myUser.getTimeZone());
			Calendar currentTime = Calendar.getInstance();
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			String memberImagePath = myUser.getProfilePhoto();
			mav.addObject("user", myUser);
			mav.addObject("memberImagePath", memberImagePath);
			mav.addObject("profile", profile);
			mav.addObject("industry", industryTypeService.findAllIndustryType());
			mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
			mav.addObject("industryJSON", objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mav.addObject("CreateOrEdit", "Edit Profile");
			mav.addObject("Step", "");
			/* The user is logged in :) */
			

		} else {
			mav.addObject("CreateOrEdit", "Create My  Profile");
			mav.addObject("Step", "STEP 2: Create Your Profile To Complete the Registration Process.");
			
		}
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "profile");
		mav.setViewName("profile/editMemberProfile.jsp");
		return mav;
	}

	

	@RequestMapping(value = "/member/myprofile", method = RequestMethod.GET)
	public ModelAndView myProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
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
			String memberImagePath = myUser.getProfilePhoto();
			mav.addObject("user", myUser);
			mav.addObject("memberImagePath", memberImagePath);
			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
			long allIssuesCount = queryService.findMemberQueryCount(userId);
			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			double fixedPercentage;
			if (allIssuesCount == 0) {
				fixedPercentage = 0.0;
			} else {
				fixedPercentage = (fixedIssuesCount * 100) / allIssuesCount;
			}
			double notFixedPercentage = 0.0;
			mav.addObject("remainingPoints", userCreditService.getPoints(userId));
			mav.addObject("fixedPercentage", fixedPercentage);
			mav.addObject("notFixedPercentage", notFixedPercentage);
			mav.addObject("fixedCount", queryService.findUserClosedStasCount(userId));
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		mav.addObject("myUser", user);
		mav.addObject("User_Type", "user");
		mav.addObject("currentPage", "profile");
		mav.setViewName("profile/myProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/request", method = RequestMethod.GET)
	public ModelAndView memberRequest(@RequestParam("msgKey") String msgKey,
			@RequestParam(value = "status", required = false, defaultValue = "Unclaimed") String status,
			@RequestParam(value = "selectedQueryId", required = false, defaultValue = "-1") String selectedQueryId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");

		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		List<Integer> userIds = new LinkedList<Integer>();
		userIds.add(userId);
		try {
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
			long points = userCreditService.getPoints(userId);
			long noOfCreditCounts = userCreditService.getCreditCounts(userId);

			if (noOfCreditCounts > 0) {
				if (points >= 0) {
					mav.addObject("points", points);
				} else {
					points = 0;
					mav.addObject("points", points);
				}
			} else if (noOfCreditCounts == 0) {

				if (points >= 0) {
					mav.addObject("points", points);
				} else {
					points = 0;
					mav.addObject("points", points);
				}
			} else {
				if (points >= 0) {
					mav.addObject("points", points);
				} else {
					points = 0;
					mav.addObject("points", points);
				}
			}
			mav.addObject("userCreditCount", noOfCreditCounts);
			long closedCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			mav.addObject("inProgressCount", inProgressCount);
			mav.addObject("openRequestCount", unClaimedCount);
			mav.addObject("fixedIssuesCount", closedCount);
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
			long pendingResolutionCount;
			pendingResolutionCount = queryService.findUserPendingResolutionStasCount(userId);
			mav.addObject("awaitingConfirmationCount", pendingResolutionCount);
			QueryBo query = new QueryBo();
			mav.addObject("query", query);
			switch (status) {
			case "Unclaimed":

				
				totalPage = (int) Math.ceil((float) unClaimedCount / DbConstants.PAGE_SIZE);
				mav.addObject("unClaimedCount", objectMapper.writeValueAsString(unClaimedCount));
				mav.addObject("queryCount", unClaimedCount);
				Set<MemberDashboardBo> queryList = userService.findMemberQueryNotAccepted(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("queryList", queryList);
				mav.addObject("queryCounts", queryList.size());
				mav.addObject("inProgressNotification",
						queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("selectedQueryId", selectedQueryId);
				mav.addObject("countryList", (countryService.findAllCountry()));
				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				mav.addObject("parentCategoryJson", objectMapper
						.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
				mav.addObject("allCategory", (categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.addObject("allCategoryJson", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.addObject("myUserJson", objectMapper.writeValueAsString(user));
				mav.setViewName("dashboard/open_request_member.jsp");
				break;

			case "InProgress":

				
				totalPage = (int) Math.ceil((float) inProgressCount / DbConstants.PAGE_SIZE);
				mav.addObject("inProgressCount", objectMapper.writeValueAsString(inProgressCount));
				mav.addObject("queryCount", inProgressCount);
				Set<MemberDashboardBo> memberList = userService.findMemberQueryWorking(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("queryList", memberList);
				mav.addObject("queryCounts", memberList.size());
				mav.addObject("inProgressNotification",
						queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING));

				mav.addObject("selectedQueryId", selectedQueryId);
				mav.addObject("countryList", (countryService.findAllCountry()));

				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				mav.addObject("allCategory", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.setViewName("dashboard/inprogressMember.jsp");
				break;

			case "Closed":
				
				totalPage = (int) Math.ceil((float) closedCount / DbConstants.PAGE_SIZE);
				mav.addObject("closedCount", objectMapper.writeValueAsString(closedCount));
				mav.addObject("queryCount", closedCount);
				Set<MemberDashboardBo> fixerList = userService.findMemberQueryClosed(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("queryCounts", fixerList.size());
				mav.addObject("queryList", fixerList);
				mav.addObject("inProgressNotification",
						queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("selectedQueryId", selectedQueryId);
				mav.addObject("countryList", (countryService.findAllCountry()));

				mav.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				mav.addObject("allCategory", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				mav.setViewName("dashboard/memberClosedRequests.jsp");
				break;

			case "Pending":
				pendingResolutionCount = queryService.findUserPendingResolutionStasCount(userId);
				totalPage = (int) Math.ceil((float) pendingResolutionCount / DbConstants.PAGE_SIZE);
				mav.addObject("queryCount", pendingResolutionCount);
				mav.addObject("inProgressNotification",
						queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING));
				mav.addObject("queryList", userService.findMemberQueryPendingResolution(userId, currentTime,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE));
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

			case "N":
				message = "Your request has been saved successfully.";
				msgType = "success";
				break;

			case "AP":
				message = "Fixer has been approved successfully.";
				msgType = "success";
				break;

			case "E":
				message = "Your request has been updated successfully.";
				msgType = "success";
				break;

			case "D":
				message = "Your request has been deleted successfully.";
				msgType = "success";
				break;

			case "C":
				message = "Your request has been closed successfully.";
				msgType = "success";
				break;

			case "RT":
				message = "Fixer has been rated successfully.";
				msgType = "success";
				break;

			case "UN":
				message = "Your request has been marked as “Not Fixed”.";
				msgType = "success";
				break;

			case "FR":
				message = "Your request has been reopened successfully.";
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
			}

			mav.addObject("message", message);
			mav.addObject("msgType", msgType);

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}

		mav.addObject("currentPage", status);
		mav.addObject("status", status);
		mav.addObject("myUser", user);

		return mav;
	}

	@RequestMapping(value = "/member/request", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberDashboardAjax(@RequestParam int startIndex, @RequestParam int maxResult,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<MemberDashboardBo> unclaimed = null;
		Set<MemberDashboardBo> inProgress = null;
		Set<MemberDashboardBo> closed = null;
		Set<MemberDashboardBo> awaitingConfirmation = null;
		try {
			Calendar currentTime = Calendar.getInstance();
			unclaimed = userService.findMemberQueryNotAccepted(userId, currentTime, "", startIndex, maxResult);
			inProgress = userService.findMemberQueryWorking(userId, currentTime, "", startIndex, maxResult);
			closed = userService.findMemberQueryClosed(userId, currentTime, "", startIndex, maxResult);

			awaitingConfirmation = userService.findMemberQueryPendingResolution(userId, currentTime, user.getTimeZone(),
					0, DbConstants.PAGE_SIZE);

		} catch (FixitException e) {
			return Utility.getFailureResponse();
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse("unclaimed", unclaimed, "inProgress", inProgress, "closed", closed,
				"awaitingConfirmation", awaitingConfirmation);
	}

	@ResponseBody
	@RequestMapping(value = "/member/changeReqStatus", method = RequestMethod.POST)
	public Map<String, Object> changeReqStatusAjax(@RequestParam Integer queryId, @RequestParam String currentStatus,
			HttpSession session) {
		try{
		Query query = queryService.findQueryByQueryId(queryId);
		query.setCurrentStatus(currentStatus);
		query = queryService.updateQueryCurrentStatus(query);
		if(query.getCurrentStatus().equals(currentStatus))
		{
			return Utility.getSuccessResponse("status", "OK");
		}
		else
		{
			return Utility.getFailureResponse("Unable to update status",null);
		}
		}catch(FixitException e)
		{
			e.printStackTrace();
			return Utility.getFailureResponse(e.getMessage(),null);
		}
		
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/member/interestedFixers", method = RequestMethod.POST)
	public Map<String, Object> interestedFixersAjax(@RequestParam Integer queryId, @RequestParam Integer userId,
			HttpSession session) {
		List<AppliedFixersListBo> appliedFixersList = new ArrayList<AppliedFixersListBo>();
		MemberDashboardBo memberDashboard = new MemberDashboardBo();
		Set<Country> countrySet = null;
		try {
			countrySet = countryService.findAllCountry();
		} catch (FixitException e1) {
			
			e1.printStackTrace();
		}
		User user = (User) session.getAttribute("user");
		try {
			appliedFixersList = queryAppliedFixersService.findFixerListByQueryId(userId, queryId, -1, -1);
			Query query = queryService.findQueryByQueryId(queryId);

			long days, min, hrs, sec;

			Set<QueryFiles> queryFiles;
			Set<QueryFilesBo> queryFilesBo;
			Set<QueryCategory> queryCategories;
			Set<String> subCategory = null;
			QueryFiles queryFile;
			QueryFilesBo queryFileBo;
			Iterator<QueryFiles> iteratorQueryFiles;

			subCategory = new HashSet<String>();
			queryFilesBo = new LinkedHashSet<QueryFilesBo>();
			queryCategories = queryCategoryDao.findQueryCategoryByQueryId(query.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				subCategory.add(categoryTypeDao.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId())
						.getCatgName());
			}
			memberDashboard.setSubCategory(subCategory);
			memberDashboard.setQueryId(query.getQueryId());
			memberDashboard.setQueryCredits(query.getQueryCredits());
			memberDashboard.setQueryContent(query.getQueryContent());
			memberDashboard.setQueryTitle(query.getQueryTitle());
			memberDashboard.setHashcode(query.getHashcode());
			memberDashboard.setCurrentStatus(query.getCurrentStatus());
			
			if (query.getQueryDeadlineDate() != null)
				memberDashboard
						.setQueryDeadlineDate(TimeDiffUtility.timeToSpecificTimezoneWithChangeFormatWithTimeZoneId(
								query.getQueryDeadlineDate(), user.getTimeZone(), "MM/dd/yyyy HH:mm:ss"));

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
				queryFileBo.setDocUploadDate(
						TimeDiffUtility.timeToSpecificTimezone(queryFile.getCreatedAt(), user.getTimeZone()));
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
			else
				memberDashboard.setInterestedFixersCount(query.getQueryAppliedFixers().size());
			Calendar currentTime = Calendar.getInstance();
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
						if (sec == 1) {
							memberDashboard.setTimeDiff(sec + " second");

						} else {
							memberDashboard.setTimeDiff(sec + " seconds");

						}
						
					}
				}
			}

		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		return Utility.getSuccessResponse("fixersList", appliedFixersList, "queryId", queryId, "queryData",
				memberDashboard, "countryList", countrySet, "myCountry", user.getCountry());
		
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView fixerDecline(@RequestParam("queryId") String queryId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Query query;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {

			query = queryService.getQueryByHashCode(queryId);
			if (DbConstants.STATUS_WORKING.toString().equals(query.getCurrentStatus())) {
				modelAndView.setViewName("redirect:/member/request?msgKey=AC");
				return modelAndView;
			}
			queryFixersService.deleteQueryFixersBasedOnQueryId(query.getQueryId());
			queryService.deleteQueryByQueryId(userId, query.getQueryId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("redirect:/member/request?msgKey=D&status=Unclaimed");
		return modelAndView;
	}

	@RequestMapping(value = "/member/memberQueryDetailPage", method = RequestMethod.GET)
	public ModelAndView queryDetailPage(@RequestParam String queryId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		if (user != null) {
			userId = user.getUserId();
			mav.addObject("myUser", user);
		}
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
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
				fixerId = queryAudit.getFixerId();
				mav.addObject("queryTitle", queryAudit.getQuery().getQueryTitle());
				mav.addObject("queryContent", queryAudit.getQuery().getQueryContent());
			}

		}
		User fixer = userService.findUserById(fixerId);
		if (fixer != null) {

			mav.addObject("fixerName", fixer.getFirstName());
			mav.addObject("fixerImgIcon", fixer.getProfileIcon());
		} else {
			Query query;
			try {
				query = queryService.getQueryByHashCode(queryId);
				if (query != null) {
					mav.addObject("fixerName", query.getUser().getUserName());
					mav.addObject("fixerImgIcon", query.getUser().getProfileIcon());
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}

		}
		mav.addObject("queryId", queryId);
		mav.addObject("fixerImgIcon", fixer.getProfileIcon());
		mav.addObject("messagesSet", messagesSet);
		mav.addObject("currentPage", "dashboard");
		mav.setViewName("dashboard/memberQueryDetailPage.jsp");

		return mav;
	}

	@RequestMapping(value = "/member/memberQuestionDetailPage", method = RequestMethod.GET)
	public ModelAndView fixerQuestionDetailPage(@RequestParam String questionId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		MemberDashboardBo memberDashboardBo = null;
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(questionId);
			memberDashboardBo = queryService.findQueryDetail(query, Calendar.getInstance(), user.getTimeZone());
		} catch (FixitException e) {
		}

		mav.addObject("queryDetail", memberDashboardBo);
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "dashboard");

		mav.setViewName("dashboard/memberQuestionDetailPage.jsp");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/member/sendMessage", method = RequestMethod.POST)
	public Map<String, Object> userSendMessage(@RequestParam String queryId, @RequestParam int customerId,
			@RequestParam int fixerId, @RequestParam String msgFrom, @RequestParam String message, HttpSession session,
			HttpServletRequest request) {

		User user = (User) session.getAttribute("user");
		QueryAudit queryAudit = null;
		QueryAuditBo queryAudiotBo = new QueryAuditBo();
		queryAudiotBo.setCustomerId(customerId);
		queryAudiotBo.setFixerId(fixerId);
		Query query = null;
		try {
			query = queryService.getQueryByHashCode(queryId);
			queryAudiotBo.setQueryId(query.getQueryId());
			queryAudiotBo.setMsgFrom(msgFrom);
			queryAudiotBo.setStatus("W");
			queryAudiotBo.setMessage(message);
			queryAudiotBo.setReadStatus("UR");

			queryAudit = queryAuditService.saveQueryDetailMessage(queryAudiotBo);
			StringBuffer url = request.getRequestURL();
			
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			
			emailServcie.emailToFixerChatMessage(queryAudit, user, base);

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

		} catch (FixitException e) {
			
			e.printStackTrace();
			return Utility.getSuccessResponse("response", "failed");
		}

	}

	@RequestMapping(value = "/member/sendMessageReview", method = RequestMethod.POST)
	public ModelAndView userSendMessageReview(@RequestParam String queryId, @RequestParam int customerId,
			@RequestParam int fixerId, @RequestParam String msgFrom, @RequestParam String message,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
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
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
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
		mav.setViewName("dashboard/memberPendingResolutionPage.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/closedQueryPage", method = RequestMethod.GET)
	public ModelAndView closedQueryDetailPage(@RequestParam String queryId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
		;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		Query query = null;
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(queryId);
			messagesSet = queryAuditService.getQueryDetailMessages(queryId, -1, -1);
			for (QueryAudit audit : messagesSet) {
				audit.setAuditTime(TimeDiffUtility.timeToSpecificTimezone(audit.getAuditDate(), user.getTimeZone()));
			}
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		

		if (messagesSet.size() > 0) {
			Iterator<QueryAudit> iterator = messagesSet.iterator();
			if (iterator.hasNext()) {
				QueryAudit queryAudit = iterator.next();
				fixerId = queryAudit.getFixerId();
				mav.addObject("queryTitle", queryAudit.getQuery().getQueryTitle());
				mav.addObject("queryContent", queryAudit.getQuery().getQueryContent());
			}

		}
		User fixer = userService.findUserById(fixerId);
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
		mav.addObject("fixerName", fixer.getFirstName());
		mav.addObject("memberName", user.getFirstName());
		mav.addObject("queryId", queryId);
		mav.addObject("query", query);
		mav.addObject("messagesSet", messagesSet);
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

		if (query != null) {
			try {
				FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
						query.getFixerId(), query.getUser().getUserId());
				if (fixerRating != null) {
					if (fixerRating.getStarRating() <= 0) {
						long end = Calendar.getInstance().getTimeInMillis();
						long start = fixerRating.getCreatedAt().getTimeInMillis();
						long differenceDay = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
						if (differenceDay < 30) {
							mav.addObject("RateIt", "PleaseRate");
						}
					} else {
						mav.addObject("RateIt", null);
					}
				} else {
					mav.addObject("RateIt", null);
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
		}
		mav.addObject("currentPage", "dashboard");
		mav.addObject("fixerImgIcon", fixer.getProfileIcon());
		mav.addObject("myUser", user);
		if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
			mav.addObject("Conflict", "No");
			mav.setViewName("dashboard/closedMemberQueryDetailPage.jsp");

		} else if (DbConstants.STATUS_REVIEW.equals(query.getCurrentStatus())) {
			mav.addObject("Conflict", "Yes");
			mav.setViewName("dashboard/memberPendingResolutionPage.jsp");

		} else if (DbConstants.STATUS_UNRESOLVED_INACTIVITY.equals(query.getCurrentStatus())) {

			mav.addObject("Conflict", "NotFixed");
			mav.setViewName("dashboard/closedMemberQueryDetailPage.jsp");
		} else if (DbConstants.STATUS_UNRESOLVED_NOTFIXED.equals(query.getCurrentStatus())) {

			mav.addObject("Conflict", "NotFixed");
			mav.setViewName("dashboard/closedMemberQueryDetailPage.jsp");
		} else {
			mav.setViewName("dashboard/closedMemberQueryDetailPage.jsp");
		}
		/* mav.setViewName("dashboard/closedMemberQueryDetailPage.jsp"); */
		return mav;
	}

	@RequestMapping(value = "/member/fixed", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryFixedByMember(@RequestParam String queryId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			Query query = queryService.getQueryByHashCode(queryId);
			query = queryService.fixedQueryByQueryId(query.getQueryId());
			if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
				double doubleStartPoints = 0;
				String review = "";
				if (query != null) {
					Integer fixerId = query.getFixerId();
					User fixer = userService.findUserById(fixerId);
					FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
							query.getFixerId(), userId);
					if (fixerRating != null) {
						fixerRatingService.upDateRatingBasedOnIds(query.getQueryId(), query.getFixerId(), userId,
								doubleStartPoints, review);
					} else {
						fixerRatingService.saveFixerRatings(query, fixer, userId, doubleStartPoints, review);
					}

					FixerAccounting fixerAccounting = new FixerAccounting();
					fixerAccounting.setQuery(query);
					fixerAccounting.setUser(fixer);
					fixerAccounting.setStatus(0);
					if (query.getQueryCredits() == null) {
						fixerAccounting.setAmountPaid(0d);

					} else {
						fixerAccounting.setAmountPaid((query.getQueryCredits()) * FixitVariables.CREDIT_AMOUNT);
					}
					fixerAccountingService.saveFixerAccounting(fixerAccounting);

					try {
						emailServcie.emailToFixerRequestFixed(fixer, query);
					} catch (Exception e) {
						
					}

				}
			}
			return Utility.getSuccessResponse("result", "success");
			

		} catch (Exception e) {
			
			return Utility.getFailureResponse("result", "failure");
		}
	}

	@RequestMapping(value = "/member/notfixed", method = RequestMethod.POST)
	public ModelAndView queryNotFixedByMember(@RequestParam String queryId, @RequestParam String message,
			HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		Query query = null;
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			query = queryService.getQueryByHashCode(queryId);
			queryService.notFixedQuerybyQueryId(query.getQueryId(), message);
		
			try {
				User fixer = userService.findUserById(query.getFixerId());
				Set<User> fixers = new HashSet<User>();
				fixers.add(fixer);
				StringBuffer url = request.getRequestURL();
				String uri = request.getRequestURI();
				String ctx = request.getContextPath();
				String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
				Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
						query.getQueryId(), base);
				try {
					emailServcie.emailToFixerForNotFixed(fixer, query, queryExpireSet, base);
				} catch (Exception e) {
					
				}

				
			} catch (Exception e) {

			}

		} catch (Exception e) {
			
		}
		mav.setViewName("redirect:/member/request?msgKey=UN&status=InProgress");
		return mav;
	}

	@RequestMapping(value = "/member/memberQueryDetailPage/rejectFixer", method = RequestMethod.GET)
	public ModelAndView rejectFixer(@RequestParam String queryId, HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			Query query = queryService.getQueryByHashCode(queryId);
			User fixer = userService.findUserById(query.getFixerId());
			query = queryService.rejectFixer(query.getQueryId());
			try {
				emailServcie.emailToFixerRequestRejected(fixer, query);

				Set<User> fixers = userService.findFixerWhoMatchQueryCat(query.getQueryId());

				fixers.remove(fixer);
				StringBuffer url = request.getRequestURL();
				String uri = request.getRequestURI();
				String ctx = request.getContextPath();
				String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
				Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
						query.getQueryId(), base);

				emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, query, queryExpireSet, base, null);
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			
		}
		mav.setViewName("redirect:/member/request?msgKey=FR&status=InProgress");
		return mav;
	}

	@RequestMapping(value = "/member/memberSettings", method = RequestMethod.GET)
	public ModelAndView memberSettings(HttpSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		mav.addObject("currentPage", "settings");
		mav.addObject("myUser", user);
		mav.addObject("member", user);
		mav.addObject("memberAlert", user);
		mav.addObject("timeZoneList", TimeZoneList.timeZoneList());

		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
		} catch (JsonProcessingException | FixitException e) {
			
		}
		mav.addObject("userUpdate", null);
		NewPassword newPassword = new NewPassword();
		mav.addObject("newPassword", newPassword);
		mav.setViewName("settings/memberSettings.jsp");
		return mav;
	}

	

	@RequestMapping(value = "/member/updateMemberAlert", method = RequestMethod.POST)
	public ModelAndView updateMemberAlert(@ModelAttribute("memberAlert") UserBo userBo, BindingResult result,
			Model model, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBo member = new UserBo();
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
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
		mav.setViewName("settings/memberSettings.jsp");

		return mav;
	}

	

	@RequestMapping(value = "/member/createFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createFolder(@RequestParam MultipartFile file, @RequestParam Integer userId) {
		String originalFileName = null;
		String fileUniqueCode = null;
		try {
			fileUniqueCode = "D" + TimeDiffUtility.getUniqueString();
			
			originalFileName = FileUpload.uploadDocuments(file, userId);

		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse("originalFileName", originalFileName, "fileUniqueCode", fileUniqueCode);

	}

	@RequestMapping(value = "/member/createDocFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createDocFolder(@RequestParam MultipartFile file, @RequestParam Integer userId,
			@RequestParam Integer queryId) {
		String originalFileName = null;
		String fileUniqueCode = null;
		String filePath = null;
		String awsFilePath = null;
		try {
			fileUniqueCode = "D" + TimeDiffUtility.getUniqueString();
			
			originalFileName = FileUpload.uploadDocuments(file, userId);
			filePath = DbConstants.DOCUMENTS_FOLDER + DbConstants.SUFFIX + DbConstants.USER_FOLDER + userId
					+ DbConstants.SUFFIX + originalFileName;
			awsFilePath = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX + DbConstants.DOCUMENTS_FOLDER
					+ DbConstants.SUFFIX + DbConstants.USER_FOLDER + userId + DbConstants.SUFFIX + originalFileName;

		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse("originalFileName", originalFileName, "fileUniqueCode", fileUniqueCode,
				"queryId", queryId, "fileUrl", filePath, "awsFileUrl", awsFilePath);

	}

	@RequestMapping(value = "/member/deleteFileCreate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFileCreate(@RequestParam String fileName, @RequestParam Integer userId) {
		try {
			FileUpload.deleteFileFromUserFolder(fileName, userId);
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();

	}

	@RequestMapping(value = "/member/deleteFileEdit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFileEdit(@RequestParam String fileName, @RequestParam Integer userId,
			@RequestParam Integer queryId, @RequestParam String fileUniqueCode) {
		try {
			FileUpload.deleteFileFromQueryFolder(fileName, userId, queryId);
			queryService.deleteQueryFilesByFileUniqueCode(queryId, fileUniqueCode);
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();

	}

	@RequestMapping(value = "/member/deleteFileChatEdit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFileMemberEdit(@RequestParam String fileName, @RequestParam String queryId,
			HttpSession session) {
		try {
			Query query = queryService.getQueryByHashCode(queryId);
			FileUpload.deleteFileFromQueryFolder(fileName, query.getUser().getUserId(), query.getQueryId());
		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility.getSuccessResponse();

	}

	@RequestMapping(value = "/member/uploadChatDocDetailpage", method = RequestMethod.POST)
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

			if (virtualFileName != null) {
				if (virtualFileName.contains("DOCTYPE html")) {
					virtualFileName = file.getOriginalFilename();
				}
				Query query = queryService.getQueryByHashCode(queryId);

				QueryFiles queryFiles = new QueryFiles();
				queryFiles.setQuery(query);
				queryFiles.setFileName(virtualFileName);
				queryFiles.setFileType(DbConstants.DOCUMENT);
				String docUrl = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX + DbConstants.DOCUMENTS_FOLDER
						+ DbConstants.SUFFIX + DbConstants.USER_FOLDER + query.getUser().getUserId()
						+ DbConstants.SUFFIX + DbConstants.QUERY_FOLDER + query.getQueryId() + DbConstants.SUFFIX
						+ virtualFileName;
				queryFiles.setFileUrl(docUrl);
				queryFilesDao.store(queryFiles);

				QueryAudit audit = new QueryAudit();
				audit.setFixerId(fixerId);
				audit.setCustomerId(query.getUser().getUserId());
				audit.setMessage(docUrl);
				audit.setMsgFrom(DbConstants.CUSTOMER);
				audit.setQuery(query);
				audit.setStatus(DbConstants.STATUS_WORKING_DOCUMENT);
				Calendar currentTime = Calendar.getInstance();
				audit.setAuditDate(currentTime);
				queryAuditService.saveQueryAudit(audit);
				StringBuffer url = request.getRequestURL();
				String uri = request.getRequestURI();
				String ctx = request.getContextPath();
				String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
				emailServcie.emailToFixerChatAttachmentMessage(audit, user, base, file);
				return docUrl;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}

	}

	@RequestMapping(value = "/member/uploadDocDetailpage", method = RequestMethod.POST)
	@ResponseBody
	public String uploadDocDetailpage(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String queryId, HttpSession session, HttpServletRequest request) {
		try {
			// FileUpload.deleteFileFromQueryFolder(fileName, userId, queryId);
			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}
			Query query = queryService.getQueryByHashCode(queryId);
			String virtualFileName = FileUpload.uploadDocumentQueryFolder(file, userId, query.getQueryId());

			
			return virtualFileName;
		} catch (Exception e) {
			return "";
		}

	}

	@RequestMapping(value = "/memberProfileImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadMemberProfileImage(@RequestParam MultipartFile multipartFile,
			@RequestParam String userName) {
		String originalFileName = null;

		try {
			
			originalFileName = FileUpload.uploadMemberProfileImageUsingUserName(multipartFile, userName);

		} catch (Exception e) {
			return Utility.getFailureResponse();
		}
		return Utility
				.getSuccessResponse(
						"originalFullFileName", DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX
								+ DbConstants.PROFILE_FOLDER + DbConstants.SUFFIX + originalFileName,
						"originalFileName", originalFileName);

	}

	@RequestMapping(value = "/member/download", method = RequestMethod.GET)
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

	@RequestMapping(value = "member/paypal", method = RequestMethod.GET)
	public ModelAndView paypalPayment(@RequestParam String amount, @RequestParam String userUrl ,HttpServletRequest request, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			User user = (User) session.getAttribute("user");
			int userId = -1;
			if (user != null) {
				userId = user.getUserId();
			}
			
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			
			String href = payPalSinglePaymentService.getAccessTokenForPayment(amount, base, user,userUrl);
//			 mav.setViewName("redirect:https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&xo_node_fallback=true&force_sa=true&fallback=1&token="+
//			 href);
			mav.setViewName(
					"redirect:https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&xo_node_fallback=true&force_sa=true&fallback=1&token="
							+ href);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "member/finalPayment", method = RequestMethod.GET)
	public ModelAndView paypalFinalPayment(@RequestParam String token, @RequestParam String PayerID, @RequestParam String userUrl,
			HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		String amount = payPalSinglePaymentService.getExpressCheckoutDetails(token);
		String status = payPalSinglePaymentService.DoExpressCheckoutPayment(token, PayerID, amount);
		boolean payStatus=false;
		
		if (status.equals("Success")) {
			payStatus = true;
			UsersAccountingBo usersAccountingBo = new UsersAccountingBo();
			usersAccountingBo.setUser(user);
			usersAccountingBo.setType("T");

			double money = Double.parseDouble(amount);
			String credit;
			usersAccountingBo.setAmount(money);
			try {
				UsersAccounting usersAccounting = usersAccountingService.saveUserAccounting(usersAccountingBo);
				if (usersAccounting != null) {
					if (amount.equals("575.00")) {
						if ("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())) {
							credit = "6";
						} else {
							credit = "5";
						}

					} else if (amount.equals("1125.00")) {
						if ("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())) {
							credit = "11";
						} else {
							credit = "10";
						}
					} else if (amount.equals("2200.00")) {
						if ("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())) {
							credit = "21";
						} else {
							credit = "20";
						}
					} else if (amount.equals("4300.00")) {
						if ("1".equals(user.getSource()) && "N".equals(user.getTrackCredit())) {
							credit = "41";
						} else {
							credit = "40";
						}
					} else {
						double myMoney = Double.parseDouble(amount);
						myMoney = myMoney / 5;
						credit = "" + myMoney;
					}
					long pointIfUserExisted = userCreditService.getPoints(user.getUserId());
					if (pointIfUserExisted > 0) {
						pointIfUserExisted = pointIfUserExisted + Long.parseLong(credit);
						userCreditService.updatePoints(user.getUserId(), pointIfUserExisted);
					} else {
						userCreditService.updatePoints(user.getUserId(), Long.parseLong(credit));
					}
					user.setTrackCredit("Y");
					user = userDao.store(user);
					session.setAttribute("user", user);
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			
		} else {
			
		}
		 userUrl = userUrl.replaceAll(",", "&");
		 userUrl = userUrl.replaceFirst("&", "?");

		 userUrl = userUrl.replaceAll("\"", "");

		mav.setViewName("redirect:/member/" + userUrl+"&payStatus="+payStatus);
		return mav;
	}

	@RequestMapping(value = "member/recurPayment", method = RequestMethod.GET)
	public ModelAndView paypalrecuringPayment(HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		ModelAndView mav = new ModelAndView();
		try {
			payPalIntegrationService.paymentRecurringUsingCreditCard("10");
			mav.setViewName("redirect:/member/request?msgKey=PS");
		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = "member/fixerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchFixersAJAX(@RequestParam String searchFieldText,
			@RequestParam(value = "categoryIds[]") List<Integer> categoryIds, @RequestParam String country,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "editQueryId", required = false, defaultValue = "-1") Integer editQueryId,HttpSession session)
					throws Exception {
		User user = (User) session.getAttribute("user");
		List<FixerSearchBo> fixers = null;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		int offSet = ((pageNo - 1) * DbConstants.PAGE_SIZE_SEARCHED_FIXERS);
		try {
			
			fixers = userService.findFixerByFilter(searchFieldText, userId, categoryIds, country, offSet,
					DbConstants.PAGE_SIZE_SEARCHED_FIXERS, editQueryId);
			
			
		} catch (Exception e) {

		}
		return Utility.getSuccessResponse("fixers", fixers, "currentPage", pageNo + 1);
		
	}

	@RequestMapping(value = "member/fixerFavourite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> markFavourateFixer(@RequestParam String fixerId, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		Set<FixerSearchBo> fixers = null;
		List<Integer> categories = new ArrayList<Integer>();
		Integer categoryId = -1;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			int fixerintId = Integer.parseInt(fixerId);

			favouriteFixerService.saveFavourateFixer(userId, fixerintId);
		} catch (Exception e) {

		}
		return Utility.getSuccessResponse("response", "success");
	}

	@RequestMapping(value = "member/favouriteFixerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> favourateFixerListAjax(@RequestParam String fixerId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag, HttpSession session)
					throws Exception {
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;
		User user = (User) session.getAttribute("user");
		Set<FixerSearchBo> fixers = null;
		List<Integer> categories = new ArrayList<Integer>();
		Integer categoryId = -1;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			fixers = userService.getFavouriteFixersList(userId, -1, -1);

			try {
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
				double totalSizeOfFixerList = fixers.size();
				totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.PAGE_SIZE);
				fixers = null;
				fixers = userService.getFavouriteFixersList(userId, startIndex, DbConstants.PAGE_SIZE);
				switch (flag) {
				case "left":
					if (pageNo % DbConstants.PAGE_NO_DISPLAY == 1) {
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1)
								* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
							endPage = totalPage;
						} else {
							endPage = (((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1) * DbConstants.PAGE_NO_DISPLAY;
							
						}

					} else {
						if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1))
									+ 1;
							
							if (((((pageNo / DbConstants.PAGE_NO_DISPLAY)))
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = (((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY;
								
							}

						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							
							if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1))
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = (((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)) * DbConstants.PAGE_NO_DISPLAY;
								
							}

						}
					}
					
					currentPageNo = pageNo - 1;
					break;

				case "right":
					if (pageNo > 0) {

						if (pageNo + DbConstants.PAGE_NO_DISPLAY > totalPage) {
							if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
								startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY)))
										+ 1;
								endPage = totalPage;

								
							} else {
								startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
								
								if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
										* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
									
									endPage = totalPage;
								} else {
									endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
											* DbConstants.PAGE_NO_DISPLAY;
									
								}

							}
						} else {
							if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
								startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
							} else {
								startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
							}
						}
						
						currentPageNo = pageNo + 1;

					} else {
						
						startPage = 1;
						if (DbConstants.PAGE_NO_DISPLAY <= totalPage) {
							
							endPage = DbConstants.PAGE_NO_DISPLAY;
						} else {
							endPage = totalPage;
							
						}
						currentPageNo = 1;
						

					}

					break;

				case "current":
					if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if ((((pageNo / DbConstants.PAGE_NO_DISPLAY)) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
							endPage = totalPage;
						} else {
							endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY)) * DbConstants.PAGE_NO_DISPLAY;
							
						}

					} else {
						startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
						
						if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
							endPage = totalPage;
						} else {
							endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
							
						}

					}
					currentPageNo = pageNo;
					
					break;

				default:
					break;
				}

			} catch (Exception e) {

			}

		} catch (Exception e) {

		}
		return Utility.getSuccessResponse("fixers", fixers, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);
	}

	@RequestMapping(value = "member/deleteFavouriteFixer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFavouriteFixer(@RequestParam String fixerId, HttpSession session)
			throws Exception {
		User user = (User) session.getAttribute("user");
		Set<FixerSearchBo> fixers = null;
		List<Integer> categories = new ArrayList<Integer>();
		Integer categoryId = -1;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			int fixerIntId = Integer.parseInt(fixerId);
			favouriteFixerService.deleteFavouriteFixer(userId, fixerIntId);
			return Utility.getSuccessResponse("success", "success");

		} catch (Exception e) {
			return Utility.getSuccessResponse("failure", "failure");
		}

	}

	@RequestMapping(value = "/member/fixerProfile", method = RequestMethod.POST)
	public ModelAndView myProfile(@RequestParam String memberFixersId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		int fixerId = -1;
		try {
			fixerId = Integer.parseInt(memberFixersId);
		} catch (Exception e) {

		}
		ModelAndView mav = new ModelAndView();
		try {
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(userIds);
			if (chatUserGroup != null) {
				mav.addObject("groupId", chatUserGroup.getChatGroups().getChatGroupId());
				mav.addObject("isGroup", "Yes");
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			mav.addObject("user", userService.findUserById(fixerId));
			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(fixerId)));
			mav.addObject("category", categoryTypeService
					.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(fixerId)));

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		
		mav.setViewName("profile/myFixerProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/rate", method = RequestMethod.POST)
	public ModelAndView rateByMember(@RequestParam String queryId,
			@RequestParam(required = false, defaultValue = "EMPTY") String rateFixerFixed,
			@RequestParam(required = false, defaultValue = "") String review, @RequestParam String starPoints,
			HttpSession session, HttpServletRequest request) {
		// reminder:the mail will pe send to fixer in case of not fixed
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		Query query = null;
		if (user != null) {
			userId = user.getUserId();
		}
		try {

			double doubleStartPoints = Double.parseDouble(starPoints);
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				fixerId = query.getFixerId();
				User fixer = userService.findUserById(fixerId);

				FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
						query.getFixerId(), userId);
				if (fixerRating != null) {
					fixerRatingService.upDateRatingBasedOnIds(query.getQueryId(), query.getFixerId(), userId,
							doubleStartPoints, review);
				} else
					fixerRatingService.saveFixerRatings(query, fixer, userId, doubleStartPoints, review);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rateFixerFixed.equals("rateFixerFixed")) {

			mav.setViewName("redirect:/member/request?msgKey=RT&status=Closed");

		} else {
			mav.setViewName("redirect:/member/request?msgKey=C&status=Closed");
		}
		return mav;
	}

	@RequestMapping(value = "/member/rateClosed", method = RequestMethod.POST)
	public ModelAndView rateClosedByMember(@RequestParam String queryId, @RequestParam String starPoints,
			HttpSession session, HttpServletRequest request) {
		// reminder:the mail will pe send to fixer in case of not fixed
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		Query query = null;
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			double doubleStartPoints = Double.parseDouble(starPoints);
			String review = "";
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				fixerId = query.getFixerId();
				User fixer = userService.findUserById(fixerId);

				FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
						query.getFixerId(), userId);
				if (fixerRating != null) {
					fixerRatingService.upDateRatingBasedOnIds(query.getQueryId(), query.getFixerId(), userId,
							doubleStartPoints, review);
				}
			}
		} catch (Exception e) {

		}
		mav.setViewName("redirect:/member/closedQueryPage?queryId=" + query.getHashcode() + "&timeZone=");
		return mav;
	}

	@RequestMapping(value = "/member/rateLater", method = RequestMethod.GET)
	public ModelAndView rateByMemberLater(@RequestParam String queryId, HttpSession session,
			HttpServletRequest request) {
		// reminder:the mail will pe send to fixer in case of not fixed
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		Integer fixerId = -1;
		Query query = null;
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
				long countMsg = 0;
				try {
					countMsg = messageNotificationService.getAllNotificationBasedOnId(userId);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				mav.addObject("countMsg", countMsg);
			} else {
				mav.addObject("isGroup", "No");
				mav.addObject("groupId", -1);
			}
			double doubleStartPoints = 0;
			String review = "";
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				fixerId = query.getFixerId();
				User fixer = userService.findUserById(fixerId);
				FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
						query.getFixerId(), userId);
				if (fixerRating != null) {
					fixerRatingService.upDateRatingBasedOnIds(query.getQueryId(), query.getFixerId(), userId,
							doubleStartPoints, review);
				} else {
					fixerRatingService.saveFixerRatings(query, fixer, userId, doubleStartPoints, review);
				}
			}
		} catch (Exception e) {

		}
		mav.setViewName("redirect:/member/request?msgKey=RT&status=Closed");
		return mav;
	}
	

	@RequestMapping(value = "/member/privacyPolicy", method = RequestMethod.GET)
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
			long countMsg = 0;
			try {
				countMsg = messageNotificationService.getAllNotificationBasedOnId(user.getUserId());
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("countMsg", countMsg);
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("privacy_policy.jsp");
		return mav;

	}

	@RequestMapping(value = "/member/userAgreement", method = RequestMethod.GET)
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
			long countMsg = 0;
			try {
				countMsg = messageNotificationService.getAllNotificationBasedOnId(user.getUserId());
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("countMsg", countMsg);
		} else {
			mav.addObject("isGroup", "No");
			mav.addObject("groupId", -1);
		}
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("term_condition.jsp");
		return mav;

	}

	@RequestMapping(value = "/member/saveNewPassword", method = RequestMethod.POST)
	public ModelAndView saveNewPassword(@Validated @ModelAttribute("newPassword") NewPassword newPassword,
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
			long countMsg = 0;
			try {
				countMsg = messageNotificationService.getAllNotificationBasedOnId(user.getUserId());
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("countMsg", countMsg);
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
			mav.addObject("memberAlert", user);
			mav.addObject("newPassword", newPassword);

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
			mav.addObject("passwordError", exist);
			mav.addObject("member", user);
			mav.addObject("myUser", user);
			mav.addObject("memberAlert", user);
			mav.addObject("currentPage", "settings");
		}
		mav.setViewName("settings/memberSettings.jsp");
		return mav;

	}

	

	@RequestMapping(value = "/member/sendGroupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberSendGroupMessage(@RequestParam String message, @RequestParam String groupId,
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
	@RequestMapping(value = "/member/loadGroupMessage", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/findFixerDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findFixerDetails(@RequestParam Integer fixerId,
			@RequestParam(value = "queryId", required = false, defaultValue = "EMPTY") String queryId,
			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			int userId = user.getUserId();
			if (null != queryId && (!queryId.equals("EMPTY") && !queryId.trim().isEmpty())) {
				List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
				messagesSet = queryAuditService.getQueryOpenDetailMessages(queryId, fixerId, -1, -1);
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

				UserBo userBo = userService.findFixerBoById(fixerId, userId);
				return Utility.getSuccessResponse("status", "success", "fixer", userBo, "messagesSet", messagesSet);

			} else {

				UserBo userBo = userService.findFixerBoById(fixerId, userId);

				return Utility.getSuccessResponse("status", "success", "fixer", userBo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@RequestMapping(value = "/member/dashBoard", method = RequestMethod.GET)
	public ModelAndView memberDashboard(HttpSession session,
			@RequestParam(value = "msgKey", required = false) String msgKey) {

		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) session.getAttribute("user");

		try {
			mav.addObject("myUser", user);
			Calendar calendar = user.getCreatedAt();
			UserBo userBo = new UserBo();
			mav.addObject("userBo", userBo);
			String userSince = TimeDiffUtility.getUserFrom(calendar);
			if (userSince != null) {
				mav.addObject("userSince", userSince);
			}
			if (user != null) {
				MemberSetting memberSetting = new MemberSetting();
				int userId = user.getUserId();
				int adminUserId = 0;
				Set<User> admin = userService.findAdminByStatus(DbConstants.ADMIN);
				for (User adminId : admin) {
					adminUserId = adminId.getUserId();
					break;
				}
				List<Integer> userIds = new LinkedList<Integer>();
				userIds.add(user.getUserId());
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
				// ChatGroups chatGroups = new ChatGroups();
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
				// update all message status

				
				mav.addObject("myUserId", user.getUserId());
				long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
				long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
				long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
				unClaimedCount = unClaimedCount+holdCount;
				//long sumOfCredits = queryService.findSumofQueryCreditByUserId(user.getUserId());
				long sumOfCredits = queryService.findSumofQueryCreditInProgAndFixedByUserId(user.getUserId());
				
				long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

				mav.addObject("inProgressCount", inProgressCount);
				mav.addObject("openRequestCount", unClaimedCount);
				mav.addObject("sumOfCredits", sumOfCredits);
				mav.addObject("fixedIssuesCount", fixedIssuesCount);
				mav.addObject("remaininghours", userCreditService.getPoints(userId));
				mav.addObject("memberSetting", memberSetting);
				mav.addObject("userTimeZone", user.getTimeZone());
				mav.addObject("timeZoneList", TimeZoneList.timeZoneList());
				mav.addObject("catsJSONByUser", objectMapper.writeValueAsString(categoryTypeService
						.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(userId))));

				mav.addObject("industry", industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
				mav.addObject("industJSONByUser", objectMapper.writeValueAsString(industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId))));
				mav.addObject("industryByUser", industryTypeService
						.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(userId)));
				mav.addObject("industryJSON",
						objectMapper.writeValueAsString(industryTypeService.findAllIndustryType()));
				mav.addObject("allIndustry", industryTypeService.findAllIndustryType());
				mav.addObject("countryList", countryService.findAllCountry());

				
				long resolvedQueries = queryAuditService.findAllQueryCountByFixerId(userId, "C");
				if (resolvedQueries >= 0) {
					mav.addObject("resolvedQueries", resolvedQueries);
				}
				long responseTime = queryService.findAverageResponseTime(userId, "C");
				if (responseTime >= 0) {
					mav.addObject("responseTime", responseTime);
				}

				

				// pie chart data

				List<PieChartData> chartDatas = queryService.findQueryByParentCatMember(userId);
				mav.addObject("pieChartData", objectMapper.writeValueAsString(chartDatas));

				String message = "";
				String msgType = "";
				switch (msgKey) {
				case "R":
					message = "";
					break;

				case "N":
					message = "Your request has been saved successfully.";
					msgType = "success";
					break;

				case "E":
					message = "Your request has been updated successfully.";
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
					message = "Your profile has been saved successfully.";
					msgType = "success";
					break;
				case "PUF":
					message = "Your profile has not been updated.  ";
					msgType = "Error";
					break;
				case "STS":
					message = "Your settings have been saved successfully.";
					msgType = "success";
					break;
				case "STF":
					message = "Your settings have not been updated. ";
					msgType = "Error";
					break;
				case "MAE":
					message = "This Email or UserName has already registered as member";
					msgType = "Error";

				}

				mav.addObject("message", message);
				mav.addObject("msgType", msgType);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("currentPage", "dashboard");
		mav.setViewName("dashboard/memberDashboard.jsp");
		return mav;
	}

	@RequestMapping(value = "/member/updateMemberSettings", method = RequestMethod.POST)
	public ModelAndView updateMemberSettings(@ModelAttribute("memberSetting") MemberSetting memberSetting,
			HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserBo member = new UserBo();
		User user = (User) session.getAttribute("user");
		List<User> list;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		int listSize = 0;
		try {
			if (!memberSetting.getEmail().equals(user.getEmail())) {

				list = userService.checkExistingEmail(memberSetting.getEmail());
				listSize = list.size();
			}

			if (listSize > 0) {
				mav.setViewName("redirect:/member/dashBoard?msgKey=MAE");

			} else {
				String imageUrl = memberSetting.getImageUrl();
				if (imageUrl != null && !imageUrl.isEmpty()) {
					

					if (FileUpload.isValidProfileImage(imageUrl)) {
						FileUpload.deleteFileFromMemberFolder(imageUrl, user.getUserId());
						boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(imageUrl, userId);
						if (isInMemberFolder) {
							FileUpload.deleteFileFromProfileImageFolder(imageUrl);
						}
					}
					if (FileUpload.isValidProfileImage(FileUpload.getmemberIconFileNameFromImage(imageUrl, userId))) {
						FileUpload.deleteFileFromMemberFolder(
								FileUpload.getmemberIconFileNameFromImage(imageUrl, userId), user.getUserId());
						boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(
								FileUpload.getmemberIconFileNameFromImage(imageUrl, userId), userId);
						if (isInMemberFolder) {
							FileUpload.deleteFileFromProfileImageFolder(
									FileUpload.getmemberIconFileNameFromImage(imageUrl, userId));
						}
					}

				}
				user = userService.updateUserSettings(memberSetting, user);

				if (user != null) {
					if (null != memberSetting.getNewPasword()) {
						if (!(memberSetting.getNewPasword().trim().isEmpty()
								&& memberSetting.getConfirmPassword().trim().isEmpty())
								&& (memberSetting.getConfirmPassword().equals(memberSetting.getNewPasword()))) {
							user = userService.saveUserNewPassword(user, memberSetting.getNewPasword());
						}
					}
				}
				session.setAttribute("user", user);

				mav.setViewName("redirect:/member/dashBoard?msgKey=STS");
			}
		} catch (Exception e) {
			mav.setViewName("redirect:/member/dashBoard?msgKey=STF");

			e.printStackTrace();
		}
		mav.addObject("myUser", user);

		return mav;
	}

	@RequestMapping(value = "/member/editProfile", method = RequestMethod.POST)
	public ModelAndView createEditProfilePost(@ModelAttribute UserBo userBo, HttpSession session,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		User user = null;
		try {
			String firstName = "";
			String lastName = "";
			
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
				MemberProfile profile = new MemberProfile();

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
						FileUpload.deleteFileFromMemberFolder(profile.getImageUrl(), user.getUserId());
						boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(profile.getImageUrl(),
								userId);
						if (isInMemberFolder) {
							FileUpload.deleteFileFromProfileImageFolder(profile.getImageUrl());
						}
					}
					if (FileUpload.isValidProfileImage(
							FileUpload.getmemberIconFileNameFromImage(profile.getImageUrl(), userId))) {
						FileUpload.deleteFileFromMemberFolder(
								FileUpload.getmemberIconFileNameFromImage(profile.getImageUrl(), userId),
								user.getUserId());
						boolean isInMemberFolder = FileUpload.moveFileFromprofileToMemberFolder(
								FileUpload.getmemberIconFileNameFromImage(profile.getImageUrl(), userId), userId);
						if (isInMemberFolder) {
							FileUpload.deleteFileFromProfileImageFolder(
									FileUpload.getmemberIconFileNameFromImage(profile.getImageUrl(), userId));
						}
					}

				}
				User updateUser = userService.updateMemberProfile(profile,
						request.getSession().getServletContext().getRealPath("/"));

				session.setAttribute("user", updateUser);
				mav.setViewName("redirect:/member/dashBoard?msgKey=PSS");

			}
		} catch (Exception e) {

			e.printStackTrace();
			mav.setViewName("redirect:/member/dashBoard?msgKey=PUF");

		}

		return mav;

	}

	@ResponseBody
	@RequestMapping(value = "/member/getRequestedAreas", method = RequestMethod.GET)
	public Map<String, List<RequestAreas>> getRequestedAreas(HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		try {
			Set<Query> setOFQuery = queryService.findQueryByCustomerId(userId);
			Iterator<Query> itr = setOFQuery.iterator();
			Set<QueryCategory> listOfQueryCateg = new HashSet<QueryCategory>();
			while (itr.hasNext()) {

				listOfQueryCateg.addAll(itr.next().getQueryCategory());

			}
			Iterator<QueryCategory> itrQueryCategory = listOfQueryCateg.iterator();
			List<String> catList = new ArrayList<String>();
			while (itrQueryCategory.hasNext()) {

				catList.add(itrQueryCategory.next().getCategoryType().getCatgName());

			}
			Map<String, Integer> map = new HashMap<String, Integer>();

			for (String temp : catList) {
				Integer count = map.get(temp);
				map.put(temp, (count == null) ? 1 : count + 1);
			}
			
			List<RequestAreas> list = new ArrayList<RequestAreas>();

			for (Map.Entry<String, Integer> entry : map.entrySet()) {

				RequestAreas requestAreas = new RequestAreas();
				requestAreas.setCount(entry.getValue());
				requestAreas.setCatName(entry.getKey());
				list.add(requestAreas);
				
			}
			Collections.sort(list, new Comparator<RequestAreas>() {
				public int compare(RequestAreas requestAreas1, RequestAreas requestAreas2) {
					// notice the cast to (Integer) to invoke compareTo
					return ((String) requestAreas1.getCatName()).compareTo(requestAreas2.getCatName());
				}
			});
			Map<String, List<RequestAreas>> responseMap = new HashMap<String, List<RequestAreas>>();
			responseMap.put("response", list);
			return responseMap;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/member/memberGroupDetail", method = RequestMethod.POST)
	public ModelAndView memberGroupDetail(@RequestParam String groupId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Integer> addUserID = new ArrayList<Integer>();
		User user = (User) session.getAttribute("user");
		ChatGroups chatGroups = new ChatGroups();
		Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();
		try {
			
			Integer groupIdInteger = Integer.parseInt(groupId);
			chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
			if (chatGroups != null) {
				chatMessageSet = chatMessageService.getAllChatMessagesBasedOnGroupId(chatGroups.getChatGroupId(), user);
			}
			mav.addObject("messagesSet", chatMessageSet);
			mav.addObject("groupId", groupId);
			mav.addObject("groupName", chatGroups.getChatGroupName());
			// update all message status

			
			mav.addObject("messagesSet", chatMessageSet);
			mav.addObject("groupId", groupId);
			mav.addObject("groupName", chatGroups.getChatGroupName());
			

			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/member/approveFixer", method = RequestMethod.GET)
	public ModelAndView fixerapprovalPage(@RequestParam Integer queryId, @RequestParam String fixerId,
			HttpSession session, HttpServletRequest request, @RequestParam Long queryPoints) {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		MemberDashboardBo memberDashboardBo = null;
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		try {

			query = queryService.findQueryByQueryId(queryId);

			// update member credits when approving fixer
	       
			try { 
				long points = userCreditService.getPoints(userId);
				
				if(points <= 0 || points < queryPoints){
					
					mav.setViewName("redirect:/member/request?msgKey=CS");
					 return mav;
				}
				
				
				points = points - queryPoints;
				int i = userCreditService.updatePoints(userId, points);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Calendar currentTime = Calendar.getInstance();
			query.setFixerId(Integer.parseInt(fixerId));
			query.setCurrentStatus(DbConstants.STATUS_WORKING);
			query.setDateAccepted(currentTime);


			queryService.UpdateQuery(query);
			User fixer = userService.findUserById(Integer.parseInt(fixerId));
			emailServcie.emailToFixerMemberApproveRequest(base, query, fixer);
			emailServcie.emailToFixersMemberDisapproveRequest(base, query, Integer.parseInt(fixerId));

		} catch (FixitException e) {
		}



		mav.setViewName("redirect:/member/request?msgKey=AP&status=InProgress");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/member/showChatMessages", method = RequestMethod.POST)
	public Map<String, Object> showChatMessages(HttpSession session, @RequestParam String queryHasCode,
			@RequestParam int fixerId) {
		try {
			User user = (User) session.getAttribute("user");

			List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
			messagesSet = queryAuditService.getQueryOpenDetailMessages(queryHasCode, fixerId, -1, -1);
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
			return Utility.getFailureResponse("staus", "faied");
		}
	}

	@RequestMapping(value = "/member/updateIndustry", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/deleteIndustry", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/updateMessageCounts", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/reassignRequest", method = RequestMethod.GET)
	public ModelAndView reassignRequest(@RequestParam("queryId") String queryId, @RequestParam int fixerId,
			HttpSession session) {

		Map<String, String> selecedFixers = new HashMap<String, String>();
		ModelAndView modelAndView = null;
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<CategoryType> categoryTypes = null;

		ObjectMapper objectMapper = new ObjectMapper();
		try {

			modelAndView = new ModelAndView();
			List<Integer> userIds = new LinkedList<Integer>();
			userIds.add(userId);
			long points = userCreditService.getPoints(userId);
			modelAndView.addObject("points", points);


			Query queryVo = queryService.getQueryByHashCode(queryId);
			QueryBo query = new QueryBo();
			query.setQueryCredits(queryVo.getQueryCredits());
			query.setQueryTitle(queryVo.getQueryTitle());
			query.setQueryContent(queryVo.getQueryContent());
			query.setCurrentStatus(queryVo.getCurrentStatus());
			query.setFixerId(queryVo.getFixerId());
			
		
			
			
			query.setQueryMode("CREATE");
			selecedFixers = queryFixersService.getQueryFixersIdsAndNamesBasedOnQueryId(queryVo.getQueryId(), -1, -1);
			selecedFixers.remove(String.valueOf(fixerId));
			modelAndView.addObject("rejectedfixerId", fixerId);
			modelAndView.addObject("queryHashCode", queryId);
			modelAndView.addObject("queryId", queryVo.getQueryId());

			if ("true".equals(selecedFixers.get("selectedFixers"))) {
				modelAndView.addObject("selectedFixer", true);
			} else {
				modelAndView.addObject("selectedFixer", false);
			}
			query.setFixersIds(selecedFixers.get("fixersId"));
			query.setFixersNames(selecedFixers.get("fixersNames"));

			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			Set<QueryCategory> queryCategories = queryCategoryDao.findQueryCategoryByQueryId(queryVo.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				catTypeSet.add(categoryTypeService.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId()));
			}

			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			modelAndView.addObject("inProgressCount", inProgressCount);
			modelAndView.addObject("openRequestCount", unClaimedCount);
			modelAndView.addObject("fixedIssuesCount", fixedIssuesCount);

			modelAndView.addObject("query", query);
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("CREATE"));

			modelAndView.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("parentCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategory",
					categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("CREATE"));
			modelAndView.addObject("queryCategories", catTypeSet);
			modelAndView.addObject("countryList", (countryService.findAllCountry()));
			modelAndView.addObject("parentCategory",
					categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));

			modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));

			modelAndView.addObject("myUserJson", objectMapper.writeValueAsString(user));
			modelAndView.addObject("allCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("parentCategoryJson", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			
			Set<QueryFiles> files = queryFilesDao.findDocumentsByQueryId(queryVo.getQueryId());
			categoryTypes = categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE);

			
			if (files.size() > 0) {

				modelAndView.addObject("documents", files);
			} else {
				modelAndView.addObject("documents", null);
			}

		} catch (Exception e) {

		}

		modelAndView.addObject("allCategories", categoryTypes);
		modelAndView.addObject("myUser", user);
		modelAndView.addObject("currentPage", "dashboard");
		modelAndView.setViewName("/reassigned_request.jsp");
		return modelAndView;

	}

	@RequestMapping(value = "/member/reassignedAskQuestion", method = RequestMethod.POST)
	public ModelAndView postQuestionAfterReassing(@ModelAttribute("query") QueryBo query, HttpServletRequest request,
			HttpSession session) {
		Set<User> selectedFixersSet = new HashSet<User>();
		ModelAndView modelAndView = new ModelAndView();
		Set<Integer> fixerIdList = new HashSet<Integer>();
		Set<Integer> fixerNameList = new HashSet<Integer>();
		String fixersIdJson = query.getFixersIds();
		
		String fixersIdJsonArr[] = fixersIdJson.split(",");
		
		if (fixersIdJsonArr != null && fixersIdJsonArr.length > 0 && !(fixersIdJson.equals(""))) {
			modelAndView.addObject("selectedFixer", true);
			for (String s : fixersIdJsonArr) {
				fixerIdList.add(Integer.parseInt(s));
				fixerIdList.remove(query.getRejectedfixerId());
			}
		} else {
			modelAndView.addObject("selectedFixer", false);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		try {

			long points = userCreditService.getPoints(userId);
			// update member credits on reassign
			 points =points + query.getOldCredits();
 			int i = userCreditService.updatePoints(userId, points);
			modelAndView.addObject("points", points);

			Query queryByHashCode = queryService.getQueryByHashCode(query.getQueryHascode());
			User fixer = userService.findUserById(query.getFixerId());
			queryAppliedFixersService.saveFixerDecline(queryByHashCode.getFixerId(), queryByHashCode.getQueryId());

			queryByHashCode = queryService.rejectFixer(queryByHashCode.getQueryId());
			Set<QueryAppliedFixers> appliedfixerset  = queryByHashCode.getQueryAppliedFixers();
		
			//delete applied fixer 
			queryAppliedFixersService.deleteAppliedFixerByQueryIdAndStatus(queryByHashCode.getQueryId(), "A");

			try {

				query.setQueryId(queryByHashCode.getQueryId());
				

				emailServcie.emailToFixerRequestRejected(fixer, queryByHashCode);

				Set<User> fixers = userService.findFixerWhoMatchQueryCat(query.getQueryId());

				fixers.remove(fixer);
				queryFixersService.deleteQueryFixersBasedOnQueryId(query.getQueryId());


			} catch (Exception e) {
				
			}
			Query savedQuery = queryService.saveQuery(query, request.getSession().getServletContext().getRealPath("/"),
					userId);

			List<String> listCatName = new ArrayList<String>();
			List<Integer> categories = query.getCategories();

			for (Integer cat : categories) {
				CategoryType categoryType = categoryTypeDao.findCategoryTypeByCatId(cat);
				String catName = categoryType.getCatgName();
				listCatName.add(catName);
			}

			modelAndView.addObject("queryCategories", listCatName);

			List<String> filenames = query.getDocuments();
			List<String> fileList = new ArrayList<String>();
			if (filenames != null) {
				for (String fileName : filenames) {

					String awsFilePath = DbConstants.AMAZON_S3_URL + DbConstants.SUFFIX + DbConstants.DOCUMENTS_FOLDER
							+ DbConstants.SUFFIX + DbConstants.USER_FOLDER + userId + DbConstants.SUFFIX
							+ DbConstants.QUERY_FOLDER + savedQuery.getQueryId() + DbConstants.SUFFIX + fileName;

					fileList.add(awsFilePath);
				}
			}
			if (savedQuery != null) {
				queryFixersService.deleteQueryFixersBasedOnQueryId(savedQuery.getQueryId());
				selectedFixersSet = userService.findAllUserBasedOnIdList(fixerIdList, -1, -1);
				queryFixersService.saveQueryFixers(selectedFixersSet, savedQuery);
				boolean notSureCatFlag = false;
				List<Integer> catIds = query.getCategories();
				if (catIds.contains(DbConstants.NOT_SURE_CAT_ID)) {
					notSureCatFlag = true;
				}

				if (notSureCatFlag) {
					Set<User> users = new HashSet<User>();
					users.add(user);
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(users, user.getUserId(),
							savedQuery.getQueryId(), base);
					emailServcie.emailToMemberNotSureCat(savedQuery, queryExpireSet, base);
				} else {
					if (fixersIdJsonArr.length > 0 && !(fixersIdJson.equals("[]")) && selectedFixersSet.size() > 0 && selectedFixersSet != null) {

						Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(selectedFixersSet,
								user.getUserId(), savedQuery.getQueryId(), base);

						emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(selectedFixersSet, savedQuery,
								queryExpireSet, base, fileList);
					} else {
						Set<User> fixers = userService.findFixerWhoMatchQueryCat(savedQuery.getQueryId());

						Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
								savedQuery.getQueryId(), base);

						emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, savedQuery, queryExpireSet,
								base, fileList);
					}
				}
			}

			modelAndView.addObject("selectedQueryId", query.getQueryId());


			modelAndView.addObject("modeType", "reassign");
			List<AppliedFixersListBo> appliedFixersListBo = new ArrayList<AppliedFixersListBo>();
			if (selectedFixersSet != null) {
				appliedFixersListBo = userService.setAppliedFixerList(selectedFixersSet, userId);
				modelAndView.addObject("selectedFixersSetLen", selectedFixersSet.size());
			} else {
				modelAndView.addObject("selectedFixersSetLen", 0);

			}

			modelAndView.addObject("appliedFixersListBo", appliedFixersListBo);
			long fixedIssuesCount = queryService.findUserClosedStasCount(userId);
			long unClaimedCount = queryService.findUserStasCount(userId, DbConstants.STATUS_OPEN);
			
			long holdCount = queryService.findUserStasCount(userId, DbConstants.STATUS_HOLD);
			unClaimedCount = unClaimedCount+holdCount;
			long inProgressCount = queryService.findUserStasCount(userId, DbConstants.STATUS_WORKING);

			modelAndView.addObject("inProgressCount", inProgressCount);
			modelAndView.addObject("openRequestCount", unClaimedCount);
			modelAndView.addObject("fixedIssuesCount", fixedIssuesCount);
			modelAndView.setViewName("postedRequest.jsp");

			// modelAndView.setViewName("redirect:/member/request?msgKey=E");

			modelAndView.addObject("myUser", user);
			modelAndView.addObject("currentPage", "dashboard");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return modelAndView;
	}

	@RequestMapping(value = "/member/previousGiattChart", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/nextGanttChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nextGanttChart(HttpSession session, @RequestParam String currentStartDate)
			throws FixitException {

		User user = (User) session.getAttribute("user");
		
		// data generation for frequency and duration

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

		List<Query> queries = queryService.findQueryClosed(user.getUserId(), startDate, endDate);
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
	@RequestMapping(value = "/member/getComparePassword", method = RequestMethod.POST)
	public Map<String, Object> comparePassword(HttpSession session, @RequestParam String password) {
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
	@RequestMapping(value = "/member/updateMessageNotification", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/posted", method = RequestMethod.GET)
	public ModelAndView pppp() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("postedRequest.jsp");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/member/adminMessageNotification", method = RequestMethod.POST)
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

	@RequestMapping(value = "/member/fixerLoadGroupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fixerLoadGroupMessage(@RequestParam Integer fixerId,
			@RequestParam(value = "queryId", required = false, defaultValue = "EMPTY") String queryId,
			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			int userId = user.getUserId();
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

			UserBo userBo = userService.findFixerBoById(fixerId, userId);
			return Utility.getSuccessResponse("status", "success", "fixer", userBo, "messagesSet", messagesSet);

		} catch (Exception e) {
			e.printStackTrace();
			
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/member/denyAppliedFixer", method = RequestMethod.POST)
	public Map<String, Object> denyAppliedFixer(HttpServletRequest request, Integer queryId, Integer fixerId) {

		try {
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			User fixer = userService.findUserById(fixerId);
			Query query = queryService.findQueryByQueryId(queryId);

			if (fixer != null) {
				queryAppliedFixersService.saveFixerDecline(fixerId, queryId);

				if (DbConstants.EMAIL_ALERT.equals(fixer.getEmailAlert())) {
					emailServcie.emailToAppliedFixersMemberDisapproveRequest(base, query, fixer);
				}

				return Utility.getSuccessResponse();
			}
		} catch (FixitException e) {
			e.printStackTrace();

		}
		return Utility.getFailureResponse();
	}

}
