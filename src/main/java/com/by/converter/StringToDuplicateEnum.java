package com.by.converter;

import com.by.typeEnum.DuplicateEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-12-15.
 */
public class StringToDuplicateEnum implements Converter<String, DuplicateEnum> {
    @Override
    public DuplicateEnum convert(String source) {
        return DuplicateEnum.fromString(source);
    }
}
