package com.by.controller;

import com.by.model.Member;
import com.by.typeEnum.ValidEnum;

public interface UtilContoller {

	default boolean isValidMember(Member m) {
		if (m.getValid().equals(ValidEnum.VALID)) {
			return true;
		}
		return false;
	}

}
