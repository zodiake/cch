package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.model.CardRule;
import com.by.service.CardRuleService;

@Component
@Qualifier("cardRuleNameValidator")
public class CardRuleNameValidator implements Validator {
	@Autowired
	private CardRuleService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return CardRule.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CardRule rule = (CardRule) target;
		Long count = service.countByName(rule.getName());
		if (count > 0) {
			errors.rejectValue("name", "name.unique");
		}
	}
}
