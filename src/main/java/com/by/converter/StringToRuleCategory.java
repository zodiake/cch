package com.by.converter;

import org.springframework.core.convert.converter.Converter;

import com.by.model.RuleCategory;

public class StringToRuleCategory implements Converter<String, RuleCategory>{

	public RuleCategory convert(String source) {
		return new RuleCategory(Integer.parseInt(source));
	}

}
