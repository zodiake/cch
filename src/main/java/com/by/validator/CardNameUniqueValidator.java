package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.model.Card;
import com.by.repository.CardRepository;

@Component
@Qualifier("cardNameUniqueValidator")
public class CardNameUniqueValidator implements Validator {
	@Autowired
	private CardRepository repository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Card.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Card c = (Card) target;
		Long count = repository.countByName(c.getName());
		if (count > 0) {
			errors.rejectValue("name", "name.unique");
		}
	}
}
