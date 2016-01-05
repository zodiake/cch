package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.typeEnum.CouponAdminStateEnum;

/**
 * Created by yagamai on 15-12-14.
 */
public class StringToCouponAdminStateEnum implements Converter<String, CouponAdminStateEnum> {
    @Override
    public CouponAdminStateEnum convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
        return CouponAdminStateEnum.fromString(source);
    }
}
