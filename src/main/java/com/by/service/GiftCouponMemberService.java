package com.by.service;

import com.by.json.CouponJson;
import com.by.model.Member;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 15-12-3.
 */
public interface GiftCouponMemberService {
    GiftCouponMember useCoupon(String code, Member member);

    void exchangeCoupon(GiftCoupon coupon, Member member, int total);

    List<GiftCouponMember> findByCouponAndMember(GiftCoupon coupon, Member member);

    Long sumTotalGroupByCoupon(GiftCoupon coupon);

    List<CouponJson> findByMember(Member member, Pageable pageable);
}
