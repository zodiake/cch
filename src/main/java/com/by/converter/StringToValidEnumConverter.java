package com.by.converter;

import org.springframework.core.convert.converter.Converter;

import com.by.typeEnum.ValidEnum;

public class StringToValidEnumConverter implements Converter<String, ValidEnum> {
	@Override
	public ValidEnum convert(String source) {
		return ValidEnum.fromString(source);
	}
}
