package com.fixit.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixit.dao.QueryCategoryDao;
import com.fixit.dao.QueryDao;
import com.fixit.dao.QueryFilesDao;
import com.fixit.domain.bo.AdminMemberSearch;
import com.fixit.domain.bo.AsugUserData;
import com.fixit.domain.bo.ChatMessageBo;
import com.fixit.domain.bo.FixerPayment;
import com.fixit.domain.bo.FixerSearchBo;
import com.fixit.domain.bo.LatestFeeds;
import com.fixit.domain.bo.MemberDashboardBo;
import com.fixit.domain.bo.NewPassword;
import com.fixit.domain.bo.QueryAuditBo;
import com.fixit.domain.bo.QueryBo;
import com.fixit.domain.bo.QueryReviewBo;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.UserGroupsList;
import com.fixit.domain.vo.CategoryType;
import com.fixit.domain.vo.ChatGroups;
import com.fixit.domain.vo.ChatMessage;
import com.fixit.domain.vo.ChatUserGroup;
import com.fixit.domain.vo.FixerAccounting;
import com.fixit.domain.vo.FixerRating;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryAudit;
import com.fixit.domain.vo.QueryCategory;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.QueryFiles;
import com.fixit.domain.vo.User;
import com.fixit.service.AdminService;
import com.fixit.service.BlogService;
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
import com.fixit.service.PayPalMerchantToFixerPaymentService;
import com.fixit.service.QueryAuditService;
import com.fixit.service.QueryExpireService;
import com.fixit.service.QueryService;
import com.fixit.service.UserCategoryService;
import com.fixit.service.UserCreditService;
import com.fixit.service.UserIndustryService;
import com.fixit.service.UserService;
import com.fixit.utility.DateFormat;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.FixitVariables;
import com.fixit.utility.Pagination;
import com.fixit.utility.TimeDiffUtility;
import com.fixit.utility.TimeZoneList;
import com.fixit.utility.Utility;
import com.fixit.validation.FixerSettingsValidator;
import com.fixit.validation.NewPasswordValidator;

import bsh.util.Util;

