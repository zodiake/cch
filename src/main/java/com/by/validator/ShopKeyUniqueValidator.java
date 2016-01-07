package com.by.validator;

import com.by.model.Shop;
import com.by.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Qualifier("shopKeyUniqueValidator")
public class ShopKeyUniqueValidator implements Validator {
    @Autowired
    private ShopRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Shop.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Shop s = (Shop) target;
        Long count = repository.countByShopKey(s.getShopKey());
        if (count > 0) {
            errors.rejectValue("shopKey","shop.shopKey.unique");
        }
    }
}
