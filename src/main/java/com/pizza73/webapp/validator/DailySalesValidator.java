package com.pizza73.webapp.validator;

import com.pizza73.model.DailySales;
import com.pizza73.webapp.base.validator.BaseDWRValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DailySalesValidator extends BaseDWRValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return DailySales.class.isAssignableFrom(clazz);
	}

	public void validate(Object object, Errors errors) {
		DailySales dailySales= (DailySales) object;
	}
}
