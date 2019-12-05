package com.fixit.config;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.service.QueryExpireService;
import com.fixit.service.QueryService;
import com.fixit.service.UserService;
import com.fixit.utility.FixitException;
import com.fixit.utility.UserRoleEnum;
import com.fixit.web.LoginController;

@Component
public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler  {
	@Autowired
	private UserService userService;
	
	@Autowired
	private QueryExpireService queryExpireService;
	
	@Autowired
	private QueryService queryService;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		
		
		 DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		HttpSession session = httpServletRequest.getSession();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		session.setAttribute("username", authUser.getUsername());
		session.setAttribute("authorities", authentication.getAuthorities());
		session.setMaxInactiveInterval(30*60);
		List<com.fixit.domain.vo.User> users=new ArrayList<com.fixit.domain.vo.User>();
		try {
		users=userService.checkExistingEmailOrUserName(authUser.getUsername(), authUser.getUsername());
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		com.fixit.domain.vo.User user=null;
		if(users.size()>0){
			
			 user=users.get(0);
			 if(user.getTimeZone()==null){
				
				 user.setTimeZone(LoginController.timeZoneMap.get("timeZone"));
			 }
			session.setAttribute("user", user);
		}
		Collection<? extends GrantedAuthority> roleList = authentication
				.getAuthorities();
		if (roleList.contains(new SimpleGrantedAuthority(
				UserRoleEnum.ROLE_FIXER.toString()))) {
			
			
			
			if(httpServletRequest.getSession().getAttribute("emailToFixerCode")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("emailToFixerCode");
				try {
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("emailToFixerCode", null);
					
						 httpServletResponse.sendRedirect("fixer/request?status=Unclaimed&msgKey=R");
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
				   
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
			}else if(httpServletRequest.getSession().getAttribute("emailCode")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("emailCode");
				try {	
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("emailCode", null);
					
						 httpServletResponse.sendRedirect("fixer/request?status=Unclaimed&msgKey=R");
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
					 }else{
						
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
				   
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
			}else if(httpServletRequest.getSession().getAttribute("notFixedQuery")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("notFixedQuery");
				try {
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("notFixedQuery", null);
					 httpServletResponse.sendRedirect("fixer/closedQueryPage?queryId="+query.getHashcode());
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }
						 
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
			}
			else if(httpServletRequest.getSession().getAttribute("fixerChat")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("fixerChat");
				String senderId = (String) httpServletRequest.getSession().getAttribute("senderId");
				try {
					

					 Query query= queryService.getQueryByHashCode(hashcode);
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("fixerChat", null);
						 httpServletRequest.getSession().setAttribute("senderId", null);
						 if(query.getCurrentStatus().equals("O"))
							{
								
							 	httpServletResponse.sendRedirect("fixer/request?msgKey=R&status=Applied&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								 httpServletResponse.sendRedirect("fixer/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								 httpServletResponse.sendRedirect("fixer/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
						 
						 
						 
					
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
					 }

						 
				} catch (FixitException e) {
					
				
					e.printStackTrace();
				}
			}
			else{
				
				redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/fixer/dashBoard?msgKey=R");
			}
		} else if (roleList.contains(new SimpleGrantedAuthority(
				UserRoleEnum.ROLE_MEMBER.toString()))) {
			
			
			if (httpServletRequest.getSession().getAttribute("fixerApplyCode") != null) {

				String hashcode=(String) httpServletRequest.getSession().getAttribute("fixerApplyCode");
				Query query = null;
								
				try {
					query = queryService.getQueryByHashCode(hashcode);
				} catch (FixitException e) {
					e.printStackTrace();
				}
				if (query != null) {
					
					httpServletRequest.getSession().setAttribute("fixerApplyCode", null);
					httpServletResponse.sendRedirect("member/request?msgKey=R&applyQueryCode=" + query.getQueryId());
						
												
				} else {
					httpServletResponse.sendRedirect("member/dashBoard?msgKey=EX");
				}
				

			}
			else if(httpServletRequest.getSession().getAttribute("memberRespond")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberRespond");
				try {
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("memberRespond", null);
					     httpServletResponse.sendRedirect("member/memberQueryDetailPage?queryId="+query.getHashcode());
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
					 }
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
					 }
				   
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}	
			}else if (httpServletRequest.getSession().getAttribute("memberRequestNewFixer")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberRequestNewFixer");
				try {
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						
						 httpServletRequest.getSession().setAttribute("memberRequestNewFixer", null);
					 httpServletResponse.sendRedirect("member/editQuestion?queryId="+query.getHashcode());
					 }else{
						
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
					 }
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
					 }
					  
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
			}
			
			else if(httpServletRequest.getSession().getAttribute("adminNotSure")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("adminNotSure");
				QueryExpire queryExpire = null;
				try{
					
					queryExpire =queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				}catch(Exception e){
					
				}
				if(queryExpire!=null){
					
					try {
						
						 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
						 if(query!=null){
							 
							 httpServletRequest.getSession().setAttribute("adminNotSure", null);
						     httpServletResponse.sendRedirect("member/editQuestion?queryId="+query.getHashcode());
						 }else{
							 
							 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
						 }
						  
					} catch (FixitException e) {
						
						
						e.printStackTrace();
					}
				}else{
					
					redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
				}
			}else if (httpServletRequest.getSession().getAttribute("adminUnClaimed")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("adminUnClaimed");
				QueryExpire queryExpire = null;
				try{
					
					queryExpire  = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
					}catch(Exception e){
						
				}
				if(queryExpire!=null){
					
					try {
						
						 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
						 if(query!=null){
							 
							 httpServletRequest.getSession().setAttribute("adminUnClaimed", null);
						     httpServletResponse.sendRedirect("member/editQuestion?queryId="+query.getHashcode());
						 }else{
							
							 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
						 }
						  
					} catch (FixitException e) {
						
						e.printStackTrace();
					}		
				}else{
					
					redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");
				}
			
			}
			else if (httpServletRequest.getSession().getAttribute("memberChat")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberChat");
				String senderId=(String)  httpServletRequest.getSession().getAttribute("senderId");
				try {
					
					 Query query= queryService.getQueryByHashCode(hashcode);
					 if(query!=null){
						
						 httpServletRequest.getSession().setAttribute("memberChat", null);
						 httpServletRequest.getSession().setAttribute("senderId", null);
						 if(query.getCurrentStatus().equals("O"))
							{
							 	httpServletResponse.sendRedirect("member/request?msgKey=R&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								httpServletResponse.sendRedirect("member/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								httpServletResponse.sendRedirect("member/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId());
							}
							
					   
					 }else{
						 
						 redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R"); 
					 }
					  
				} catch (FixitException e) {
					
				
					e.printStackTrace();
				}
			}
			else{
				
				redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/member/dashBoard?msgKey=R");	
			}
			
			
		} else if (roleList.contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.toString()))) {
			
			redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin/FixerForReview?msgKey=R");
		} else {
			
		}

		
		
	}

	
	public String myCustomAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		// do some logic here if you want something to be done whenever
		// the user successfully logs in.
		
		 DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
		HttpSession session = httpServletRequest.getSession();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		session.setAttribute("username", authUser.getUsername());
		session.setAttribute("authorities", authentication.getAuthorities());
		session.setMaxInactiveInterval(30*60);
		List<com.fixit.domain.vo.User> users=new ArrayList<com.fixit.domain.vo.User>();
		try {
		users=userService.checkExistingEmailOrUserName(authUser.getUsername(), authUser.getUsername());
		} catch (FixitException e) {
			
			e.printStackTrace();
		}
		com.fixit.domain.vo.User user=null;
		if(users.size()>0){
			 user=users.get(0);
			 if(user.getTimeZone()==null){
				 user.setTimeZone(LoginController.timeZoneMap.get("timeZone"));
			 }
			session.setAttribute("user", user);
		}
		Collection<? extends GrantedAuthority> roleList = authentication
				.getAuthorities();
		if (roleList.contains(new SimpleGrantedAuthority(
				UserRoleEnum.ROLE_FIXER.toString()))) {
			
			if(httpServletRequest.getSession().getAttribute("emailToFixerCode")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("emailToFixerCode");
				try {
					
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					  
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("emailToFixerCode", null);
					 
						 return "fixer/request?status=Unclaimed&msgKey=R";
					 }else{
						
						 return "/fixer/dashBoard?msgKey=R";
						 
					 }
					 }else{
						 
						 return "/fixer/dashBoard?msgKey=R";
						 
					 }
				   
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
			}
			
			else if(httpServletRequest.getSession().getAttribute("emailCode")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("emailCode");
				try {
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 httpServletRequest.getSession().setAttribute("emailCode", null);
						
					      return "fixer/request?status=Unclaimed&msgKey=R";
					 }else{
						 return "fixer/dashBoard?msgKey=R"; 
					 }
					 }else{
						 return  "fixer/dashBoard?msgKey=R";
			
					 }
				   
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
			}else if(httpServletRequest.getSession().getAttribute("notFixedQuery")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("notFixedQuery");
				try {
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 httpServletRequest.getSession().setAttribute("notFixedQuery", null);
					return "fixer/closedQueryPage?queryId="+query.getHashcode();
					 }else{
					
						 return "fixer/dashBoard?msgKey=R"; 
					 }
					 }else{
						 return "fixer/dashBoard?msgKey=R"; 
					 }
						 
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
			}
			else if(httpServletRequest.getSession().getAttribute("fixerChat")!=null){
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("fixerChat");
				String senderId = (String) httpServletRequest.getSession().getAttribute("senderId");
				try {
					

					 Query query= queryService.getQueryByHashCode(hashcode);
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("fixerChat", null);
						 httpServletRequest.getSession().setAttribute("senderId", null);
						 if(query.getCurrentStatus().equals("O"))
							{
								
							 return "fixer/request?msgKey=R&status=Applied&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								return "fixer/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								return "fixer/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
						 
						 
						 
					
					 }else{
						 
						 return "/fixer/dashBoard?msgKey=R";
					 }

						 
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
				
				
			}
			else{
			return "fixer/dashBoard?msgKey=R";
			}
		} else if (roleList.contains(new SimpleGrantedAuthority(
				UserRoleEnum.ROLE_MEMBER.toString()))) {
			
			if (httpServletRequest.getSession().getAttribute("fixerApplyCode") != null) {

				String hashcode=(String) httpServletRequest.getSession().getAttribute("fixerApplyCode");
				Query query = null;
								
				try {
					query = queryService.getQueryByHashCode(hashcode);
				} catch (FixitException e) {
					e.printStackTrace();
				}
				if (query != null) {
					
					httpServletRequest.getSession().setAttribute("fixerApplyCode", null);
					return "member/request?msgKey=R&applyQueryCode=" + query.getQueryId();
						
												
				} else {
					return "member/dashBoard?msgKey=EX";
				}
				

			}
			
			else if(httpServletRequest.getSession().getAttribute("memberRespond")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberRespond");
				try {
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 httpServletRequest.getSession().setAttribute("memberRespond", null);
					     return "member/memberQueryDetailPage?queryId="+query.getHashcode();
					 }else{
						 return "member/dashBoard?msgKey=R";
					 }
					 }else{
						 return "member/dashBoard?msgKey=R";	 
					 }
				   
				} catch (FixitException e) {
					
					e.printStackTrace();
				}	
			}else if (httpServletRequest.getSession().getAttribute("memberRequestNewFixer")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberRequestNewFixer");
				try {
					QueryExpire queryExpire=queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				  if(queryExpire!=null){
					 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
					 if(query!=null){
						 httpServletRequest.getSession().setAttribute("memberRequestNewFixer", null);
					 return "member/editQuestion?queryId="+query.getHashcode();
					 }else{
						return "member/dashBoard?msgKey=R"; 
					 }
					 }else{
						return "member/dashBoard?msgKey=R"; 
					 }
					  
				} catch (FixitException e) {
					
					e.printStackTrace();
				}
			}
			
			if(httpServletRequest.getSession().getAttribute("adminNotSure")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("adminNotSure");
				QueryExpire queryExpire = null;
				try{
					queryExpire =queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
				}catch(Exception e){
					
				}
				if(queryExpire!=null){
					try {
						 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
						 if(query!=null){
							 httpServletRequest.getSession().setAttribute("adminNotSure", null);
						     return "member/editQuestion?queryId="+query.getHashcode();
						 }else{
							 return "member/dashBoard?msgKey=R"; 
						 }
						  
					} catch (FixitException e) {
						
						e.printStackTrace();
					}
				}else{
					return "member/dashBoard";
				}
			}else if (httpServletRequest.getSession().getAttribute("adminUnClaimed")!=null){
				String hashcode=(String) httpServletRequest.getSession().getAttribute("adminUnClaimed");
				QueryExpire queryExpire = null;
				try{
					queryExpire  = queryExpireService.findQueryExpireByFixerIdAndhashcode(user.getUserId(),hashcode);
					}catch(Exception e){
				}
				if(queryExpire!=null){
					try {
						 Query query= queryService.findQueryByQueryId(queryExpire.getQueryId());
						 if(query!=null){
							 httpServletRequest.getSession().setAttribute("adminUnClaimed", null);
						     return "member/editQuestion?queryId="+query.getHashcode();
						 }else{
								return "member/dashBoard?msgKey=R";
						 }
						  
					} catch (FixitException e) {
						e.printStackTrace();
					}		
				}else{
				
					return "member/dashBoard";
				}
			
			}
			else if (httpServletRequest.getSession().getAttribute("memberChat")!=null){
				
				
				String hashcode=(String) httpServletRequest.getSession().getAttribute("memberChat");
				String senderId=(String)  httpServletRequest.getSession().getAttribute("senderId");
				try {
					
					 Query query= queryService.getQueryByHashCode(hashcode);
					 if(query!=null){
						 
						 httpServletRequest.getSession().setAttribute("memberChat", null);
						 httpServletRequest.getSession().setAttribute("senderId", null);
						 if(query.getCurrentStatus().equals("O"))
							{
							 	return "member/request?msgKey=R&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
							else if(query.getCurrentStatus().equals("W"))
							{
								
								return "member/request?msgKey=R&status=InProgress&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
							else if(query.getCurrentStatus().equals("C"))
							{
								return "member/request?msgKey=R&status=Closed&senderId=" + senderId+"&queryId=" + query.getQueryId();
							}
							
					   
					 }else{
						 
						 return "/member/dashBoard?msgKey=R"; 
					 }
					  
				} catch (FixitException e) {
					
					
					e.printStackTrace();
				}
				
			}
			else{
				return "member/dashBoard?msgKey=R";
			}
			
			
		} else if (roleList.contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.toString()))) {
			
			return "admin/FixerForReview?msgKey=R";	
				
			
		} else {

		}
		return "login";

		
	}

}