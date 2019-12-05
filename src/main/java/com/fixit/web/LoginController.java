package com.fixit.web;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixit.config.CustomAuthenticationFailureHandler;
import com.fixit.config.CustomAuthenticationSuccessHandler;
import com.fixit.dao.CategoryTypeDao;
import com.fixit.dao.VerificationDao;
import com.fixit.domain.bo.FixerProfile;
import com.fixit.domain.bo.FixerRegisterBo;
import com.fixit.domain.bo.MemberSignUpProfile;
import com.fixit.domain.bo.UserBo;
import com.fixit.domain.bo.ValidatePassword;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.User;
import com.fixit.domain.vo.Verification;
import com.fixit.service.CountryService;
import com.fixit.service.EmailServcie;
import com.fixit.service.QueryExpireService;
import com.fixit.service.QueryService;
import com.fixit.service.RandomConverter;
import com.fixit.service.UserCategoryService;
import com.fixit.service.UserCreditService;
import com.fixit.service.UserService;
import com.fixit.service.VerificationService;
import com.fixit.utility.EmailUtility;
import com.fixit.utility.FileUpload;
import com.fixit.utility.FixitException;
import com.fixit.utility.MailChimpApi;
import com.fixit.utility.OAuthServiceProvider;
import com.fixit.utility.PasswordEncode;
import com.fixit.utility.SessionAttributes;
import com.fixit.utility.UserRoleEnum;
import com.fixit.utility.UserTypeEnum;
import com.fixit.utility.Utility;
import com.fixit.validation.FixerFormValidator;
import com.fixit.validation.MemberFormValidator;
import com.fixit.validation.PasswordValidation;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

@Controller("LoginController")
public class LoginController {
	@Autowired
	private UserCategoryService fixerCategoryService;

	private static final String MSG_BAD_CREDENTIALS = null;

	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.stream.read "
			+ "https://www.googleapis.com/auth/plus.stream.write";

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private QueryExpireService queryExpireService;

	@Autowired
	private CategoryTypeDao categoryTypeDao;

	@Autowired
	MemberFormValidator memberFormValidator;

	@Autowired
	private CountryService countryService;

	@Autowired
	UserCreditService userCreditService;

	@Autowired
	FixerFormValidator fixerFormValidator;

	@Autowired
	private EmailServcie emailServcie;

	@Autowired
	private RandomConverter randomConverter;

	@Autowired
	private VerificationDao verificationDao;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private PasswordValidation passwordValidation;

	@Autowired
	@Qualifier("linkedInServiceProvider")
	private OAuthServiceProvider linkedInServiceProvider;

	@Autowired
	@Qualifier("googleServiceProvider")
	private OAuthServiceProvider googleServiceProvider;

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public static Map<String, String> timeZoneMap = new LinkedHashMap<String, String>();

	/*
	 * @InitBinder protected void initBinder(WebDataBinder binder) {
	 * binder.setValidator(customerFormValidator); }
	 */
	/**
	 * 
	 * @return
	 */
	@RequestMapping({ "/", "/index" })
	public ModelAndView firstPage() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("flag", "Home");
		
		mav.setViewName("redirect:http://www.erpfixers.com");
		

