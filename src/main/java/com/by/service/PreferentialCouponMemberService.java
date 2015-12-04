package com.by.service;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponMemberService {
    PreferentialCouponMember useCoupon(PreferentialCoupon coupon, Member member, int total);

    PreferentialCouponMember exchangeCoupon(PreferentialCoupon coupon, Member member, int total);

    PreferentialCouponMember findByCouponAndMember(PreferentialCoupon coupon, Member member);

    Long sumTotalGroupByCoupon(PreferentialCoupon coupon);
}
