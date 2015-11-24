package com.by.model;

import java.io.Serializable;

// hibernate composite id class
public class MemberCouponId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long member;
	private Long coupon;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Long getCoupon() {
		return coupon;
	}

	public void setCoupon(Long coupon) {
		this.coupon = coupon;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof MemberCouponId) {
			MemberCouponId that = (MemberCouponId) o;
			return this.member.equals(that.member) && this.coupon.equals(that.coupon);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return member.hashCode() + coupon.hashCode();
	}
}