		return mav;
	}

	@RequestMapping({ "/whatIsAMember" })
	public ModelAndView whatIsAMember() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("howItWorks", "active");
		mav.setViewName("/whatIsAMember.jsp");
		return mav;
	}

	@RequestMapping({ "/whatIsAFixer" })
	public ModelAndView whatIsAFixer() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("howItWorks", "active");
		mav.setViewName("/whatIsAFixer.jsp");
		return mav;
	}

	@RequestMapping({ "/examplesOfRequests" })
	public ModelAndView examplesOfRequests() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("howItWorks", "active");
		mav.setViewName("/examplesOfRequests.jsp");
		return mav;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping({ "/signup" })
	public ModelAndView signUpPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/register/signup.jsp");

		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView signInPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "newMember", required = false) String newMember,
			@RequestParam(value = "newFixer", required = false) String newFixer, 
			@RequestParam(value = "msgType", required = false) String msgType,
			@RequestParam(value = "msg", required = false) String msg,
			HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		if (session != null) {
			com.fixit.domain.vo.User user = (com.fixit.domain.vo.User) session.getAttribute("user");
			if (user != null) {
				if (UserTypeEnum.F.toString().equals(user.getUserType())) {
					
					if (session.getAttribute("emailToFixerCode") != null) {
						String hashcode = (String) session.getAttribute("emailToFixerCode");
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {
							
							e.printStackTrace();
						}
						if (queryExpire != null) {
							Query query = null;
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("emailToFixerCode", null);
								mav.setViewName("redirect:/fixer/request?status=Unclaimed&msgKey=R");
							} else {
								mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
						}

						return mav;
					}
					if (session.getAttribute("emailCode") != null) {
						String hashcode = (String) session.getAttribute("emailCode");
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {
							
							e.printStackTrace();
						}
						if (queryExpire != null) {
							Query query = null;
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("emailCode", null);
								mav.setViewName("redirect:/fixer/request?status=Unclaimed&msgKey=R");
							} else {
								mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
						}

						return mav;
					}
					if (session.getAttribute("notFixedQuery") != null) {
						String hashcode = (String) session.getAttribute("notFixedQuery");
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {
							
							e.printStackTrace();
						}
						if (queryExpire != null) {
							Query query = null;
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("notFixedQuery", null);
								mav.setViewName("redirect:/fixer/closedQueryPage?queryId=" + query.getHashcode());
							} else {
								mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
						}
						return mav;
					}

					if (session.getAttribute("fixerChat") != null) {
						String hashcode = (String) session.getAttribute("fixerChat");
						String senderId = (String) session.getAttribute("senderId");
						Query query = null;
						try {
							query = queryService.getQueryByHashCode(hashcode);
						} catch (FixitException e) {
							e.printStackTrace();
						}
						if (query != null) {
							session.setAttribute("fixerChat", null);
							session.setAttribute("senderId", null);
							if(query.getCurrentStatus().equals("O"))
							{
								
								mav.setViewName("redirect:/fixer/request?msgKey=R&status=Applied&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								mav.setViewName("redirect:/fixer/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								mav.setViewName("redirect:/fixer/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							
						} else {
							mav.setViewName("redirect:/fixer/dashBoard?msgKey=EX");
						}
						return mav;

					}

				}
				if (UserTypeEnum.C.toString().equals(user.getUserType())) {
					
					if (session.getAttribute("fixerApplyCode") != null) {

						String hashcode = (String) session.getAttribute("fixerApplyCode");
						Query query = null;
						
						try {
							query = queryService.getQueryByHashCode(hashcode);
						} catch (FixitException e) {
							e.printStackTrace();
						}
						if (query != null) {
							session.setAttribute("fixerApplyCode", null);
							
								mav.setViewName("redirect:/member/request?msgKey=R&applyQueryCode=" + query.getQueryId());
														
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}
						return mav;

					}
					if (session.getAttribute("memberRespond") != null) {

						String hashcode = (String) session.getAttribute("memberRespond");
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {
							e.printStackTrace();
						}
						if (queryExpire != null) {
							Query query = null;
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("memberRespond", null);
								mav.setViewName(
										"redirect:/member/memberQueryDetailPage?queryId=" + query.getHashcode());
							} else {
								mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}

						return mav;

					}
					if (session.getAttribute("memberRequestNewFixer") != null) {
						String hashcode = (String) session.getAttribute("memberRequestNewFixer");
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {
							e.printStackTrace();
						}
						if (queryExpire != null) {
							Query query = null;
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("memberRequestNewFixer", null);
								mav.setViewName("redirect:/member/editQuestion?queryId=" + query.getHashcode());
							} else {
								mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}

						return mav;

					}

					if (session.getAttribute("adminNotSure") != null) {
						String hashcode = (String) session.getAttribute("adminNotSure");
						QueryExpire queryExpire = null;
						Query query = null;

						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (FixitException e) {

						}
						if (queryExpire != null) {
							try {

								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("adminNotSure", null);
								mav.setViewName("redirect:/member/editQuestion?queryId=" + query.getHashcode());
							} else {
								mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}

						return mav;

					}

					if (session.getAttribute("adminUnClaimed") != null) {
						String hashcode = (String) session.getAttribute("adminUnClaimed");
						Query query = null;
						QueryExpire queryExpire = null;
						try {
							queryExpire = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),
									hashcode);
						} catch (Exception e) {
						}
						if (queryExpire != null) {
							try {
								query = queryService.findQueryByQueryId(queryExpire.getQueryId());
							} catch (FixitException e) {
								e.printStackTrace();
							}
							if (query != null) {
								session.setAttribute("adminUnClaimed", null);
								mav.setViewName("redirect:/member/editQuestion?queryId=" + query.getHashcode());
							} else {
								mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
							}
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}
						return mav;

					}

					if (session.getAttribute("memberChat") != null) {
						String hashcode = (String) session.getAttribute("memberChat");
						String senderId = (String) session.getAttribute("senderId");
						Query query = null;
						
						try {
							query = queryService.getQueryByHashCode(hashcode);
						} catch (FixitException e) {
							e.printStackTrace();
						}
						if (query != null) {
							session.setAttribute("memberChat", null);
							session.setAttribute("senderId", null);
							if(query.getCurrentStatus().equals("O"))
							{
								mav.setViewName("redirect:/member/request?msgKey=R&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								mav.setViewName("redirect:/member/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								mav.setViewName("redirect:/member/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							
						} else {
							mav.setViewName("redirect:/member/dashBoard?msgKey=EX");
						}
						return mav;

					}

				}

				if (UserTypeEnum.A.toString().equals(user.getUserType())) {
					mav.setViewName("redirect:/admin/FixerForReview?msgKey=R");

				}
			}
		}

		Collection<? extends GrantedAuthority> roleList = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (roleList.size() > 0) {
			if (roleList.contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_FIXER.toString()))) {

				mav.setViewName("redirect:/fixer/dashBoard?msgKey=R");
			} else if (roleList.contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_MEMBER.toString()))) {

				mav.setViewName("redirect:/member/dashBoard?msgKey=R");
			} else if (roleList.contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.toString()))) {
				mav.setViewName("redirect:/admin/FixerForReview?msgKey=R");
				
			} else {
				if (error != null) {
					if ("R".equals(error)) {
						mav.addObject("error", "Fixer is currently in Review!");
					} else if ("D".equals(error)) {
						mav.addObject("error", "Fixer is deactivated by Admin!");
					} else if ("SNF".equals(error)) {
						mav.addObject("error", "Please SignUp first before login");
					} else {

						mav.addObject("error", "Invalid username and password!");
					}
				}

				if (logout != null) {
					mav.addObject("msg", "You've been logged out successfully.");
				}

				if (newMember != null) {
					mav.addObject("newMember",
							"You have successfully signed up. Please use your credentials to log in.");
				}

				if (newFixer != null) {
					mav.addObject("newFixer", "You have succesfully signed up. Please wait for the admin approval.");
				}
				if(msg!=null && msgType!=null){
					mav.addObject("msg", msg);
					mav.addObject("msgType", msgType);
				}

				mav.setViewName("login/login.jsp");
			}
		} else {
		}
		return mav;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * 
	 * 		/**
	 * 
	 */
	@RequestMapping(value = "/memberSignUp", method = RequestMethod.GET)
	public ModelAndView userRegister(
			@RequestParam(value = "flag", required = false, defaultValue = "false") String flag,
			@RequestParam(value = "ref", required = false, defaultValue = "NONE") String ref) {
		ModelAndView mav = new ModelAndView();
		UserBo member = new UserBo();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			mav.addObject("load", true);
			mav.addObject("member", member);
			mav.addObject("countryList", countryService.findAllCountry());
			mav.addObject("flag", flag);
			mav.addObject("isPopUp", true);
			mav.addObject("ref", ref);
			mav.setViewName("signUp/memberSignUp.jsp");
		} catch (Exception e) {

		}

		return mav;
	}

	/**
	 * 
	 * @param userBo
	 * @param result
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */

	@RequestMapping(value = "/memberSignUp", method = RequestMethod.POST)
	ModelAndView userRegister(@Validated @ModelAttribute("member") UserBo userBo, BindingResult result, Model model,
			HttpServletRequest request, HttpSession session, final RedirectAttributes redirectAttributes)
			throws JsonProcessingException {
		ModelAndView mav = new ModelAndView();
		if (userBo.isSocialLogin()) {
			userBo.setOverview("");
			

			String randomPassword = randomConverter.alphaNumericGenerator(10);
			userBo.setPassword(randomPassword);

		}
		memberFormValidator.validate(userBo, result);
		 if (userBo.getSendEmail() == null)
		 userBo.setSendEmail("N");
		 else
		 userBo.setSendEmail("Y");
		ObjectMapper objectMapper = new ObjectMapper();
		MemberSignUpProfile memberProfile = new MemberSignUpProfile();
		userBo.setUserType("C");
		model.addAttribute("user", userBo);
		if (result.hasErrors()) {
			try {

				mav.addObject("countryList", countryService.findAllCountry());

			} catch (FixitException e) {
				
				e.printStackTrace();
			}
			List<FieldError> fieldErrors = result.getFieldErrors();
			List<String> listErrors = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				listErrors.add(fieldErrors.get(i).getField());

			}
			mav.addObject("errorFields", objectMapper.writeValueAsString(listErrors));
			mav.addObject("errors", true);
			mav.addObject("load", true);
			mav.addObject("flag", "true");
			mav.addObject("isPopUp", false);
			mav.setViewName("signUp/memberSignUp.jsp");
		} else {
			com.fixit.domain.vo.User user;
			try {

				if (userBo != null) {
					memberProfile.setUserId(userBo.getUserId());
					memberProfile.setFirstName(userBo.getFirstName());
					memberProfile.setLastName(userBo.getLastName());
					memberProfile.setCity(userBo.getCity());
					memberProfile.setCountry(userBo.getCountry());
					memberProfile.setEmail(userBo.getEmail());
					
					memberProfile.setPassword(userBo.getPassword());
					memberProfile.setUserName(userBo.getUserName());
					memberProfile.setUserType(userBo.getUserType());
					memberProfile.setTimeZone(userBo.getTimeZone());
					memberProfile.setSendEmail(userBo.getSendEmail());
					memberProfile.setOverview(userBo.getOverview());
					memberProfile.setSource(userBo.getSource());
					

				}
				redirectAttributes.addFlashAttribute("memberProfile", memberProfile);
				mav.setViewName("redirect:/member/profile");
			} catch (Exception e) {
				return null;
			}
		}
		return mav;
	}

	@RequestMapping(value = "/fixerSignUp", method = RequestMethod.GET)
	public ModelAndView fixerRegister() {
		ModelAndView mav = new ModelAndView();
		FixerRegisterBo fixer = new FixerRegisterBo();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			mav.addObject("load", true);
			mav.addObject("fixer", fixer);
			mav.addObject("countryList", countryService.findAllCountry());
			mav.setViewName("signUp/fixerSignUp.jsp");
		} catch (Exception e) {

		}

		return mav;
	}

	@RequestMapping(value = "/fixerSignUp", method = RequestMethod.POST)
	ModelAndView fixerRegister(@Validated @ModelAttribute("fixer") FixerRegisterBo fixerRegisterBo,
			BindingResult result, Model model, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		if (fixerRegisterBo.getSendEmail() == null){
			fixerRegisterBo.setSendEmail("N");}
		else{
		fixerRegisterBo.setSendEmail("Y");
		}
		if (fixerRegisterBo.isSocialLogin()) {
			fixerRegisterBo.setOverview("");
			
			
			String randomPassword = randomConverter.alphaNumericGenerator(10);
			fixerRegisterBo.setPassword(randomPassword);

		}

		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		fixerFormValidator.validate(fixerRegisterBo, result);
		fixerRegisterBo.setUserType("F");
		fixerRegisterBo.setFixerStatus("R");
		model.addAttribute("fixer", fixerRegisterBo);
		if (result.hasErrors()) {
			mav.addObject("errors", true);

			List<FieldError> fieldErrors = result.getFieldErrors();
			List<String> listErrors = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				listErrors.add(fieldErrors.get(i).getField());

			}
			try {
				mav.addObject("errorFields", objectMapper.writeValueAsString(listErrors));
				mav.addObject("load", false);
				mav.addObject("countryList", countryService.findAllCountry());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (FixitException e) {
				e.printStackTrace();
			}
			mav.setViewName("signUp/fixerSignUp.jsp");
		} else {
			com.fixit.domain.vo.User user;
			try {

				fixerRegisterBo.setFixerStatus("R");

				FixerProfile profile = new FixerProfile();
				if (fixerRegisterBo != null) {
					
					
					profile.setSocialLogin(fixerRegisterBo.isSocialLogin());
					profile.setUserId(fixerRegisterBo.getUserId());
					profile.setFirstName(fixerRegisterBo.getFirstName());
					profile.setLastName(fixerRegisterBo.getLastName());
					profile.setCity(fixerRegisterBo.getCity());
					profile.setCountry(fixerRegisterBo.getCountry());
					profile.setEmail(fixerRegisterBo.getEmail());
					profile.setFixerStatus(fixerRegisterBo.getFixerStatus());
					profile.setLinkedinProfile(fixerRegisterBo.getLinkedinProfile());
					
					profile.setPassword(PasswordEncode.encodePassword(fixerRegisterBo.getPassword()));
					profile.setUserName(fixerRegisterBo.getUserName());
					profile.setMobileNumber(fixerRegisterBo.getMobileNumber());
					profile.setUserType(fixerRegisterBo.getUserType());
					profile.setTimeZone(fixerRegisterBo.getTimeZone());
					profile.setSendEmail(fixerRegisterBo.getSendEmail());
				    profile.setImageUrl(fixerRegisterBo.getImageUrl());
				  
					profile.setOverview(fixerRegisterBo.getOverview());

				}
				redirectAttributes.addFlashAttribute("profile", profile);
				mav.setViewName("redirect:/fixerSignUp/step2");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return mav;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ModelAndView emailLinkVerification(@RequestParam("emailCode") String emailCode, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		session.setAttribute("emailCode", emailCode);
		mav.setViewName("redirect:/login");

		return mav;
	}
	@RequestMapping(value = "/email/newRequest", method = RequestMethod.GET)
	public ModelAndView emailToFixerCode(@RequestParam("emailToFixerCode") String emailToFixerCode, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		session.setAttribute("emailToFixerCode", emailToFixerCode);
		mav.setViewName("redirect:/login");

		return mav;
	}
	@RequestMapping(value = "/email/fixerApply", method = RequestMethod.GET)
	public ModelAndView emailFixerApply(@RequestParam("fixerApplyCode") String fixerApplyCode, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		session.setAttribute("fixerApplyCode", fixerApplyCode);
		mav.setViewName("redirect:/login");

		return mav;
	}

	@RequestMapping(value = "/email/notFixed", method = RequestMethod.GET)
	public ModelAndView emailLinkVerificationForNotFixed(@RequestParam("notFixedQuery") String notFixedQuery,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		session.setAttribute("notFixedQuery", notFixedQuery);
		mav.setViewName("redirect:/login");

		return mav;
	}

	@RequestMapping(value = "/email/memberRespond", method = RequestMethod.GET)
	public ModelAndView emailLinkVerificationFormemberRespond(@RequestParam("memberRespond") String memberRespond,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		session.setAttribute("memberRespond", memberRespond);
		mav.setViewName("redirect:/login");

		return mav;
	}

	@RequestMapping(value = "/email/memberRequestNewFixer", method = RequestMethod.GET)
	public ModelAndView emailLinkVerificationFormemberRequestNewFixer(
			@RequestParam("memberRequestNewFixer") String memberRequestNewFixer, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.setAttribute("memberRequestNewFixer", memberRequestNewFixer);
		mav.setViewName("redirect:/login");

		return mav;
	}

	@RequestMapping(value = "/timeZone", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTimeZone(@RequestParam("timeZone") String timeZone, HttpServletResponse response) {
		timeZoneMap.put("timeZone", timeZone);
		return Utility.getSuccessResponse("timeZone", timeZoneMap);

	}

	@RequestMapping(value = "/loginAgain", method = RequestMethod.GET)
	public ModelAndView loginAgain() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/login_again.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "email/notSure", method = RequestMethod.GET)
	public ModelAndView adminNotSure(@RequestParam("adminNotSure") String adminNotSure, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("adminNotSure", adminNotSure);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}

	@RequestMapping(value = "email/unClaimed", method = RequestMethod.GET)
	public ModelAndView adminUnClaimed(@RequestParam("adminUnClaimed") String adminUnClaimed, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("adminUnClaimed", adminUnClaimed);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}

	@RequestMapping(value = "email/approveFixer", method = RequestMethod.GET)
	public ModelAndView approveFixer(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}
	
	@RequestMapping(value = "email/memberChat", method = RequestMethod.GET)
	public ModelAndView memberChat(@RequestParam("memberChat") String memberChat,@RequestParam("senderId") String senderId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("memberChat", memberChat);
		session.setAttribute("senderId", senderId);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}

	@RequestMapping(value = "email/fixerChat", method = RequestMethod.GET)
	public ModelAndView fixerChat(@RequestParam("fixerChat") String fixerChat,@RequestParam("senderId") String senderId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("fixerChat", fixerChat);
		session.setAttribute("senderId", senderId);
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}

	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginPage", "loginPage");
		mav.setViewName("privacy_policy.jsp");
		return mav;

	}

	@RequestMapping(value = "/userAgreement", method = RequestMethod.GET)
	public ModelAndView userAgreement() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginPage", "loginPage");
		mav.setViewName("user_agreement.jsp");
		return mav;

	}

	@RequestMapping(value = "fixerAgreement", method = RequestMethod.GET)
	public ModelAndView fixerAgreement() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginPage", "loginPage");
		mav.setViewName("fixer_agreement.jsp");
		return mav;

	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public ModelAndView forgotpassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/forgotpassword.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "/resetpasswordmail", method = RequestMethod.POST)
	public ModelAndView resetpasswordmail(@RequestParam String email, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		User user;
		List<User> users;
		try {
			users = userService.checkExistingEmailOrUserName(email, email);
			if (users.size() > 0) {
				user = users.get(0);
				if (user != null) {
					verificationService.deleteVerificationByUserId(user.getUserId());
					Verification verification = new Verification();
					verification.setUserId(user.getUserId());
					verification.setHashCode(randomConverter.stringASCIIGenerator(user.getUserId()));
					verification = verificationService.saveVerification(verification);

					StringBuffer url = request.getRequestURL();
					String uri = request.getRequestURI();
					String ctx = request.getContextPath();
					String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
					emailServcie.resetpasswordmail(user, verification, base);
					modelAndView.addObject("msgType", "success");
					modelAndView.addObject("msg", "Email Sent to you please Check your Email");
					modelAndView.setViewName("login/forgotpassword.jsp");
					return modelAndView;
				} else {
					modelAndView.addObject("msgType", "error");
					modelAndView.addObject("msg", "User Not Found");
					modelAndView.setViewName("login/forgotpassword.jsp");
					return modelAndView;
				}
			} else {
				modelAndView.addObject("msgType", "error");
				modelAndView.addObject("msg", "User Not Found");
				modelAndView.setViewName("login/forgotpassword.jsp");
				return modelAndView;
			}

		} catch (Exception e) {
			modelAndView.addObject("msgType", "error");
			modelAndView.addObject("msg", "Internal Server Error");
			modelAndView.setViewName("login/forgotpassword.jsp");
			return modelAndView;
		}

	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public ModelAndView resetpassword(@RequestParam("hashCode") String hashCode) {
		ModelAndView modelAndView = new ModelAndView();
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DAY_OF_MONTH, -1);
		try {
			Verification verification = verificationService.findVerificationByHashCode(hashCode, time);
			if (verification != null) {
				modelAndView.addObject("userId", verification.getUserId());
				ValidatePassword validatePassword = new ValidatePassword();
				modelAndView.addObject("validatePassword", validatePassword);
				modelAndView.setViewName("login/resetpassword.jsp");
				return modelAndView;
			} else {
				modelAndView.addObject("msgType", "error");
				modelAndView.addObject("msg", "Link Already  Expired Try Again");
				modelAndView.setViewName("login/forgotpassword.jsp");
				return modelAndView;
			}
		} catch (Exception e) {
			modelAndView.addObject("msgType", "error");
			modelAndView.addObject("msg", "Internal Server Error");
			modelAndView.setViewName("login/forgotpassword.jsp");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ModelAndView resetpasswordPost(@RequestParam Integer userId,
			@Validated @ModelAttribute("validatePassword") ValidatePassword validatePassword, BindingResult result,
			Model model) {
		ModelAndView modelAndView = new ModelAndView();

		passwordValidation.setValidatePassword(validatePassword);
		passwordValidation.validate(validatePassword, result);

		if (result.hasErrors()) {
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("validatePassword", validatePassword);
			modelAndView.setViewName("login/resetpassword.jsp");
			return modelAndView;
		}

		User user = userService.findUserById(userId);
		user.setPassword(PasswordEncode.encodePassword(validatePassword.getNewPassword()));
		try {
			user = userService.updateUser(user);
			if (user != null) {
				verificationService.deleteVerificationByUserId(userId);
				modelAndView.addObject("msgType", "success");
				modelAndView.addObject("msg", "Password Reset Successfully");
				modelAndView.setViewName("redirect:/login");
				return modelAndView;
			} else {
				modelAndView.addObject("msgType", "error");
				modelAndView.addObject("msg", "Password Reset Failed Try Again");
				modelAndView.setViewName("login/forgotpassword.jsp");
				return modelAndView;
			}
		} catch (FixitException e) {
			modelAndView.addObject("msgType", "error");
			modelAndView.addObject("msg", "Internal Server Error");
			modelAndView.setViewName("login/forgotpassword.jsp");
			return modelAndView;

		}

	}

	@RequestMapping(value = "sapModules", method = RequestMethod.GET)
	public ModelAndView allModules() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("sapModules", "sapModules");
		mav.setViewName("sapModules.jsp");
		return mav;

	}

	@RequestMapping(value = { "/member-login-linkedin" }, method = RequestMethod.GET)
	public ModelAndView login(WebRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// getting request and access token from session
		ObjectMapper objectMapper = new ObjectMapper();
		org.scribe.model.Token requestToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, SCOPE_SESSION);
		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = linkedInServiceProvider.getService();
		if (requestToken == null || accessToken == null) {
			try {
				requestToken = service.getRequestToken();
				request.setAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, requestToken, SCOPE_SESSION);
				session.setAttribute("isUserType", "MEMBER");
				// redirect to linkedin auth page"redirect:login"
				
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(requestToken)));
				mav.setViewName("signUp/redirect.jsp");
			} catch (Exception e) {

			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, accessToken, SCOPE_SESSION);

			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET,
					"http://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry,headline,email-address,location,summary,picture-url)?format=json");
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			

			try {
				JSONObject object = new JSONObject(oauthResponse.getBody());
				UserBo member = new UserBo();

				member.setFirstName(object.optString("firstName"));
				member.setLastName(object.optString("lastName"));
				member.setEmail(object.optString("emailAddress"));
				member.setOverview(object.optString("summary"));
				member.setImageUrl(object.optString("pictureUrl"));
				member.setSocialLogin(true);
				String countryCode = object.getJSONObject("location").getJSONObject("country").getString("code");
				Locale obj = new Locale("", countryCode);
				String countryName = obj.getDisplayCountry();
				member.setCountry(countryName);

				mav.addObject("load", true);
				mav.addObject("member", member);
				mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
				mav.addObject("flag", true);
				mav.addObject("isPopUp", false);
				mav.setViewName("signUp/memberSignUp.jsp");
			} catch (Exception e) {

			}
		}
		return mav;
	}

	@RequestMapping(value = { "/fixer-login-linkedin" }, method = RequestMethod.GET)
	public ModelAndView loginFixer(WebRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		// getting request and access token from session
		org.scribe.model.Token requestToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, SCOPE_SESSION);
		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = linkedInServiceProvider.getService();
		if (requestToken == null || accessToken == null) {

			try {
				requestToken = service.getRequestToken();
				request.setAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, requestToken, SCOPE_SESSION);
				session.setAttribute("isUserType", "FIXER");
				// redirect to linkedin auth page"redirect:login"
				
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(requestToken)));
				mav.setViewName("signUp/redirect.jsp");
			} catch (Exception e) {

			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, accessToken, SCOPE_SESSION);

			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET,
					"http://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry,headline,email-address,location,summary,picture-url)?format=json");
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			

			try {
				FixerRegisterBo fixer = new FixerRegisterBo();
				JSONObject object = new JSONObject(oauthResponse.getBody());

				fixer.setFirstName(object.optString("firstName"));
				fixer.setLastName(object.optString("lastName"));
				fixer.setEmail(object.optString("emailAddress"));
				fixer.setOverview(object.optString("summary"));
				fixer.setImageUrl(object.optString("pictureUrl"));
				fixer.setSocialLogin(true);
				String countryCode = object.getJSONObject("location").getJSONObject("country").getString("code");
				Locale obj = new Locale("", countryCode);
				String countryName = obj.getDisplayCountry();
				fixer.setCountry(countryName);

				try {
					
					mav.addObject("load", true);
					mav.addObject("fixer", fixer);
					mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
					mav.setViewName("signUp/fixerSignUp.jsp");
				} catch (Exception e) {

				}
			} catch (Exception e) {

			}
		}
		return mav;
	}

	@RequestMapping(value = { "/fixer-login-google" }, method = RequestMethod.GET)
	public ModelAndView loginFixerGoogle(WebRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		// getting request and access token from session
		org.scribe.model.Token requestToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN_GOOGLE, SCOPE_SESSION);
		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN_GOOGLE, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = googleServiceProvider.getService();
		if (accessToken == null) {

			try {
				
				session.setAttribute("isUserType", "FIXER");
				// redirect to linkedin auth page"redirect:login"
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(null)));
				mav.setViewName("signUp/redirect.jsp");
				
			} catch (Exception e) {

			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, accessToken, SCOPE_SESSION);

			// getting user profile
			String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			

			try {
				FixerRegisterBo fixer = new FixerRegisterBo();
				JSONObject object = new JSONObject(oauthResponse.getBody());

				fixer.setFirstName(object.optString("given_name"));
				fixer.setLastName(object.optString("family_name"));
				fixer.setEmail(object.optString("email"));
				fixer.setSocialLogin(true);
				
				fixer.setImageUrl(object.optString("picture"));

				
				try {
					
					mav.addObject("load", true);
					mav.addObject("fixer", fixer);
					mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
					mav.setViewName("signUp/fixerSignUp.jsp");
				} catch (Exception e) {

				}
			} catch (Exception e) {

			}
		}
		return mav;
	}

	@RequestMapping(value = { "/member-login-google" }, method = RequestMethod.GET)
	public ModelAndView loginGoogle(WebRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		// getting request and access token from session
		org.scribe.model.Token requestToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN_GOOGLE, SCOPE_SESSION);
		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN_GOOGLE, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = googleServiceProvider.getService();
		if (accessToken == null) {
			try {
				
				session.setAttribute("isUserType", "MEMBER");
				// redirect to linkedin auth page"redirect:login"
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(null)));
				mav.setViewName("signUp/redirect.jsp");
				
			} catch (Exception e) {

			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN_GOOGLE, accessToken, SCOPE_SESSION);
			// getting user profile
			String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			

			try {
				JSONObject object = new JSONObject(oauthResponse.getBody());
				UserBo member = new UserBo();
				member.setFirstName(object.optString("given_name"));
				member.setLastName(object.optString("family_name"));
				member.setEmail(object.optString("email"));
				member.setSocialLogin(true);
				member.setImageUrl(object.optString("picture"));
				
				mav.addObject("load", true);
				mav.addObject("member", member);
				mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
				mav.addObject("flag", true);
				mav.addObject("isPopUp", false);
				mav.setViewName("signUp/memberSignUp.jsp");
			} catch (Exception e) {

			}
		}
		return mav;
	}

	@RequestMapping(value = { "/linkedInSignup" }, method = RequestMethod.POST)
	public ModelAndView callback(@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "summary", required = false) String summary,
			@RequestParam(value = "isUserType", required = false) String userType,
			@RequestParam(value = "given_name", required = false) String givenName,
			@RequestParam(value = "family_name", required = false) String familyName,
			@RequestParam(value = "image_url", required = false) String imageUrl,
			@RequestParam(value = "email", required = false) String email,

			@RequestParam(value = "publicProfileUrl", required = false) String publicProfileUrl,

			@RequestParam(value = "error", required = false) String error,

			WebRequest request, HttpServletRequest myRequest, HttpServletResponse myResponse) {
		ModelAndView mav = new ModelAndView();
		// getting request tocken
		if (error != null && error.length() > 0) {
			if (userType.equals("MEMBER")) {
				mav.setViewName("redirect:/memberSignUp");
			} else if (userType.equals("FIXER")) {
				mav.setViewName("redirect:/fixerSignUp");
			} else {
				mav.setViewName("redirect:/login");
			}
			return mav;
		}
		
		try {
			if (userType.equals("MEMBER")) {
				

				UserBo member = new UserBo();
				ObjectMapper objectMapper = new ObjectMapper();
				member.setFirstName(givenName);
				member.setLastName(familyName);
				member.setEmail(email);
				member.setOverview(summary);
				member.setImageUrl(imageUrl);
				member.setSocialLogin(true);
				member.setCity(city);
				String countryCode = code;
				Locale obj = new Locale("", countryCode);
				String countryName = obj.getDisplayCountry();
				member.setCountry(countryName);

				mav.addObject("load", true);
				mav.addObject("member", member);
				mav.addObject("countryList", countryService.findAllCountry());
				mav.addObject("flag", true);
				mav.addObject("isPopUp", false);

				mav.setViewName("signUp/memberSignUp.jsp");
			} else if (userType.equals("FIXER")) {
				FixerRegisterBo fixer = new FixerRegisterBo();
				// JSONObject object = new JSONObject(oauthResponse.getBody());
				ObjectMapper objectMapper = new ObjectMapper();

				fixer.setFirstName(givenName);
				fixer.setLastName(familyName);
				fixer.setEmail(email);
				fixer.setOverview(summary);
				fixer.setImageUrl(imageUrl);
				fixer.setSocialLogin(true);
				fixer.setLinkedinProfile(publicProfileUrl);
				String countryCode = code;
				Locale obj = new Locale("", countryCode);
				String countryName = obj.getDisplayCountry();
				fixer.setCountry(countryName);
				fixer.setCity(city);
				try {
					// fixer.setUserType("F");
					mav.addObject("load", false);
					mav.addObject("fixer", fixer);
					mav.addObject("countryList", countryService.findAllCountry());
					mav.setViewName("signUp/fixerSignUp.jsp");
				} catch (Exception e) {

				}

			} else {

				JSONObject object;
				try {

					List<User> l = null;
					User user;
					l = userService.checkExistingEmailOrUserName(email, email);
					if (l != null && l.size() > 0) {
						user = l.get(0);
						UserDetails springUser;
						userType = user.getUserType();
						if (UserTypeEnum.F.toString().equals(user.getUserType())) {
							if (("R".equals(user.getFixerStatus())) || (user.getFixerStatus() == null)) {
								MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
								String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
										myResponse, new InsufficientAuthenticationException(
												messages.getMessage(MSG_BAD_CREDENTIALS, "R")));
								mav.setViewName("redirect:" + "/" + url);
							}

							if ("D".equals(user.getFixerStatus())) {
								MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
								String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
										myResponse, new InsufficientAuthenticationException(
												messages.getMessage(MSG_BAD_CREDENTIALS, "D")));
								mav.setViewName("redirect:" + "/" + url);
							}
						}
						List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
						switch (userType) {
						case "F":
							result.add(new SimpleGrantedAuthority("ROLE_FIXER"));
							break;
						case "C":
							result.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
							break;
						case "A":
							result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
							break;
						}
						result.add(new SimpleGrantedAuthority("read"));
						result.add(new SimpleGrantedAuthority("write"));
						result.add(new SimpleGrantedAuthority("userid " + user.getUserId()));
						springUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
								user.getPassword(), true, true, true, true, result);
						
						Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, null,
								springUser.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
						String customString = customAuthenticationSuccessHandler
								.myCustomAuthenticationSuccess(myRequest, myResponse, authentication);
						mav.setViewName("redirect:" + "/" + customString);
					} else {
						UserBo member = new UserBo();
						ObjectMapper objectMapper = new ObjectMapper();
						member.setFirstName(givenName);
						member.setLastName(familyName);
						member.setEmail(email);
						member.setOverview(summary);
						member.setImageUrl(imageUrl);
						member.setSocialLogin(true);

						String countryCode = code;
						Locale obj = new Locale("", countryCode);
						String countryName = obj.getDisplayCountry();
						member.setCountry(countryName);

						mav.addObject("load", true);
						mav.addObject("member", member);
						mav.addObject("countryList", countryService.findAllCountry());
						mav.addObject("flag", false);
						mav.addObject("isPopUp", false);

						mav.setViewName("signUp/memberSignUp.jsp");
					}
				} catch (Exception e) {
					MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
					mav.setViewName("redirect:/login?error=1");
				}

			}
		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = { "/googleSignUp" }, method = RequestMethod.POST)
	public ModelAndView callbackGoogle(@RequestParam(value = "isUserType", required = false) String userType,
			@RequestParam(value = "given_name", required = false) String givenName,
			@RequestParam(value = "family_name", required = false) String familyName,
			@RequestParam(value = "image_url", required = false) String imageUrl,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "error", required = false) String error, WebRequest request,
			HttpServletRequest myRequest, HttpServletResponse myResponse) {
		ModelAndView mav = new ModelAndView();
		if (error != null && error.length() > 0) {
			if (userType.equals("MEMBER")) {
				mav.setViewName("redirect:/memberSignUp");
			} else if (userType.equals("FIXER")) {
				mav.setViewName("redirect:/fixerSignUp");
			} else {
				mav.setViewName("redirect:/login");
			}
			return mav;
		}

				try {
			if (userType.equals("MEMBER")) {
				

				UserBo member = new UserBo();
				ObjectMapper objectMapper = new ObjectMapper();
				member.setFirstName(givenName);
				member.setLastName(familyName);
				member.setEmail(email);
				member.setImageUrl(imageUrl);
				member.setSocialLogin(true);
				

				mav.addObject("load", true);
				mav.addObject("member", member);
				mav.addObject("countryList", countryService.findAllCountry());
				mav.addObject("flag", true);
				mav.addObject("isPopUp", false);

				mav.setViewName("signUp/memberSignUp.jsp");
			} else if (userType.equals("FIXER")) {
				FixerRegisterBo fixer = new FixerRegisterBo();
				
				ObjectMapper objectMapper = new ObjectMapper();
				fixer.setFirstName(givenName);
				fixer.setLastName(familyName);
				fixer.setEmail(email);
				fixer.setImageUrl(imageUrl);
				fixer.setSocialLogin(true);

				try {
					
					mav.addObject("load", true);
					mav.addObject("fixer", fixer);
					mav.addObject("countryList", countryService.findAllCountry());
					mav.setViewName("signUp/fixerSignUp.jsp");
				} catch (Exception e) {

				}

			} else {

				JSONObject object;
				try {
					

					List<User> l = null;
					User user;
					l = userService.checkExistingEmailOrUserName(email, email);
					if (l != null && l.size() > 0) {
						user = l.get(0);
						UserDetails springUser;
						userType = user.getUserType();
						if (UserTypeEnum.F.toString().equals(user.getUserType())) {
							if (("R".equals(user.getFixerStatus())) || (user.getFixerStatus() == null)) {
								MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
								String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
										myResponse, new InsufficientAuthenticationException(
												messages.getMessage(MSG_BAD_CREDENTIALS, "R")));
								mav.setViewName("redirect:" + "/" + url);
							}

							if ("D".equals(user.getFixerStatus())) {
								MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
								String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
										myResponse, new InsufficientAuthenticationException(
												messages.getMessage(MSG_BAD_CREDENTIALS, "D")));
								mav.setViewName("redirect:" + "/" + url);
							}
						}
						List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
						switch (userType) {
						case "F":
							result.add(new SimpleGrantedAuthority("ROLE_FIXER"));
							break;
						case "C":
							result.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
							break;
						case "A":
							result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
							break;
						}
						result.add(new SimpleGrantedAuthority("read"));
						result.add(new SimpleGrantedAuthority("write"));
						result.add(new SimpleGrantedAuthority("userid " + user.getUserId()));
						springUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
								user.getPassword(), true, true, true, true, result);
						
						Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, null,
								springUser.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
						String customString = customAuthenticationSuccessHandler
								.myCustomAuthenticationSuccess(myRequest, myResponse, authentication);
						mav.setViewName("redirect:" + "/" + customString);
					} else {

						UserBo member = new UserBo();
						ObjectMapper objectMapper = new ObjectMapper();
						member.setFirstName(givenName);
						member.setLastName(familyName);
						member.setEmail(email);
						member.setSocialLogin(true);
						member.setImageUrl(imageUrl);
						
						mav.addObject("load", true);
						mav.addObject("member", member);
						mav.addObject("countryList", countryService.findAllCountry());
						mav.addObject("flag", true);
						mav.addObject("isPopUp", false);
						mav.setViewName("signUp/memberSignUp.jsp");
					}
				} catch (Exception e) {
					MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
					mav.setViewName("redirect:/login?error=1");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = { "/login-via-linkedin" }, method = RequestMethod.GET)
	public ModelAndView applicationLogin(WebRequest request, HttpServletRequest myRequest,
			HttpServletResponse myResponse, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		// getting request and access token from session
		org.scribe.model.Token requestToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, SCOPE_SESSION);
		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = linkedInServiceProvider.getService();
		if (requestToken == null || accessToken == null) {

			try {
				requestToken = service.getRequestToken();
				request.setAttribute(SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN, requestToken, SCOPE_SESSION);
				session.setAttribute("isUserType", "LOGIN");
				// redirect to linkedin auth page"redirect:login"
				
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(requestToken)));
				mav.setViewName("signUp/redirect.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN, accessToken, SCOPE_SESSION);

			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET,
					"http://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry,headline,email-address,location,summary,picture-url)?format=json");
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			
			JSONObject object;
			try {
				object = new JSONObject(oauthResponse.getBody());
				String email = object.optString("emailAddress");
				List<User> l = null;
				User user;
				l = userService.checkExistingEmailOrUserName(email, email);
				if (l != null && l.size() > 0) {
					user = l.get(0);
					UserDetails springUser;
					String userType = user.getUserType();
					if (UserTypeEnum.F.toString().equals(user.getUserType())) {
						if (("R".equals(user.getFixerStatus())) || (user.getFixerStatus() == null)) {
							MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
							String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
									myResponse, new InsufficientAuthenticationException(
											messages.getMessage(MSG_BAD_CREDENTIALS, "R")));
							mav.setViewName("redirect:" + "/" + url);
						}

						if ("D".equals(user.getFixerStatus())) {
							MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
							String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
									myResponse, new InsufficientAuthenticationException(
											messages.getMessage(MSG_BAD_CREDENTIALS, "D")));
							mav.setViewName("redirect:" + "/" + url);
						}
					}
					List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
					switch (userType) {
					case "F":
						result.add(new SimpleGrantedAuthority("ROLE_FIXER"));
						break;
					case "C":
						result.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
						break;
					case "A":
						result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
						break;
					}
					result.add(new SimpleGrantedAuthority("read"));
					result.add(new SimpleGrantedAuthority("write"));
					result.add(new SimpleGrantedAuthority("userid " + user.getUserId()));
					springUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
							user.getPassword(), true, true, true, true, result);
					
					Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, null,
							springUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
					String customString = customAuthenticationSuccessHandler.myCustomAuthenticationSuccess(myRequest,
							myResponse, authentication);
					mav.setViewName("redirect:" + "/" + customString);
				} else {
					UserBo member = new UserBo();

					member.setFirstName(object.optString("firstName"));
					member.setLastName(object.optString("lastName"));
					member.setEmail(object.optString("emailAddress"));
					member.setOverview(object.optString("summary"));
					member.setImageUrl(object.optString("pictureUrl"));
					member.setSocialLogin(true);
					String countryCode = object.getJSONObject("location").getJSONObject("country").getString("code");
					Locale obj = new Locale("", countryCode);
					String countryName = obj.getDisplayCountry();
					member.setCountry(countryName);

					mav.addObject("load", true);
					mav.addObject("member", member);
					mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
					mav.addObject("flag", false);
					mav.addObject("isPopUp", false);

					mav.setViewName("signUp/memberSignUp.jsp");
				}
			} catch (Exception e) {
				MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
				mav.setViewName("redirect:/login?error=1");
			}

		}
		return mav;
	}

	/*
	 * @RequestMapping("/favicon.ico") public String favicon() { return
	 * "forward:/images/favicon.ico"; }
	 */

	@RequestMapping(value = { "/login-via-google" }, method = RequestMethod.GET)
	public ModelAndView applicationLoginGoogle(WebRequest request, HttpServletRequest myRequest,
			HttpServletResponse myResponse, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		// getting request and access token from session

		org.scribe.model.Token accessToken = (org.scribe.model.Token) request
				.getAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN_GOOGLE, SCOPE_SESSION);
		org.scribe.oauth.OAuthService service = googleServiceProvider.getService();
		if (accessToken == null) {

			try {
				
				session.setAttribute("isUserType", "LOGIN");
				// redirect to linkedin auth page"redirect:login"
				mav.addObject("url", objectMapper.writeValueAsString(service.getAuthorizationUrl(null)));
				mav.setViewName("signUp/redirect.jsp");
				
			} catch (Exception e) {

			}
		} else {
			request.setAttribute(SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN_GOOGLE, accessToken, SCOPE_SESSION);

			// getting user profile
			String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
			// getting user profile
			OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, oauthRequest);
			Response oauthResponse = oauthRequest.send();
			JSONObject object;
			try {
				object = new JSONObject(oauthResponse.getBody());
				String email = object.optString("email");
				List<User> l = null;
				User user;
				l = userService.checkExistingEmailOrUserName(email, email);
				if (l != null && l.size() > 0) {
					user = l.get(0);
					UserDetails springUser;
					String userType = user.getUserType();
					if (UserTypeEnum.F.toString().equals(user.getUserType())) {
						if (("R".equals(user.getFixerStatus())) || (user.getFixerStatus() == null)) {
							MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
							String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
									myResponse, new InsufficientAuthenticationException(
											messages.getMessage(MSG_BAD_CREDENTIALS, "R")));
							mav.setViewName("redirect:" + "/" + url);
						}

						if ("D".equals(user.getFixerStatus())) {
							MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
							String url = customAuthenticationFailureHandler.myAuthenticationFailure(myRequest,
									myResponse, new InsufficientAuthenticationException(
											messages.getMessage(MSG_BAD_CREDENTIALS, "D")));
							mav.setViewName("redirect:" + "/" + url);
						}
					}
					List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
					switch (userType) {
					case "F":
						result.add(new SimpleGrantedAuthority("ROLE_FIXER"));
						break;
					case "C":
						result.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
						break;
					case "A":
						result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
						break;
					}
					result.add(new SimpleGrantedAuthority("read"));
					result.add(new SimpleGrantedAuthority("write"));
					result.add(new SimpleGrantedAuthority("userid " + user.getUserId()));
					springUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
							user.getPassword(), true, true, true, true, result);
					
					Authentication authentication = new UsernamePasswordAuthenticationToken(springUser, null,
							springUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
					String customString = customAuthenticationSuccessHandler.myCustomAuthenticationSuccess(myRequest,
							myResponse, authentication);
					mav.setViewName("redirect:" + "/" + customString);
				} else {

					UserBo member = new UserBo();

					member.setFirstName(object.optString("given_name"));
					member.setLastName(object.optString("family_name"));
					member.setEmail(object.optString("email"));
					member.setSocialLogin(true);
					member.setImageUrl(object.optString("picture"));
					
					mav.addObject("load", true);
					mav.addObject("member", member);
					mav.addObject("countryList", objectMapper.writeValueAsString(countryService.findAllCountry()));
					mav.addObject("flag", true);
					mav.addObject("isPopUp", false);
					mav.setViewName("signUp/memberSignUp.jsp");
				}
			} catch (Exception e) {
				MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
				mav.setViewName("redirect:/login?error=1");
			}

		}
		return mav;
	}
	

}
