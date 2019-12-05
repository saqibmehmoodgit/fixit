package com.fixit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
	
		
		String message=exception.getMessage();
		if("D".equals(message)){
			response.sendRedirect("login?error=D"); 
		}else if("R".equals(message)){
			response.sendRedirect("login?error=R"); 
		}else{
			response.sendRedirect("login?error=1"); 
		}
		
		
	}

	public String myAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
	
		
		String message=exception.getMessage();
		if("D".equals(message)){
			return "login?error=D"; 
		}else if("R".equals(message)){
			return "login?error=R"; 
		}else if("SNF".equals(message)){
			return "login?error=SNF"; 
		}
		else{
			return "login?error=1"; 
		}
		
		
	}
}
