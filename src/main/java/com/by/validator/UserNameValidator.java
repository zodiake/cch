package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.model.User;
import com.by.service.UserService;

@Component
@Qualifier("userNameValidator")
public class UserNameValidator implements Validator {
	@Autowired
	private UserService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User) target;
		Long count = service.countByName(u.getName());
		if (count > 0) {
			errors.rejectValue("name", "name.unique");
		}
	}
}