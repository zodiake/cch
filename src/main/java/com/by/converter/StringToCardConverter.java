package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.model.Card;

/**
 * Created by yagamai on 15-12-22.
 */
public class StringToCardConverter implements Converter<String, Card> {
	@Override
	public Card convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
		return new Card(Integer.parseInt(source));
	}
}
