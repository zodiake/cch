package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.model.Member;

/**
 * Created by yagamai on 15-11-30.
 */
public class StringToMemberConverter implements Converter<String, Member> {
    @Override
    public Member convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
        return new Member(Long.valueOf(source));
    }
}
