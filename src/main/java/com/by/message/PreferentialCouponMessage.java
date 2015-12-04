package com.by.message;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;

/**
 * Created by yagamai on 15-12-2.
 */
public class PreferentialCouponMessage {
    private PreferentialCoupon coupon;
    private Member member;
    private int total;

    public PreferentialCouponMessage(PreferentialCoupon coupon, Member member, int total) {
        this.coupon = coupon;
        this.member = member;
        this.total = total;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
