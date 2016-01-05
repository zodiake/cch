package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.typeEnum.DuplicateEnum;

/**
 * Created by yagamai on 15-12-15.
 */
public class StringToDuplicateEnum implements Converter<String, DuplicateEnum> {
    @Override
    public DuplicateEnum convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
        return DuplicateEnum.fromString(source);
    }
}
