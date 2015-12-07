package com.by.repository;

import com.by.model.Member;
import com.by.model.MemberCouponId;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingCouponMemberRepository extends PagingAndSortingRepository<ParkingCouponMember, MemberCouponId> {
    List<ParkingCouponMember> findByMember(Member member);

    ParkingCouponMember findByMemberAndCoupon(Member member, ParkingCoupon coupon);

    @Query("select sum(p.total) from ParkingCouponMember p where p.coupon=:coupon")
    Long sumTotalGroupByCoupon(@Param("coupon") ParkingCoupon coupon);

    Long countByCouponAndMember(ParkingCoupon parkingCoupon, Member member);

    Page<ParkingCouponMember> findByMember(Member member, Pageable pageable);
}
