package com.by.service;

import com.by.model.Coupon;

public interface CouponService {
    boolean isValidCoupon(Coupon coupon);

    boolean noStorageLimited(Coupon coupon);

    boolean isValid(Coupon coupon);

    boolean withinValidDate(Coupon couponSummary);

    boolean isDuplicateCoupon(Coupon couponSummary);

    boolean isPermanent(Coupon couponSummary);
}
