package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.model.Card;
import com.by.model.Shop;
import com.by.repository.ShopRepository;

@Component
@Qualifier("shopKeyValidator")
public class ShopKeyValidator implements Validator {
	@Autowired
	private ShopRepository repository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Card.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Shop s = (Shop) target;
		Shop another = repository.findByShopKey(s.getShopKey());
		if (another != null && s.getId() != another.getId()) 
			errors.rejectValue("shopKey", null, "该Pos - key已存在");
	}
}
