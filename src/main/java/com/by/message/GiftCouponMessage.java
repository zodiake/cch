package com.by.message;

import com.by.model.Member;
import com.by.model.GiftCoupon;

/**
 * Created by yagamai on 15-12-2.
 */
public class GiftCouponMessage {
    private GiftCoupon coupon;
    private Member member;
    private int total;

    public GiftCouponMessage(GiftCoupon coupon, Member member, int total) {
        this.coupon = coupon;
        this.member = member;
        this.total = total;
    }

    public GiftCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(GiftCoupon coupon) {
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
