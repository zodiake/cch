package com.by.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.by.model.ParkingCoupon;

/**
 * Created by yagamai on 15-11-30.
 */
public class StringToParkingCouponConverter implements Converter<String, ParkingCoupon> {
    @Override
    public ParkingCoupon convert(String source) {
		if (StringUtils.isEmpty(source))
			return null;
        return new ParkingCoupon(Integer.valueOf(source));
    }
}
