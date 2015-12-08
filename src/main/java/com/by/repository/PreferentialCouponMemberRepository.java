package com.by.repository;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 15-12-4.
 */
public interface PreferentialCouponMemberRepository extends PagingAndSortingRepository<PreferentialCouponMember, Long> {
    List<PreferentialCouponMember> findByCouponAndMember(PreferentialCoupon coupon, Member member);

    @Query("select count(*) from PreferentialCouponMember p where p.coupon=:coupon")
    Long countByCoupon(@Param("coupon") PreferentialCoupon coupon);

    Page<PreferentialCouponMember> findByMember(Member member, Pageable pageable);

    PreferentialCouponMember findByCode(String code);
}
