package com.by.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.form.BaseCouponForm;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;

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

	<T extends Coupon> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb);
}
