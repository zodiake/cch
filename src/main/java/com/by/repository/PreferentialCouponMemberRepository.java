package com.by.repository;

import com.by.model.Member;
import com.by.model.MemberCouponId;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-12-4.
 */
public interface PreferentialCouponMemberRepository extends PagingAndSortingRepository<PreferentialCouponMember, MemberCouponId> {
    PreferentialCouponMember findByCouponAndMember(PreferentialCoupon coupon, Member member);

    @Query("select sum(p.total) from PreferentialCouponMember p where p.coupon=:coupon")
    Long sumTotalGroupByCoupon(PreferentialCoupon coupon);
}
