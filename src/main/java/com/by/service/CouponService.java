package com.by.service;

import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;

import java.util.List;

public interface CouponService {
    Coupon bindMember(CouponSummary summary, Member member);

    Coupon save(Coupon coupon);

    Long count();

    Coupon findById(Long id);

    Long countBySummaryWhereMemberIsNull(CouponSummary summary);

    Long countBySummary(CouponSummary summary);

    Long countBySummaryAndMember(CouponSummary summary, Member member);

    List<Coupon> findByMember(Member member);

    List<CouponJson> findByMemberJson(Member member);
}
