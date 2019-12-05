package com.fixit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fixit.domain.bo.QueryBo;

@Component
public class PostQuestionValidation  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return QueryBo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QueryBo query = (QueryBo) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "queryContent", "NotEmpty.query.queryContent");
		ValidationUtils.rejectIfEmpty(errors, "categories", "NotEmpty.query.categories");
		
		
	}

}
