package com.by.message;

import com.by.model.Coupon;
import com.by.model.Member;

/**
 * Created by yagamai on 15-12-2.
 */
public class CouponMessage {
	private Coupon coupon;
	private Member member;

	public CouponMessage(Member m, Coupon coupon) {
		this.member = m;
		this.coupon = coupon;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
