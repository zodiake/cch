package com.by.controller;

import org.springframework.stereotype.Component;

import com.by.model.Member;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public class BaseController {
	protected boolean isValidMember(Member m) {
		if (m.getValid().equals(ValidEnum.VALID)) {
			return true;
		}
		return false;
	}
}
