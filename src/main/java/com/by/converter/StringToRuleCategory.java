package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.model.RuleCategory;

public class StringToRuleCategory implements Converter<String, RuleCategory> {

	public RuleCategory convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
		return new RuleCategory(Integer.parseInt(source));
	}

}