@Controller("AdminController")
public class AdminController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private NewPasswordValidator newPasswordValidator;

	@Autowired
	private ChatGroupsService chatGroupsService;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private ChatUserGroupService chatUserGroupService;

	@Autowired
	private MessageNotificationService messageNotificationService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private IndustryTypeService industryTypeService;

	@Autowired
	private PayPalMerchantToFixerPaymentService payPalMerchantToFixerPaymentService;

	@Autowired
	private FixerRatingService fixerRatingService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private EmailServcie emailServcie;

	@Autowired
	private UserIndustryService userIndustryService;

	@Autowired
	private UserCategoryService userCategoryService;

	@Autowired
	private QueryExpireService queryExpireService;

	@Autowired
	private CategoryTypeService categoryTypeService;

	@Autowired
	private UserService userService;

	@Autowired
	private FixerSettingsValidator fixerSettingsValidator;

	@Autowired
	private QueryDao queryDao;

	@Autowired
	private QueryService queryService;

	@Autowired
	private QueryAuditService queryAuditService;

	@Autowired
	private QueryCategoryDao queryCategoryDao;

	@Autowired
	private QueryFilesDao queryFilesDao;

	@Autowired
	private AdminService adminService;

	@Autowired
	private FixerAccountingService fixerAccountingService;

	@Autowired
	private UserCreditService userCreditService;

	@Autowired
	private FavouriteFixerService favouriteFixerService;

	@RequestMapping(value = "/findQueryNotAcceptedByFixer", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findQueryNotAcceptedByFixer() throws FixitException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return Utility.getSuccessResponse("query",
				queryDao.findQueryInactiveFixers(simpleDateFormat.format(calendar.getInstance()), 1), "query2",
				queryDao.findQueryByQueryId(263));
	}

	@RequestMapping(value = "/admin/adminSettings", method = RequestMethod.GET)
	public ModelAndView fixerSettings(HttpSession session) {
		
		System.out.println("  admin/adminSettings controller method is being called ");
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		UserBo member = new UserBo();
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		mav.addObject("currentPage", "settings");
		mav.addObject("myUser", user);
		mav.addObject("member", user);
		mav.addObject("fixerAlert", user);
		try {
			mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
		} catch (JsonProcessingException | FixitException e) {
			
		}
		mav.addObject("userUpdate", null);
		NewPassword newPassword = new NewPassword();
		mav.addObject("newPassword", newPassword);
		mav.setViewName("admin/adminSettings.jsp");
		return mav;
	}

	
	@RequestMapping("/admin/FixerForReview")
	public ModelAndView FixerForReview(@RequestParam("msgKey") String msgKey,
			@RequestParam(value = "timeStamp", required = false) String timeStamp,
			@RequestParam(value = "status", required = false, defaultValue = "fixerApproval") String status,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag, HttpSession session)
			throws FixitException {
		
		User user = (User) session.getAttribute("user");
		

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/adminDashBoard.jsp");
		Set<User> forFixerCount = null;
		Set<QueryReviewBo> queries;
		ObjectMapper objectMapper = new ObjectMapper();
		Calendar currentTime;

		try {
			
			currentTime = Calendar.getInstance();
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
			switch (status) {
			case "fixerApproval":
				long fixerApprovalCount = adminService.loadAllFixerCountWithStatus(-1, -1, "R");
				totalPage = (int) Math.ceil((float) fixerApprovalCount / DbConstants.PAGE_SIZE);
				forFixerCount = adminService.loadAllFixerWithStatus(startIndex, DbConstants.PAGE_SIZE, "R");
				mav.addObject("fixerReviewCount", objectMapper.writeValueAsString((Integer) forFixerCount.size()));
				mav.addObject("fixerStatus",
						adminService.loadAllFixerWithStatus(startIndex, DbConstants.PAGE_SIZE, "R"));
				mav.addObject("fixerApprovalCount", fixerApprovalCount);
				break;

			case "queryReview":
				long reviewCount = adminService.loadAllReviewQueriesCount(-1, -1, "R");
				totalPage = (int) Math.ceil((float) reviewCount / DbConstants.PAGE_SIZE);
				mav.addObject("queriesStatus",
						queries = adminService.loadAllReviewQueries(startIndex, DbConstants.PAGE_SIZE, "R"));
				mav.addObject("reviewCount", reviewCount);
				break;

			case "notSure":
				Set<MemberDashboardBo> notSureQuery;
				long notSureCount = adminService.findQueryNotSureCat(DbConstants.NOT_SURE_CAT_ID, -1, -1);
				totalPage = (int) Math.ceil((float) notSureCount / DbConstants.PAGE_SIZE);
				notSureQuery = adminService.findQueryNotSureCat(currentTime, DbConstants.NOT_SURE_CAT_ID,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("notSure", notSureQuery);
				mav.addObject("notSureCount", notSureCount);
				break;

			case "unClaimed":
				Set<MemberDashboardBo> unClaimed;
				BigInteger count = queryService.findQueryNotAcceptedByFixerCount(
						DateFormat.getDateFormat().format(currentTime.getTime()), DbConstants.QUERY_ACCEPT_LIMIT);
				long unClaimedCount = count.longValue();
				totalPage = (int) Math.ceil((float) unClaimedCount / DbConstants.PAGE_SIZE);
				unClaimed = adminService.findMemberQueryNotAccepted(currentTime, DbConstants.QUERY_ACCEPT_LIMIT,
						user.getTimeZone(), startIndex, DbConstants.PAGE_SIZE);
				mav.addObject("unClaimed", unClaimed);
				mav.addObject("unClaimedCount", unClaimedCount);
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

			case "QU":
				message = "Issue Updated Successfully";
				msgType = "success";
				break;

			case "CA":
				message = "Category Assigned Successfully";
				msgType = "success";
				break;

			case "FA":
				message = "Fixer Assigned Successfully";
				msgType = "success";
				break;

			}

			mav.addObject("message", message);
			mav.addObject("msgType", msgType);
			mav.addObject("status", status);
			mav.addObject("currentPage", "dashboard");

		} catch (FixitException e) {
			
			e.printStackTrace();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		java.util.Date date = new java.util.Date();
		String timeStampEnd = (new Timestamp(date.getTime()).toString());
		

		
		return mav;
	}

	@RequestMapping(value = "/admin/queryDetail", method = RequestMethod.GET)
	public ModelAndView adminQueryDetailPage(@RequestParam String queryId, HttpSession session) throws FixitException {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		ModelAndView mav = new ModelAndView();
		Query query = null;
		List<QueryAudit> messagesSet = new LinkedList<QueryAudit>();
		try {
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

			QueryAudit reasonFromMember = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
					query.getFixerId(), query.getQueryId(), "UN");
			if (reasonFromMember != null) {
				mav.addObject("memberReason", reasonFromMember.getMessage());
			}

			QueryAudit reasonFromFixer = queryAuditService.getQueryAuditBasedOnStatus(query.getUser().getUserId(),
					query.getFixerId(), query.getQueryId(), "R");
			if (reasonFromFixer != null) {
				mav.addObject("fixerReason", reasonFromFixer.getMessage());
			}

			mav.addObject("query", query);
			User member = userService.findUserById(query.getUser().getUserId());
			mav.addObject("memberName", member.getUserName());
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
			mav.addObject("memberImgIcon", member.getProfileIcon());
			User fixer = userService.findUserById(query.getFixerId());
			mav.addObject("fixerName", fixer.getUserName());
			mav.addObject("fixerImgIcon", fixer.getProfileIcon());
			mav.addObject("messagesSet", messagesSet);
			mav.addObject("currentPage", "dashboard");
			mav.setViewName("admin/adminReviewQueries.jsp");

			return mav;
		} catch (Exception e) {
			throw new FixitException("", "");
		}

	}

	@RequestMapping(value = "/admin/favorInFixer", method = RequestMethod.GET)
	public ModelAndView adminResolveIssueInFavourOfFixer(@RequestParam String queryId) throws FixitException {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		try {
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				queryService.setQueryStatusToClosed(query.getQueryId(), DbConstants.STATUS_CLOSED);
				try {
					if (DbConstants.STATUS_CLOSED.equals(query.getCurrentStatus())) {
						double doubleStartPoints = 0;
						String review = "";
						if (query != null) {
							Integer fixerId = query.getFixerId();
							User fixer = userService.findUserById(fixerId);
							FixerRating fixerRating = fixerRatingService.findRatingBasedOnIds(query.getQueryId(),
									query.getFixerId(), query.getUser().getUserId());
							if (fixerRating != null) {
								fixerRatingService.upDateRatingBasedOnIds(query.getQueryId(), query.getFixerId(),
										query.getUser().getUserId(), doubleStartPoints, review);
							} else {
								fixerRatingService.saveFixerRatings(query, fixer, query.getUser().getUserId(),
										doubleStartPoints, review);
							}

							FixerAccounting fixerAccounting = new FixerAccounting();
							fixerAccounting.setQuery(query);
							fixerAccounting.setUser(fixer);
							fixerAccounting.setStatus(0);
							fixerAccounting.setAmountPaid(40.0);
							fixerAccountingService.saveFixerAccounting(fixerAccounting);

						}
					}

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
			mav.setViewName("redirect:/admin/FixerForReview?msgKey=QU&status=queryReview");
			return mav;
		} catch (Exception e) {
			throw new FixitException("", "");
		}

	}

	@RequestMapping(value = "/admin/favorInMember", method = RequestMethod.GET)
	public ModelAndView adminResolveIssueInFavourOfMember(@RequestParam String queryId, HttpServletRequest request)
			throws FixitException {
		ModelAndView mav = new ModelAndView();
		Query query = null;
		try {
			query = queryService.getQueryByHashCode(queryId);
			if (query != null) {
				queryService.adminInFavourOfMember(query.getQueryId());
				try {

					Set<User> fixers = userService.findFixerWhoMatchQueryCat(query.getQueryId());

					Iterator<User> iterator = fixers.iterator();
					while (iterator.hasNext()) {
						User fixer = iterator.next();
						if (query.getFixerId() == fixer.getUserId()) {
							fixers.remove(fixer);
						}
					}
					StringBuffer url = request.getRequestURL();
					String uri = request.getRequestURI();
					String ctx = request.getContextPath();
					String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers,
							query.getUser().getUserId(), query.getQueryId(), base);
					emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, query, queryExpireSet, base, null);
				} catch (Exception e) {

				}

			}
		} catch (FixitException e) {

			e.printStackTrace();
		}
		mav.setViewName("redirect:/admin/FixerForReview?msgKey=QU&status=queryReview");
		return mav;

	}

	@RequestMapping("/admin/updateFixer")
	public ModelAndView updateFixer(int id, String status, HttpServletRequest request) throws FixitException {
		
		ModelAndView mav = new ModelAndView();
		int result;
		try {
			result = adminService.updateFixerStatus(id, status);
			if (result == 1) {
				mav.addObject("status", 1);
				
				Set<User> fixers = new HashSet<User>();
				User user = userService.findUserById(id);
				fixers.add(user);
				StringBuffer url = request.getRequestURL();
				String uri = request.getRequestURI();
				String ctx = request.getContextPath();
				String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
				if (status.equals(DbConstants.USER_DEACTIVE)) {
					emailServcie.emailToFixerForProfileRejection(fixers);
					userService.deleteUserAllData(id);
				}
				if (status.equals(DbConstants.USER_ACTIVE)) {
					emailServcie.emailToFixerForProfileActivation(fixers, base);
				}
			} else {
				mav.addObject("status", 0);
				
			}
			mav.addObject("redirectUrl", "/profile/myFixerProfile.jsp");
			mav.setViewName("profile/myFixerProfile.jsp");
		} catch (FixitException e) {
			throw new FixitException("", "");
		}
		return mav;
	}

	@RequestMapping("/admin/NotSureQueries" + "")
	public ModelAndView FixerForNotSure() throws FixitException {
		ModelAndView mav = new ModelAndView();
		Set<Query> queries;

		try {
			queries = adminService.loadAllNotSureQueries(-1, -1, 65);
			mav.addObject("fixerStatus", queries);
			mav.setViewName("admin/adminnotsurequeries.jsp");
			return mav;
		} catch (FixitException e) {
			throw new FixitException("", "");
		}

	}

	@RequestMapping("/admin/reviewQueries")
	public ModelAndView queriesForReview() throws FixitException {
		ModelAndView mav = new ModelAndView();
		Set<QueryReviewBo> queries;

		try {
			queries = adminService.loadAllReviewQueries(-1, -1, "R");
			mav.addObject("fixerStatus", queries);

			mav.setViewName("admin/adminReviewQueries.jsp");
			return mav;
		} catch (FixitException e) {
			throw new FixitException("", "");
		}

	}

	@RequestMapping(value = "/admin/fixerProfile", method = RequestMethod.GET)
	public ModelAndView myProfile(@RequestParam int fixerId) {
		ModelAndView mav = new ModelAndView();
		try {
			User myUser = userService.findUserById(fixerId);
			String fixerImagePath = FileUpload.fixerProfilePath(myUser.getProfilePhoto());
			Calendar currentTime = Calendar.getInstance();
			mav.addObject("fixerImagePath", fixerImagePath);
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			mav.addObject("user", myUser);
			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(fixerId)));

			Set<CategoryType> catType = categoryTypeService
					.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(fixerId));
			mav.addObject("category", catType);
			List<Integer> listCatIds = new ArrayList<Integer>();
			for (CategoryType categoryType : catType) {
				listCatIds.add(Integer.valueOf(categoryType.getParentId()));
			}

			mav.addObject("parentCat", userService.findAllParentCategoryByCatId(listCatIds));

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		mav.addObject("User_Type", "admin");
		mav.addObject("currentPage", "dashboard");
		mav.addObject("fixerRating", 0);
		mav.setViewName("profile/myFixerProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/sendMessage", method = RequestMethod.POST)
	public ModelAndView fixerSendMessage(@RequestParam String queryId, @RequestParam int customerId,
			@RequestParam int fixerId, @RequestParam String msgFrom, @RequestParam String message,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();
		QueryAudit queryAudit = null;
		QueryAuditBo queryAudiotBo = new QueryAuditBo();
		queryAudiotBo.setCustomerId(customerId);
		queryAudiotBo.setFixerId(fixerId);
		Query query = null;
		try {
			Integer queryIntId = Integer.parseInt(queryId);
			query = queryService.findQueryByQueryId(queryIntId);
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
		mav.setViewName("admin/adminReviewQueries.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/editQuestion", method = RequestMethod.GET)
	public ModelAndView editPostQuestion(@RequestParam("queryId") String queryId, @RequestParam("status") String status,
			HttpSession session) {
		ModelAndView modelAndView = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		int userId;

		try {
			modelAndView = new ModelAndView();
			Query queryVo = queryService.getQueryByHashCode(queryId);
			user = queryVo.getUser();
			userId = user.getUserId();
			Integer selectedFixerId = queryVo.getFixerId();
			if (selectedFixerId > 0) {
				User selectedFixer = userService.findUserById(selectedFixerId);
				if (selectedFixer != null) {

					modelAndView.addObject("selectedFixer", selectedFixer);
				}
			} else {
				modelAndView.addObject("selectedFixer", "");
			}
			// if (DbConstants.STATUS_WORKING.toString().equals(
			// queryVo.getCurrentStatus())) {
			// modelAndView.addObject("currentPage", "dashboard");
			// modelAndView.setViewName("redirect:/member/dashBoard?msgKey=AC");
			// return modelAndView;
			// }
			QueryBo query = new QueryBo();
			query.setQueryId(queryVo.getQueryId());
			query.setFixerId(queryVo.getFixerId());
			query.setQueryTitle(queryVo.getQueryTitle());
			query.setQueryContent(queryVo.getQueryContent());
			query.setCurrentStatus(queryVo.getCurrentStatus());
			query.setQueryMode("EDIT");

			Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
			Set<QueryCategory> queryCategories = queryCategoryDao.findQueryCategoryByQueryId(queryVo.getQueryId());
			for (QueryCategory queryCategory : queryCategories) {
				catTypeSet.add(categoryTypeService.findCategoryTypeByCatId(queryCategory.getCategoryType().getCatId()));
			}

			// query.setCategories();
			modelAndView.addObject("query", query);
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("parentCategory",
					categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
			modelAndView.addObject("parentCategories", objectMapper
					.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
			modelAndView.addObject("allCategory", objectMapper
					.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));

			// categoryId List
			modelAndView.addObject("modeType", objectMapper.writeValueAsString("EDIT"));
			modelAndView.addObject("status", status);
			modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));
			Set<QueryFiles> files = queryFilesDao.findDocumentsByQueryId(queryVo.getQueryId());
			List<String> fileNames = new ArrayList<String>();

			for (QueryFiles file : files) {
				fileNames.add(file.getFileName());
			}
			if (fileNames.size() > 0) {
				modelAndView.addObject("documents", objectMapper.writeValueAsString(fileNames));
			} else {
				modelAndView.addObject("documents", null);
			}

			files = queryFilesDao.findUrlLinksByQueryId(queryVo.getQueryId());
		

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
		modelAndView.setViewName("admin/editquestionadmin.jsp");
		return modelAndView;
	}

	/**
	 * postQuestion POST
	 * 
	 * @param queryBo
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/admin/askQuestion", method = RequestMethod.POST)
	public ModelAndView postQuestion(@Valid @ModelAttribute("query") QueryBo query, BindingResult result, Model model,
			HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		

		try {

			User user = queryService.findQueryByQueryId(query.getQueryId()).getUser();
			int userId = user.getUserId();

			if (result.hasErrors()) {
				modelAndView.addObject("query", query);
				modelAndView.addObject("parentCategory",
						categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE));
				modelAndView.addObject("allCategory", objectMapper
						.writeValueAsString(categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE)));
				modelAndView.addObject("userId", userId);

				// categoryId List
				if (query.getQueryMode().equals("EDIT")) {
					modelAndView.addObject("modeType", objectMapper.writeValueAsString("EDIT"));
				} else {
					modelAndView.addObject("modeType", objectMapper.writeValueAsString("CREATE"));
				}
				Set<CategoryType> catTypeSet = new HashSet<CategoryType>();
				List<Integer> catIds = query.getCategories();
				if (catIds != null && catIds.size() > 0) {
					for (Integer cat : catIds) {
						catTypeSet.add(categoryTypeService.findCategoryTypeByCatId(cat));
					}
				}
				modelAndView.addObject("catTypeSet", objectMapper.writeValueAsString(catTypeSet));
				modelAndView.addObject("documents", objectMapper.writeValueAsString(query.getDocuments()));
				
				Set<CategoryType> categoryTypes = null;
				try {
					categoryTypes = categoryTypeService.findAllCategoryType(FixitVariables.WITH_NOT_SURE);
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
				modelAndView.addObject("parentCategories", objectMapper
						.writeValueAsString(categoryTypeService.findAllParentCategory(FixitVariables.WITH_NOT_SURE)));
				modelAndView.addObject("myUser", user);
				
				modelAndView.addObject("allCategories", categoryTypes);
				modelAndView.setViewName("admin/editquestionadmin.jsp");
				return modelAndView;
			}

			Query savedQuery = queryService.saveQuery(query, request.getSession().getServletContext().getRealPath("/"),
					userId);
			if (savedQuery != null) {

				if (savedQuery.getFixerId().equals(DbConstants.ALL_FIXER)) {
					Set<User> fixers = userService.findFixerWhoMatchQueryCat(savedQuery.getQueryId());

					StringBuffer url = request.getRequestURL();
					String uri = request.getRequestURI();
					String ctx = request.getContextPath();
					String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
							savedQuery.getQueryId(), base);

					emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, savedQuery, queryExpireSet, base,
							null);
				} else {
					// email To
					User myCurrentUser = userService.findUserById(query.getFixerId());
					Set<User> fixers = new HashSet<User>();
					fixers.add(myCurrentUser);
					StringBuffer url = request.getRequestURL();
					String uri = request.getRequestURI();
					String ctx = request.getContextPath();
					String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
					Set<QueryExpire> queryExpireSet = queryExpireService.saveQueryExpire(fixers, user.getUserId(),
							savedQuery.getQueryId(), base);
					emailServcie.emailToFixerWhoMatchQueryCatAndQueryExpire(fixers, savedQuery, queryExpireSet, base,
							null);
				}
			}
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		
		modelAndView.addObject("currentPage", "dashboard");
		Integer selectedFixerId = query.getFixerId();
		if (selectedFixerId > 0) {
			User selectedFixer = userService.findUserById(selectedFixerId);
			if (selectedFixer != null) {
				modelAndView.addObject("selectedFixer", selectedFixer);
			}
		}
		switch (query.getStatus()) {
		case "notSure":
			modelAndView.setViewName("redirect:/admin/FixerForReview?msgKey=CA&status=notSure");
			break;

		case "unClaimed":
			modelAndView.setViewName("redirect:/admin/FixerForReview?msgKey=FA&status=unClaimed");
			break;
		}

		return modelAndView;
	}

	@RequestMapping(value = "/admin/questionDetailPage", method = RequestMethod.GET)
	public ModelAndView fixerQuestionDetailPage(@RequestParam String questionId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}

		ModelAndView mav = new ModelAndView();
		MemberDashboardBo memberDashboardBo = null;
		Query query = null;
		User user1 = null;
		try {
			query = queryService.getQueryByHashCode(questionId);
			memberDashboardBo = queryService.findQueryDetail(query, Calendar.getInstance(), user.getTimeZone());
			user1 = query.getUser();
		} catch (FixitException e) {
			e.printStackTrace();
		}
		mav.addObject("queryDetail", memberDashboardBo);
		mav.addObject("myUser", user1);
		mav.addObject("currentPage", "dashboard");

		mav.setViewName("admin/adminQuestionDetailPage.jsp");
		return mav;
	}

	@RequestMapping(value = "admin/fixerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchFixersAJAX(@RequestParam String searchFieldText, @RequestParam String catId,
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
			categoryId = Integer.parseInt(catId);
			categories.add(categoryId);
		} catch (Exception e) {
			categoryId = -1;
		}

		if (categoryId > 0) {
			fixers = userService.searchListOfUserswithCategory(userId, categories, searchFieldText, "0", "", -1, -1);

		} else {
			fixers = userService.searchListOfUsers(userId, searchFieldText, -1, -1);
		}
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

			if (categoryId > 0) {
				// 0 for average rating and "" for country name to get all
				// ratings and country
				fixers = userService.searchListOfUserswithCategory(userId, categories, searchFieldText, "0", "",
						startIndex, DbConstants.PAGE_SIZE);

			} else {
				fixers = userService.searchListOfUsers(userId, searchFieldText, startIndex, DbConstants.PAGE_SIZE);
			}

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;

							
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
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
		return Utility.getSuccessResponse("fixers", fixers, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "admin/fixerFavourite", method = RequestMethod.POST)
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

	@RequestMapping(value = "admin/favouriteFixerList", method = RequestMethod.POST)
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

	@RequestMapping(value = "admin/deleteFavouriteFixer", method = RequestMethod.POST)
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

	@RequestMapping(value = "/admin/download", method = RequestMethod.GET)
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

	@RequestMapping(value = "/admin/fixersList", method = RequestMethod.GET)
	public ModelAndView fixerListInAdminDashBoard(
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag,
			@RequestParam(value = "msgKey", required = false, defaultValue = "") String msgKey) throws FixitException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", "settings");
		List<FixerPayment> fixerPayment;
		int totalPage = 0;

		double totalSizeOfFixerList = adminService.fixersWithAmountToPaidCount();
		totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.PAGE_SIZE);
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
			fixerPayment = adminService.fixersWithAmountToPaid(startIndex, DbConstants.PAGE_SIZE);
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

			String message = "";
			String msgType = "";
			switch (msgKey) {

			case "FPNF":
				message = "Fixer doesn't have Paypal ID.";
				msgType = "Error";
				break;

			default:
				message = "";
				break;

			}
			mav.addObject("message", message);
			mav.addObject("msgType", msgType);
			mav.addObject("currentPage", "Fixers");
			mav.addObject("totalPage", totalPage);
			mav.addObject("fixerPaymentCount", totalPage);
			mav.addObject("fixerPayment", fixerPayment);
		} catch (Exception e) {

		}
		mav.setViewName("admin/adminFixersList.jsp");
		return mav;

	}

	@RequestMapping(value = "admin/searchFixer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fixerListAjax(String searchText,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) throws FixitException {
		Set<FixerPayment> fixers = new LinkedHashSet<FixerPayment>();
		;
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

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

			double totalSizeOfFixerList = adminService.searchListOfFixerUsingSearchCount(searchText);
			totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.PAGE_SIZE);
			fixers = null;
			fixers = adminService.searchListOfFixerUsingSearchText(searchText, startIndex, DbConstants.PAGE_SIZE);

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;

							
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
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
		return Utility.getSuccessResponse("fixers", fixers, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "admin/makeFixerPayment", method = RequestMethod.GET)
	public ModelAndView payToFixer(@RequestParam String amount, @RequestParam String fixerId,
			HttpServletRequest request) {
		User user = null;
		ModelAndView mav = new ModelAndView();
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		try {
			user = userService.findUserById(Integer.parseInt(fixerId));
			int fixerIntId = Integer.parseInt(fixerId);
			fixerAccountingService.updateFixerPaidStatus(fixerIntId, 1);

			mav.setViewName("redirect:/admin/fixersList");
		} catch (Exception e) {

		}

		

		return mav;
	}

	@RequestMapping(value = "admin/finalFixerPayment", method = RequestMethod.GET)
	public ModelAndView finalPaymentByAdminToFixer(HttpSession session) {
		String fixerId = null;
		int fixerIntId = -1;
		ModelAndView mav = new ModelAndView();
		try {
			String paykey = (String) session.getAttribute("PayKey");
			payPalMerchantToFixerPaymentService.finalPaymentStepExecuteAdminToFixerAdaptive(paykey);
			session.setAttribute("PayKey", null);
			

			if (session != null) {
				fixerId = (String) session.getAttribute("fixerId");
				try {
					fixerIntId = Integer.parseInt(fixerId);
				} catch (Exception e) {

				}
				if (fixerIntId >= 0) {
					try {
						fixerAccountingService.updateFixerPaidStatus(fixerIntId, 1);
						mav.setViewName("redirect:/admin/fixersList");
					} catch (Exception e) {

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public ModelAndView adminAllUsers(
			@RequestParam(value = "status", required = false, defaultValue = "Members") String status,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag,
			@RequestParam(value = "isDeleted", required = false, defaultValue = "") String isDeleted,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;
		int userId = -1;
		if (user != null) {
			userId = user.getUserId();
		}
		Set<User> users = null;
		int startIndex;
		switch (status) {
		case "Members":

			try {

				if (pageNo == 0) {
					startIndex = 0;
				} else {
					if (flag.equals("left")) {
						startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						if (flag.equals("right")) {
							startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						} else {
							startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						}
					}
				}
				users = adminService.getAllMember(-1, -1);
				double totalSizeOfFixerList = users.size();
				totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
				users = null;
				users = adminService.getAllMember(startIndex, DbConstants.ADMIN_USERS_PAGE_SIZE);
				if (users != null) {
					mav.addObject("totalUsersCount", users.size());
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("UserType", "C");
			break;

		case "Fixers":
			try {

				if (pageNo == 0) {
					startIndex = 0;
				} else {
					if (flag.equals("left")) {
						startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						if (flag.equals("right")) {
							startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						} else {
							startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						}
					}
				}
				users = adminService.getAllFixers(-1, -1);
				double totalSizeOfFixerList = users.size();
				totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
				users = null;
				users = adminService.getAllFixers(startIndex, DbConstants.ADMIN_USERS_PAGE_SIZE);
				if (users != null) {
					mav.addObject("totalUsersCount", users.size());
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("UserType", "F");
			break;

		default:
			try {
				if (pageNo == 0) {
					startIndex = 0;
				} else {
					if (flag.equals("left")) {
						startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						if (flag.equals("right")) {
							startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						} else {
							startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
						}
					}
				}
				users = adminService.getAllMember(-1, -1);
				double totalSizeOfFixerList = users.size();
				totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
				users = null;
				users = adminService.getAllMember(startIndex, DbConstants.ADMIN_USERS_PAGE_SIZE);

				if (users != null) {
					mav.addObject("totalUsersCount", users.size());
				}
			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			mav.addObject("UserType", "C");
			break;
		}
		switch (flag) {
		case "left":
			if (pageNo % DbConstants.PAGE_NO_DISPLAY == 1) {
				mav.addObject("startPage",
						(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1);
				if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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
					if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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
						if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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
					mav.addObject("endPage", ((pageNo / DbConstants.PAGE_NO_DISPLAY)) * DbConstants.PAGE_NO_DISPLAY);
				}

			} else {
				mav.addObject("startPage", (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
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
		String message = "";
		String msgType = "";
		switch (isDeleted) {
		case "MD":
			message = "Member has successfully deleted";
			msgType = "success";
			break;

		case "MND":
			message = "You cannot delete this member as it has posted some Request or Blog.";
			msgType = "Error";
			break;

		case "FD":
			message = "Fixer has successfully deleted";
			msgType = "success";
			break;

		case "FND":
			message = "You cannot delete this fixer as it has Responded some Request or posted Blog.";
			msgType = "Error";
			break;

		default:
			break;
		}
		mav.addObject("message", message);
		mav.addObject("msgType", msgType);

		mav.addObject("totalPage", totalPage);
		mav.addObject("allUsers", users);
		mav.addObject("currentPage", "users");
		mav.setViewName("admin/adminAllUsers.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/userFixerProfile", method = RequestMethod.GET)
	public ModelAndView myUserProfile(@RequestParam int fixerId) {
		ModelAndView mav = new ModelAndView();
		try {
			User myUser = userService.findUserById(fixerId);
			Calendar currentTime = Calendar.getInstance();
			String fixerImagePath = myUser.getProfilePhoto();
			mav.addObject("fixerImagePath", fixerImagePath);
			String lastLogin = TimeDiffUtility.findDiffString(
					TimeDiffUtility.findDayDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findHrsDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findMinutesDiff(myUser.getLastLogin(), currentTime),
					TimeDiffUtility.findSecondsDiff(myUser.getLastLogin(), currentTime));
			mav.addObject("lastLogin", lastLogin);
			mav.addObject("user", myUser);
			mav.addObject("industry", industryTypeService
					.findIndustryTypeListFromIndstIds(userIndustryService.findUserIndustryByUserId(fixerId)));
			Set<CategoryType> catType = categoryTypeService
					.findCategoryTypeListFromcatIds(userCategoryService.findUserCategoryByUserId(fixerId));
			mav.addObject("category", catType);
			List<Integer> listCatIds = new ArrayList<Integer>();
			for (CategoryType categoryType : catType) {
				listCatIds.add(Integer.valueOf(categoryType.getParentId()));
			}

			mav.addObject("parentCat", userService.findAllParentCategoryByCatId(listCatIds));

			if (fixerId != -1) {
				try {
					double fixerRating = fixerRatingService.getAggregateRatingsOfFixer(fixerId, myUser.getRating());
					if (fixerRating >= 0) {
						mav.addObject("fixerRating", fixerRating);
					}
					long totalQuery = queryAuditService.findAllQueryCountByFixerId(fixerId, "W");
					long responseTime = queryService.findAverageResponseTime(fixerId, "C");
					long resolvedQueries = queryAuditService.findAllQueryCountByFixerId(fixerId, "C");
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

		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		mav.addObject("User_Type", "admin");
		mav.addObject("currentPage", "users");

		mav.setViewName("profile/myFixerProfile.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/userMemberProfile", method = RequestMethod.GET)
	public ModelAndView myUserMemberProfile(@RequestParam int userId) {
		ModelAndView mav = new ModelAndView();
		try {
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
			mav.addObject("myUser", myUser);
		} catch (FixitException e) {
			
		} catch (Exception e) {
			
		}
		mav.addObject("User_Type", "admin");
		mav.addObject("currentPage", "users");
		mav.setViewName("profile/myProfile.jsp");
		return mav;

	}

	@RequestMapping(value = "/admin/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("privacy_policy.jsp");
		return mav;

	}

	@RequestMapping(value = "/admin/changeCredits", method = RequestMethod.POST)
	public ModelAndView changeCredits(@RequestParam("fixerId") String fixerId, @RequestParam("points") String points,
			HttpSession session) {

		ModelAndView mav = new ModelAndView();
		Integer fixerIntegerId = -1;
		Long pointsLong = (long) 0;
		try {
			fixerIntegerId = Integer.parseInt(fixerId);
			pointsLong = Long.parseLong(points);
		} catch (NumberFormatException e) {

		}
		if (fixerIntegerId != -1) {
			long pointIfUserExisted = userCreditService.getPoints(fixerIntegerId);

			if (pointIfUserExisted >= 0) {

				userCreditService.updatePoints(fixerIntegerId, pointsLong);
			} else {
				User user = userService.findUserById(fixerIntegerId);
				if (user != null) {
					try {
						userCreditService.saveUserCredits(user, pointsLong);
					} catch (FixitException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		mav.setViewName("redirect:/admin/users");
		return mav;
	}

	@RequestMapping(value = "/admin/userAgreement", method = RequestMethod.GET)
	public ModelAndView termAndCondition(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.addObject("myUser", user);
		mav.addObject("currentPage", "termCondNPolicy");
		mav.setViewName("term_condition.jsp");
		return mav;

	}

	@RequestMapping(value = "admin/searchUserMember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userMemberListAjax(String searchText,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) throws FixitException {
		Set<AdminMemberSearch> users = new LinkedHashSet<AdminMemberSearch>();
		;
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

		try {
			int startIndex;
			if (pageNo == 0) {
				startIndex = 0;
			} else {
				if (flag.equals("left")) {
					startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
				} else {
					if (flag.equals("right")) {
						startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					}
				}
			}

			double totalSizeOfFixerList = adminService.searchListOfUserUsingSearchCount(searchText);
			totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
			users = null;
			users = adminService.searchListOfUserUsingSearchText(searchText, startIndex,
					DbConstants.ADMIN_USERS_PAGE_SIZE);

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;

							
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
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
				/* mav.addObject("currentPageNo", pageNo); */
				break;

			default:
				break;
			}

		} catch (Exception e) {

		}
		return Utility.getSuccessResponse("fixers", users, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "admin/searchUserFixer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userListFixerAjax(String searchText,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) throws FixitException {
		Set<User> users = new LinkedHashSet<User>();
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

		try {
			int startIndex;
			if (pageNo == 0) {
				startIndex = 0;
			} else {
				if (flag.equals("left")) {
					startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
				} else {
					if (flag.equals("right")) {
						startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					}
				}
			}

			double totalSizeOfFixerList = adminService.searchListOfUserFixersUsingSearchCount(searchText);
			totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
			users = null;
			users = adminService.searchListOfUserFixersUsingSearchText(searchText, startIndex,
					DbConstants.ADMIN_USERS_PAGE_SIZE);

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;

						
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
								
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
			e.printStackTrace();
		}
		return Utility.getSuccessResponse("fixers", users, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "admin/searchChatUsers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userChatListAjax(String searchText,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) throws FixitException {
		Set<User> users = new LinkedHashSet<User>();
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

		try {
			int startIndex;
			if (pageNo == 0) {
				startIndex = 0;
			} else {
				if (flag.equals("left")) {
					startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
				} else {
					if (flag.equals("right")) {
						startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					}
				}
			}

			double totalSizeOfFixerList = adminService.searchListOfChatUserUsingSearchCount(searchText);
			totalPage = (int) Math.ceil(totalSizeOfFixerList / DbConstants.ADMIN_USERS_PAGE_SIZE);
			users = null;
			users = adminService.searchListOfChatUserUsingSearchText(searchText, startIndex,
					DbConstants.ADMIN_USERS_PAGE_SIZE);

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
							
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;

							
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
							
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
		return Utility.getSuccessResponse("chatUsers", users, "startPage", startPage, "endPage", endPage,
				"currentPageNo", currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "/admin/saveNewPassword", method = RequestMethod.POST)
	public ModelAndView saveNewAdminPassword(@Validated @ModelAttribute("newPassword") NewPassword newPassword,
			BindingResult result, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		newPasswordValidator.setMyCurrentUser(user);
		newPasswordValidator.validate(newPassword, result);
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
		mav.setViewName("admin/adminSettings.jsp");
		return mav;

	}

	@RequestMapping(value = "/admin/latestFeeds", method = RequestMethod.GET)
	public ModelAndView findlatetstFeeds(
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) {
		ModelAndView mav = new ModelAndView();
		int startIndex = Pagination.findStartIndex(pageNo, flag);
		try {
			long totalQueryAudit = queryAuditService.findAllQueryAuditCount();
			int totalPage = (int) Math.ceil((float) totalQueryAudit / DbConstants.PAGE_FEED_SIZE);
			Set<QueryAudit> queryAuditSet = queryAuditService.findAllQueryAudit(startIndex, DbConstants.PAGE_FEED_SIZE);
			Set<LatestFeeds> latestFeed = new LinkedHashSet<LatestFeeds>();
			LatestFeeds feed;
			User member, fixer;
			Calendar currentTime = Calendar.getInstance();
			long count = startIndex;
			for (QueryAudit queryAudit : queryAuditSet) {
				feed = new LatestFeeds();
				count++;
				feed.setFeedNo(count);
				member = userService.findUserById(queryAudit.getCustomerId());
				fixer = userService.findUserById(queryAudit.getFixerId());
				feed.setMemberName(member.getUserName());
				if (queryAudit.getFixerId().equals(0)) {
					feed.setFixerName("open");
				} else {
					feed.setFixerName(fixer.getUserName());
				}
				feed.setMessage(queryAudit.getMessage());
				feed.setMessageFrom(queryAudit.getMsgFrom());
				feed.setQueryTitle(queryAudit.getQuery().getQueryTitle());
				feed.setStatus(queryAudit.getStatus());
				feed.setTimeDiff(TimeDiffUtility.findDiffString(
						TimeDiffUtility.findDayDiff(queryAudit.getAuditDate(), currentTime),
						TimeDiffUtility.findHrsDiff(queryAudit.getAuditDate(), currentTime),
						TimeDiffUtility.findMinutesDiff(queryAudit.getAuditDate(), currentTime),
						TimeDiffUtility.findSecondsDiff(queryAudit.getAuditDate(), currentTime)));
				latestFeed.add(feed);
			}
			mav = Pagination.paginate(mav, pageNo, flag, totalPage);
			mav.addObject("latestFeed", latestFeed);

		} catch (Exception e) {
			
		}
		mav.addObject("currentPage", "latestFeeds");
		mav.setViewName("admin/latestfeeds.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/deleteMember", method = RequestMethod.GET)
	public ModelAndView deleteMemberStuff(@RequestParam String userId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		try {
			Integer intUserID = Integer.parseInt(userId);
			int userdeleted = userService.deleteUserAllData(intUserID);
			if (userdeleted == 1) {
				mav.setViewName("redirect:/admin/users?isDeleted=MD");
			} else {
				mav.setViewName("redirect:/admin/users?isDeleted=MND");
			}
		} catch (Exception e) {
			mav.setViewName("redirect:/admin/users?isDeleted=MND");
		}
		return mav;
	}

	@RequestMapping(value = "/admin/deleteFixer", method = RequestMethod.GET)
	public ModelAndView deleteFixerStuff(@RequestParam String userId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		try {
			Integer intUserID = Integer.parseInt(userId);
			int userdeleted = userService.deleteUserAllData(intUserID);
			if (userdeleted == 1) {
				mav.setViewName("redirect:/admin/users?isDeleted=FD");
			} else {
				mav.setViewName("redirect:/admin/users?isDeleted=FND");
			}
		} catch (Exception e) {
			mav.setViewName("redirect:/admin/users?status=Fixers&isDeleted=FND");
		}
		return mav;
	}

	@RequestMapping(value = "/admin/fixedIssueList", method = RequestMethod.GET)
	public ModelAndView fixedIssueList(@RequestParam int fixerId) {
		ModelAndView mav = new ModelAndView();
		Set<Query> fixedIssueList = new HashSet<Query>();
		User user = new User();
		;
		try {
			user = userService.findUserById(fixerId);
			fixedIssueList = adminService.fixedIssueListByFixerId(fixerId, -1, -1);
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		mav.addObject("fixedIssueList", fixedIssueList);
		mav.addObject("currentPage", "Fixers");
		mav.addObject("user", user);
		mav.setViewName("admin/fixedIssueList.jsp");
		return mav;
	}

	@RequestMapping(value = "/admin/chat", method = RequestMethod.GET)
	public ModelAndView adminChatFunctionality(
			@RequestParam(value = "status", required = false, defaultValue = "Users") String status) {
		ModelAndView mav = new ModelAndView();
		Set<UserGroupsList> users = new LinkedHashSet<UserGroupsList>();
		try {
			switch (status) {
			case "Users":
				users = userService.getAllMemberAndActivatedFixers(-1, -1);
				mav.addObject("UserType", "users");
				break;

			case "Chat":
				

				int adminUserId = -1;
				Set<User> admin = userService.findAdminByStatus(DbConstants.ADMIN);
				for (User adminId : admin) {
					adminUserId = adminId.getUserId();
					break;
				}

				users = chatGroupsService.FindChatGroupNameByAdminId(adminUserId);

				mav.addObject("UserType", "chat");
				break;
			default:
				users = userService.getAllMemberAndActivatedFixers(-1, -1);
				mav.addObject("UserType", "users");
				break;
			}

			mav.addObject("allUsers", users);
			mav.addObject("currentPage", "chat");
			mav.setViewName("admin/adminChatPage.jsp");
			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/admin/adminChatDetail", method = RequestMethod.GET)
	public ModelAndView adminChatDetail(@RequestParam String userId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Integer> addUserID = new ArrayList<Integer>();
		User user = (User) session.getAttribute("user");
		int myUserid = user.getUserId();
		Integer toUserIdInteger = Integer.parseInt(userId);
		addUserID.add(toUserIdInteger);
		addUserID.add(myUserid);
		Set<ChatMessageBo> chatMessageSet = new LinkedHashSet<ChatMessageBo>();
		try {
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(addUserID);
			if (chatUserGroup != null) {
				chatMessageSet = chatMessageService
						.getAllChatMessagesBasedOnGroupId(chatUserGroup.getChatGroups().getChatGroupId(), user);
			}

			User otherUser = userService.findUserById(toUserIdInteger);
			mav.addObject("otherUserName", otherUser.getFirstName());
			mav.addObject("otherUserImgIcon", otherUser.getProfileIcon());
			mav.addObject("myImgIcon", user.getProfileIcon());
			mav.addObject("currentPage", "chat");
			mav.addObject("messagesSet", chatMessageSet);
			mav.addObject("toUserId", userId);
			if (chatUserGroup != null) {
				mav.addObject("groupName", chatUserGroup.getChatGroups().getChatGroupName());
			} else {
				mav.addObject("groupName", otherUser.getUserName());
			}
			mav.addObject("myUserId", myUserid);
			mav.setViewName("admin/adminChatDetailPage.jsp");

			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/admin/sendChatMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adminSendChatMessage(@RequestParam String message, @RequestParam String toUserId,
			HttpSession session,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		Integer toUserIdInteger = Integer.parseInt(toUserId);
		ChatGroups chatGroups = null;
		ChatMessage chatMessage = null;
		List<Integer> addUserID = new ArrayList<Integer>();
		addUserID.add(toUserIdInteger);
		addUserID.add(userId);
       User userMemberFixer = userService.findUserById(Integer.valueOf(toUserId));
		try {
			ChatUserGroup chatUserGroup = chatUserGroupService.findChatUserGroupBasedOnIds(addUserID);
			if (chatUserGroup != null) {
				chatMessage = chatMessageService.saveChatMessage(message, user, chatUserGroup.getChatGroups());
				messageNotificationService.saveMessageNotification(chatMessage, toUserIdInteger, false);
			} else {
				chatGroups = chatGroupsService.saveChatGroup(toUserIdInteger);
				chatUserGroupService.saveChatUserGroup(addUserID, chatGroups);
				chatMessage = chatMessageService.saveChatMessage(message, user, chatGroups);
				messageNotificationService.saveMessageNotification(chatMessage, toUserIdInteger, false);
			}
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			emailServcie.emailToUserChatMessageByAdmin(message, userMemberFixer, base);
			mav.addObject("status", "success");
			mav.setViewName("admin/adminReviewQueries.jsp");
			return Utility.getSuccessResponse("status", "success");
		} catch (FixitException e) {
			
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@RequestMapping(value = "/admin/adminGroupDetail", method = RequestMethod.GET)
	public ModelAndView adminGroupDetail(@RequestParam String groupId, HttpSession session) {
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

			mav.addObject("myImgIcon", user.getProfileIcon());
			mav.addObject("currentPage", "chat");
			mav.addObject("messagesSet", chatMessageSet);
			mav.addObject("groupId", groupId);
			mav.addObject("groupName", chatGroups.getChatGroupName());
			mav.addObject("myUserId", user.getUserId());
			mav.setViewName("admin/adminGroupDetailPage.jsp");

			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/admin/sendGroupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adminSendGroupMessage(@RequestParam String message, String groupId,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();

		ChatGroups chatGroups = null;
		try {
			Integer groupIdInteger = Integer.parseInt(groupId);
			chatGroups = chatGroupsService.findChatGroupBasedOnGroupId(groupIdInteger);
			chatMessageService.saveChatMessage(message, user, chatGroups);

			mav.addObject("status", "success");
			return Utility.getSuccessResponse("status", "success");
		} catch (FixitException e) {
			
			return Utility.getSuccessResponse("status", "failed");
		}
	}

	@RequestMapping(value = "/admin/adminMailFixers", method = RequestMethod.GET)
	public ModelAndView adminMailFixers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", "chat");
		mav.addObject("userType", "F");
		mav.setViewName("admin/adminMailToAllUsers.jsp");
		return mav;

	}

	@RequestMapping(value = "/admin/adminMailMembers", method = RequestMethod.GET)
	public ModelAndView adminMailMembers(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", "chat");
		mav.addObject("userType", "C");
		mav.setViewName("admin/adminMailToAllUsers.jsp");
		return mav;

	}

	@RequestMapping(value = "/admin/sendEmailToUsers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendEmailUsers(@RequestParam String message, @RequestParam String subject,
			@RequestParam String userType, HttpServletRequest request) throws FixitException {
		Set<User> users = new LinkedHashSet<User>();

		try {

			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			users = null;
			if (userType.equals("F")) {
				users = adminService.getListOfFixerWithActivtedStatus("A", "F", -1, -1);
			} else {
				users = adminService.getAllMember(-1, -1);
			}
			emailServcie.emailToAllUsers(users, message, subject, base, userType);
		} catch (FixitException e) {

		}

		return Utility.getSuccessResponse("mailUsers", users, "message", message);

	}

	@RequestMapping(value = "/admin/asugUser", method = RequestMethod.GET)
	public ModelAndView asugUser(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag,
			@RequestParam(value = "msgKey", required = false, defaultValue = "") String msgKey) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", "asug");
		Set<User> userBySource;
		int totalPage = 0;

		double totalSizeOfuserBySource = userService.findCountUserBySource("1");
		totalPage = (int) Math.ceil(totalSizeOfuserBySource / DbConstants.PAGE_SIZE);
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
			userBySource = userService.findUserBySource("1", startIndex, DbConstants.PAGE_SIZE);

			
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

			mav.addObject("currentPage", "Fixers");
			mav.addObject("totalPage", totalPage);
			mav.addObject("userBySourceCount", totalPage);
			mav.addObject("userBySource", userBySource);
		} catch (Exception e) {

		}
		mav.setViewName("admin/admin_asug_user.jsp");
		return mav;

	}

	@RequestMapping(value = "admin/searchAsugUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchAsugUser(@RequestParam String searchText,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag) throws FixitException {
		Set<User> users = null;
		Integer totalPage = -1;
		Integer startPage = null, endPage = null, currentPageNo = null;

		try {
			int startIndex;
			if (pageNo == 0) {
				startIndex = 0;
			} else {
				if (flag.equals("left")) {
					startIndex = (pageNo - 2) * DbConstants.ADMIN_USERS_PAGE_SIZE;
				} else {
					if (flag.equals("right")) {
						startIndex = (pageNo) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					} else {
						startIndex = (pageNo - 1) * DbConstants.ADMIN_USERS_PAGE_SIZE;
					}
				}
			}

			double totalSizeOfuserBySource = userService.findCountUserBySourceAndName("1", searchText);

			totalPage = (int) Math.ceil(totalSizeOfuserBySource / DbConstants.ADMIN_USERS_PAGE_SIZE);
			users = null;
			users = userService.findUserBySourceAndName("1", searchText, startIndex, DbConstants.ADMIN_USERS_PAGE_SIZE);

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
						startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1;
						if (((((pageNo / DbConstants.PAGE_NO_DISPLAY))) * DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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
							startPage = (DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY))) + 1;
							endPage = totalPage;
						} else {
							startPage = (DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1;
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								endPage = totalPage;
							} else {
								endPage = ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1) * DbConstants.PAGE_NO_DISPLAY;
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
			e.printStackTrace();
		}
		return Utility.getSuccessResponse("users", users, "startPage", startPage, "endPage", endPage, "currentPageNo",
				currentPageNo, "totalPage", totalPage);

	}

	@RequestMapping(value = "/admin/exportExcel", method = RequestMethod.GET)
	public ModelAndView excelExportAsugUser(
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(value = "flag", required = false, defaultValue = "right") String flag,
			@RequestParam(value = "msgKey", required = false, defaultValue = "") String msgKey,
			@RequestParam Integer year, @RequestParam Integer month) {
		Map<String, Object> asugData = new HashMap<String, Object>();
		try {
			asugData = adminService.asugData(year, month);
			List<AsugUserData> list = (List<AsugUserData>) asugData.get("asugUserData");
			if (list.size() == 0) {
				ModelAndView mav = new ModelAndView();

				mav.addObject("currentPage", "asug");
				Set<User> userBySource;
				int totalPage = 0;

				double totalSizeOfuserBySource = userService.findCountUserBySource("1");
				totalPage = (int) Math.ceil(totalSizeOfuserBySource / DbConstants.PAGE_SIZE);
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
					userBySource = userService.findUserBySource("1", startIndex, DbConstants.PAGE_SIZE);

					
					switch (flag) {
					case "left":
						if (pageNo % DbConstants.PAGE_NO_DISPLAY == 1) {
							mav.addObject("startPage",
									(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1)) + 1);
							if (((((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
								mav.addObject("endPage", totalPage);
							} else {
								mav.addObject("endPage", (((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1) + 1)
										* DbConstants.PAGE_NO_DISPLAY);
							}

						} else {
							if (pageNo % DbConstants.PAGE_NO_DISPLAY == 0) {
								mav.addObject("startPage",
										(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY) - 1))
												+ 1);
								if (((((pageNo / DbConstants.PAGE_NO_DISPLAY)))
										* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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
									mav.addObject("endPage", (((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1))
											* DbConstants.PAGE_NO_DISPLAY);
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
											(DbConstants.PAGE_NO_DISPLAY * ((pageNo / DbConstants.PAGE_NO_DISPLAY)))
													+ 1);
									mav.addObject("endPage", totalPage);
								} else {
									mav.addObject("startPage",
											(DbConstants.PAGE_NO_DISPLAY * (pageNo / DbConstants.PAGE_NO_DISPLAY)) + 1);
									if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
											* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
										mav.addObject("endPage", totalPage);
									} else {
										mav.addObject("endPage", ((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
												* DbConstants.PAGE_NO_DISPLAY);
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
							if ((((pageNo / DbConstants.PAGE_NO_DISPLAY) + 1)
									* DbConstants.PAGE_NO_DISPLAY) >= totalPage) {
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

					mav.addObject("currentPage", "Fixers");
					mav.addObject("totalPage", totalPage);
					mav.addObject("userBySourceCount", totalPage);
					mav.addObject("userBySource", userBySource);
				} catch (Exception e) {

				}
				mav.addObject("excelDataList", "0");
				mav.setViewName("admin/admin_asug_user.jsp");
				return mav;
			}
		} catch (FixitException e) {
			e.printStackTrace();
		}
		return new ModelAndView("ExcelAsugDataSummary", "asugData", asugData);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/updateFixerRatingByAdmin", method = RequestMethod.POST)
	public Map<String, Object> updateFixerRatingByAdmin(@RequestParam int rating, @RequestParam int userId) {
		int i = 0;
		try {
			User fixer = userService.findUserById(userId);
			if (fixer.getUserType().equals("F")) {
				i = adminService.updateFixerRatingByAdmin(rating, userId);
			}
			return Utility.getSuccessResponse("update", i);
		} catch (FixitException e) {
			
			e.printStackTrace();
			return Utility.getFailureResponse();
		}

	}
}
