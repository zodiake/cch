package com.by.service;

import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;

import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponMemberService {
    ShopCouponMember exchangeCoupon(Member member, ShopCoupon coupon, int total);

    ShopCouponMember useCoupon(String code, Member member);

    ShopCouponMember save(ShopCouponMember coupon);

    ShopCouponMember save(ShopCoupon coupon, Member member);

    Long countByCoupon(ShopCoupon coupon);

    List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member);
}
