package com.by.repository;

import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-12-4.
 */
public interface GiftCouponMemberRepository extends PagingAndSortingRepository<GiftCouponMember, Long> {
    List<GiftCouponMember> findByCouponAndMember(GiftCoupon coupon, Member member);

    @Query("select count(*) from GiftCouponMember p where p.coupon=:coupon")
    Long countByCoupon(@Param("coupon") GiftCoupon coupon);

    @Query("select gc from GiftCouponMember gc where gc.usedTime is null and gc.member=:member and gc.coupon.valid=:valid and gc.coupon.beginTime<:today and :today<gc.coupon.endTime order by gc.coupon.couponEndTime")
    Page<GiftCouponMember> findByMemberAndValid(@Param("member") Member member, @Param("valid") ValidEnum valid, @Param("today") Calendar today, Pageable pageable);

    Page<GiftCouponMember> findByMember(Member member, Pageable pageable);

    GiftCouponMember findByCode(String code);
}
