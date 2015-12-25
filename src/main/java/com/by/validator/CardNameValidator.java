package com.by.validator;

import com.by.model.Card;
import com.by.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("cardNameValidator")
public class CardNameValidator implements Validator {
	@Autowired
	private CardRepository repository;
	private final String reason = "名字重复";

	@Override
	public boolean supports(Class<?> clazz) {
		return Card.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Card c = (Card) target;
		Card another = repository.findByName(c.getName());
		if (c.getId() != another.getId()) {
			errors.rejectValue("name", reason);
		}
	}
}
