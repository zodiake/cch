package com.by.controller;

import com.by.model.Member;
import com.by.typeEnum.ValidEnum;

public interface UtilContoller {
	int maxSize=7;

	default boolean isValidMember(Member m) {
		if (m.getValid().equals(ValidEnum.VALID)) {
			return true;
		}
		return false;
	}

	default int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages;
		else
			return maxSize;
	}
}
