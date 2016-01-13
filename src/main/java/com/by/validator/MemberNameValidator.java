package com.by.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.by.json.MemberRequestJson;
import com.by.repository.MemberRepository;

@Component
@Qualifier("memberNameValidator")
public class MemberNameValidator implements Validator {
	@Autowired
	private MemberRepository repository;

	@Override
	public boolean supports(Class<?> clazz) {
		return MemberRequestJson.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberRequestJson member = (MemberRequestJson) target;
		Long count = repository.countByName(member.getName());
		if (count > 0) {
			errors.rejectValue("name", "name.unique","用户名不能重复");
		}
	}

}
