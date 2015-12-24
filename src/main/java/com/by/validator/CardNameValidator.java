package com.by.validator;

import com.by.model.Card;
import com.by.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("cardNameValidator")
public class CardNameValidator implements Validator {
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
        if (count > 0)
            errors.rejectValue("name", "duplicate");
    }
}
