package com.by.typeEnum;

import java.util.Locale;

public enum ValidEnum {
	INVALID, VALID;
	public static ValidEnum fromString(String source) {
		try {
			return ValidEnum.valueOf(source.toUpperCase(Locale.US));
		} catch (Exception e) {
			return null;
		}
	}
}
