package com.by.repository;

import com.by.model.Member;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 15-12-4.
 */
public interface GiftCouponMemberRepository extends PagingAndSortingRepository<GiftCouponMember, Long> {
    List<GiftCouponMember> findByCouponAndMember(GiftCoupon coupon, Member member);

    @Query("select count(*) from GiftCouponMember p where p.coupon=:coupon")
    Long countByCoupon(@Param("coupon") GiftCoupon coupon);

    Page<GiftCouponMember> findByMember(Member member, Pageable pageable);

    GiftCouponMember findByCode(String code);
}
