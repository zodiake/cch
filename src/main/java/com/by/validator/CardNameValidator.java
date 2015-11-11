package com.by.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.by.repository.CardRepository;

public class CardNameValidator implements ConstraintValidator<CardNameUnique, String> {
	@Autowired
	private CardRepository repository;

	@Override
	public void initialize(CardNameUnique constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Long count = repository.countByName(value);
		if (count > 0)
			return false;
		return true;
	}

}
