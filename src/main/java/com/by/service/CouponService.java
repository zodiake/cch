package com.by.service;

import com.by.model.Coupon;
import com.by.model.Member;

public interface CouponService {
	public Coupon bindMember(Coupon coupon, Member member);

	public Coupon save(Coupon coupon);
}
