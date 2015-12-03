package com.by.message;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;

/**
 * Created by yagamai on 15-12-2.
 */
public class PreferentialCouponMessage {
    private PreferentialCoupon coupon;
    private Member member;

    public PreferentialCouponMessage(PreferentialCoupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
    }

    public PreferentialCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(PreferentialCoupon coupon) {
        this.coupon = coupon;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
