package com.fixit.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.fixit.domain.bo.FixerRegisterBo;
import com.fixit.domain.vo.User;
import com.fixit.service.UserService;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FixitException;

@Component
public class FixerFormValidator implements Validator {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String LINKEDIN_PATTERN = "^(?:https?://)?([a-zA-Z]{1,15}(\\.))?linkedin\\.com\\/.*$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";

	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return FixerRegisterBo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		FixerRegisterBo user = (FixerRegisterBo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.fixer.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.fixer.firstName");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "linkedinProfile", "NotEmpty.fixer.linkedinProfile");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty.fixer.city");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.fixer.country");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.fixer.userName");

		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.fixer.password");

		if ((user.getMobileNumber().length() < 8 || user.getMobileNumber().length() > 15)
				&& (user.getMobileNumber() != null && !user.getMobileNumber().isEmpty())) {
			errors.rejectValue("mobileNumber", "fixer.mobileNumber", "Enter valid mobile number");
		}

		

		List<User> list;
		try {
			list = userService.checkExistingEmailOrUserName(user.getEmail(), user.getUserName());
		} catch (FixitException e) {
			list = null;
		}
		if (list != null && list.size() > 0) {
			User myUser = list.get(0);
			if (DbConstants.CUSTOMER.equals(myUser.getUserType())) {
				errors.rejectValue("exist", "fixer.exist", "This email has already been registered as a Member");
			} else {
				errors.rejectValue("exist", "fixer.exist", "This email has already been registered as a Fixer");
			}

		}
		if (!(user.getEmail() != null && user.getEmail().isEmpty())) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(user.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "email.incorrect", "Enter a valid email");
			}
		}
		
		
		String fixerUserName = user.getUserName();

		if (null != fixerUserName && !fixerUserName.trim().isEmpty()) {
			int len = fixerUserName.length();
			if (fixerUserName.matches(USERNAME_REGEX)) {
				if (len < 6 || len > 12) {
					errors.rejectValue("userName", "userName.incorrect", "Username must be between  6-12 characters");

				}
			}else{
				errors.rejectValue("userName", "userName.incorrect", "must  be alphanumeric");
			}
			
		}
	

		
		if(!user.isSocialLogin()){
			String password = user.getPassword();
			if (null != password && !password.trim().isEmpty()) {
				if (!password.matches(".*[A-Z].*")){
				errors.rejectValue("password", "fixer.password", "must contain one uppercase letter");

			}

		     if (!password.matches(".*[a-z].*")){
					errors.rejectValue("password", "fixer.password", "must contain one lowercase letter");

		     }

		     if (!password.matches(".*\\d.*")) {
					errors.rejectValue("password", "fixer.password", "must contain one number");

		     }
            }
		}
		
		String linkedinProfile = user.getLinkedinProfile();
		if (!(linkedinProfile != null && linkedinProfile.isEmpty())) {
			pattern = Pattern.compile(LINKEDIN_PATTERN, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(linkedinProfile);
			if (!matcher.matches()) {
				errors.rejectValue("linkedinProfile", "fixer.linkedinProfile", "Please enter a valid LinkedIn url");
			} 
			
		}
	}

}
