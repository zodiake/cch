package com.by.service;

import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponMemberService {
    PreferentialCouponMember useCoupon(String code, Member member);

    void exchangeCoupon(PreferentialCoupon coupon, Member member, int total);

    List<PreferentialCouponMember> findByCouponAndMember(PreferentialCoupon coupon, Member member);

    Long sumTotalGroupByCoupon(PreferentialCoupon coupon);

    List<CouponJson> findByMember(Member member, Pageable pageable);
}
