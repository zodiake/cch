package com.by.service;

import com.by.form.CouponQueryForm;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface CouponService {
	boolean isValidCoupon(Coupon coupon);

	boolean noStorageLimited(Coupon coupon);

	boolean isValid(Coupon coupon);

	boolean withinValidDate(Coupon couponSummary);

	boolean isDuplicateCoupon(Coupon couponSummary);

	boolean isPermanent(Coupon couponSummary);

	boolean couponIsWithinValidDate(Coupon coupon);

	boolean canUpdate(Coupon coupon);

	Page<CouponJson> findAll(Pageable pageable);

	Page<CouponJson> findByMember(Member member, Pageable pageable);

	<T extends Coupon> Predicate[] getPredicateList(CouponQueryForm form, Root<T> root, CriteriaBuilder cb);
}
