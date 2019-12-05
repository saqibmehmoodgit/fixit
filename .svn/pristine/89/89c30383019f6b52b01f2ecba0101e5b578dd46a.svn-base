package com.fixit.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fixit.domain.bo.UserBo;
import com.fixit.domain.vo.User;
import com.fixit.service.UserService;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FixitException;

@Component
public class MemberFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserBo member = (UserBo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.member.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.member.firstName");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.member.userName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty.member.city");
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.member.country");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.member.password");
		
		
		List<User> list;
		try {
			list = userService.checkExistingEmailOrUserName(member.getEmail(), member.getUserName());
		} catch (FixitException e) {
			list = null;
		}
		if (list != null && list.size() > 0) {
			User myUser = list.get(0);
			if (DbConstants.CUSTOMER.equals(myUser.getUserType())) {
				errors.rejectValue("exist", "user.exist", "This email has already been registered as a Member");
			} else {
				errors.rejectValue("exist", "user.exist", "This email has already been registered as a Fixer");
			}
			
		}

		if (!(member.getEmail() != null && member.getEmail().isEmpty())) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(member.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "email.incorrect", "Enter a valid email");
			}
		}

		if (!member.isSocialLogin()) {
			String password = member.getPassword();
			if (null != password && !password.trim().isEmpty()) {

				if (!password.matches(".*[A-Z].*")) {
					errors.rejectValue("password", "member.password", "must contain one uppercase letter");

				}

				if (!password.matches(".*[a-z].*")) {
					errors.rejectValue("password", "member.password", "must contain one lowercase letter");

				}

				if (!password.matches(".*\\d.*")) {
					errors.rejectValue("password", "member.password", "must contain one number");

				}
			}
		}

		String memberUserName = member.getUserName();

		if (null != memberUserName && !memberUserName.trim().isEmpty()) {
			int len = memberUserName.length();
			if (memberUserName.matches(USERNAME_REGEX)) {
				if (len < 6 || len > 12) {
					errors.rejectValue("userName", "userName.incorrect",
							"Username must be between 6-12 characters");

				}
			} else {
				errors.rejectValue("userName", "userName.incorrect", "must  be alphanumeric");
			}

		}

		

	}

}