package com.fixit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fixit.domain.bo.FixerProfile;
@Component
public class FixerStepTwoValidation implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
FixerProfile query = (FixerProfile) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "queryContent", "NotEmpty.query.queryContent");
		ValidationUtils.rejectIfEmpty(errors, "categories", "NotEmpty.profile.categories");
		ValidationUtils.rejectIfEmpty(errors, "industries", "NotEmpty.profile.industries");
		
	}

}
