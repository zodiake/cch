package com.by.converter;

import com.by.model.ParkingCoupon;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 15-11-30.
 */
public class StringToParkingCouponConverter implements Converter<String, ParkingCoupon> {
    @Override
    public ParkingCoupon convert(String source) {
        return new ParkingCoupon(Integer.valueOf(source));
    }
}
