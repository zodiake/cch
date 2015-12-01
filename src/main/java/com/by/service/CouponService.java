package com.by.service;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;

public interface CouponService {
    Coupon bindMember(Coupon coupon, Member member);

    Coupon save(Coupon coupon);

    Long count();

    Coupon findById(Long id);

    Long countBySummaryWhereMemberIsNull(CouponSummary summary);

    Long countBySummary(CouponSummary summary);
}
