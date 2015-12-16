package com.by.service;

import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {
	boolean isWithinValidDate(Coupon coupon);

	boolean noStorageLimited(Coupon coupon);

	boolean isValid(Coupon coupon);

	boolean withinValidDate(Coupon couponSummary);

	boolean isDuplicateCoupon(Coupon couponSummary);

	boolean isPermanent(Coupon couponSummary);

	boolean couponIsWithinValidDate(Coupon coupon);

	boolean canUpdate(Coupon coupon);

	Page<CouponJson> findAll(Pageable pageable);

	Page<CouponJson> findByMember(Member member, Pageable pageable);
}
