package com.by.repository;

import com.by.model.Member;
import com.by.model.MemberCouponId;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingCouponMemberRepository extends PagingAndSortingRepository<ParkingCouponMember, MemberCouponId> {
    List<ParkingCouponMember> findByMember(Member member);

    Optional<ParkingCouponMember> findByMemberAndCoupon(Member member, ParkingCoupon coupon);
}
