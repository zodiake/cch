package com.by.service;

import java.util.List;

import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;

public interface CouponService {

	Coupon save(Coupon coupon);

	Long count();

	Coupon findById(Long id);

	List<Coupon> findByMember(Member member);

	List<CouponJson> findByMemberJson(Member member);
}
