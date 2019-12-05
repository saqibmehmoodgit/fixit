package com.fixit.utility;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.amazonaws.auth.policy.Principal;
import com.fixit.domain.vo.User;
import com.fixit.service.UserService;

public class Logout extends SimpleUrlLogoutSuccessHandler {

	
	@Autowired
	private UserService userService;
	

	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		String userName="";
		if(authentication!=null){
		 userName = authentication.getName();
		}
		try {
			Calendar calendar = Calendar.getInstance();
			List<User> users = userService.checkExistingEmail(userName);
			User user;
			if(users.size()>=1){
				user = users.get(0);
				user.setLastLogin(calendar);
				userService.updateUser(user);
			 }
			if (session != null) {
		            session.invalidate();
		          
		    }
			StringBuffer url = request.getRequestURL();
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
			response.sendRedirect(base+"login");
		} catch (Exception e) {
			
		}
	}
  
  }
