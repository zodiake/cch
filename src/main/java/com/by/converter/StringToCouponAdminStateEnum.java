package com.by.converter;

import com.by.typeEnum.CouponAdminStateEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-12-14.
 */
public class StringToCouponAdminStateEnum implements Converter<String, CouponAdminStateEnum> {
    @Override
    public CouponAdminStateEnum convert(String source) {
        return CouponAdminStateEnum.fromString(source);
    }
}
