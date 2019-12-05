package com.fixit.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncode {

	public static String encodePassword(String rawPassword){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		return  encoder.encode(rawPassword);
	}
	
	public static Boolean comparePasswords(String password,String dbPassword){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (passwordEncoder.matches(password, dbPassword)) {
		    // Encode new password and store it
			return true;
		} else {
		    // Report error
		return false;
		}
	}
}
