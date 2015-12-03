package com.by.service;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponMemberService {
    PreferentialCoupon useCoupon(PreferentialCoupon coupon, Member member, int total);
}
