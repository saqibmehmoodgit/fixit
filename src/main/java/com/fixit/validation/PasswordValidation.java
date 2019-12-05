package com.fixit.validation;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fixit.domain.bo.ValidatePassword;

@Component
public class PasswordValidation implements Validator{

	private ValidatePassword validatePassword;
	
	
	
	
	public ValidatePassword getValidatePassword() {
		return validatePassword;
	}

	public void setValidatePassword(ValidatePassword validatePassword) {
		this.validatePassword = validatePassword;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ValidatePassword.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidatePassword validatePassword = (ValidatePassword)target;
		if(!validatePassword.getNewPassword().equals(validatePassword.getConfirmPassword())){
			errors.rejectValue("mismatchPassword", "password.mismatch", "NewPassword and Confirm Password Don't Match");
		}

		String password = validatePassword.getNewPassword();
		if (null != password && !password.trim().isEmpty()) {
			if (!password.matches(".*[A-Z].*")){
			errors.rejectValue("newPassword", "password.newPassword", "must contain one uppercase letter");

		}

	     if (!password.matches(".*[a-z].*")){
				errors.rejectValue("newPassword", "password.newPassword", "must contain one lowercase letter");

	     }

	     if (!password.matches(".*\\d.*")) {
				errors.rejectValue("newPassword", "password.newPassword", "must contain one number");

	     }
        }
	
	}

	
}
