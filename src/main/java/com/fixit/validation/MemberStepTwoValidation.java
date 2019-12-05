package com.fixit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fixit.domain.bo.FixerProfile;
import com.fixit.domain.bo.MemberSignUpProfile;

@Component
public class MemberStepTwoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "NotEmpty.member.companyName");
		
		ValidationUtils.rejectIfEmpty(errors, "categories", "NotEmpty.memberProfile.industries");
	
	}

}
