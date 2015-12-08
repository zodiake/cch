package com.by.message;

import com.by.model.Member;
import com.by.model.ShopCoupon;

/**
 * Created by yagamai on 15-12-8.
 */
public class ShopCouponMessage {
    private ShopCoupon coupon;
    private Member member;
    private int total;

    public ShopCouponMessage(ShopCoupon coupon, Member member, int total) {
        this.coupon = coupon;
        this.member = member;
        this.total = total;
    }

    public ShopCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(ShopCoupon coupon) {
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
