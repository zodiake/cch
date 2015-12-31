package com.by.repository;

import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponMemberRepository extends PagingAndSortingRepository<ShopCouponMember, Long> {
    Long countByCoupon(ShopCoupon coupon);

    List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member);

    @Query("select sc from ShopCouponMember sc where sc.usedTime is null and sc.member=:member and sc.coupon.valid=:valid and sc.coupon.beginTime<:today and :today<sc.coupon.endTime order by sc.coupon.couponEndTime")
    Page<ShopCouponMember> findByMember(@Param("member") Member member, @Param("valid") ValidEnum valid, @Param("today") Calendar today, Pageable pageable);

    ShopCouponMember findByCode(String code);
}
