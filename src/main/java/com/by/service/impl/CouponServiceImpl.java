package com.by.service.impl;

import com.by.model.Coupon;
import com.by.service.CouponService;
import com.by.typeEnum.DuplicateEnum;
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
                // TODO: 15-12-4
                coupon.getEndTime().add(1, Calendar.DATE);
                Calendar today = Calendar.getInstance();
                if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean noStorageLimited(Coupon coupon) {
        return coupon.getTotal() == 0;
    }


    public boolean isValid(Coupon coupon) {
        return coupon.getValid().equals(ValidEnum.VALID);
    }

    public boolean withinValidDate(Coupon couponSummary) {
        Calendar today = Calendar.getInstance();
        couponSummary.getEndTime().add(1, Calendar.DATE);
        return couponSummary.getBeginTime().before(today) && couponSummary.getEndTime().after(today);
    }

    public boolean isDuplicateCoupon(Coupon couponSummary) {
        return couponSummary.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
    }


    public boolean isPermanent(Coupon couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

}

