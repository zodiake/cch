package com.by.service.impl;

import com.by.model.Coupon;
import com.by.service.CouponService;
import com.by.typeEnum.ValidEnum;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class CouponServiceImpl implements CouponService {

    @Override
    public boolean isValidCoupon(Coupon coupon) {
        if (coupon.getValid().equals(ValidEnum.VALID)) {
            if (coupon.getBeginTime() == null && coupon.getEndTime() == null)
                return true;
            if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
                coupon.getEndTime().add(1, Calendar.DATE);
                Calendar today = Calendar.getInstance();
                if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

