package com.by.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.by.model.Card;
import com.by.repository.CardRepository;

public class CardNameValidator implements ConstraintValidator<CardNameUnique, Card> {
	@Autowired
	private CardRepository repository;

	@Override
	public void initialize(CardNameUnique constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(Card value, ConstraintValidatorContext context) {
		Long count = repository.countByName(value.getName());
		if (count > 0)
			return false;
		return true;
	}

}
