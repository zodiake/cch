package com.by.converter;

import com.by.model.Member;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-11-30.
 */
public class StringToMemberConverter implements Converter<String, Member> {
    @Override
    public Member convert(String source) {
        return new Member(Long.valueOf(source));
    }
}
