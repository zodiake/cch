package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Member;
import com.by.model.ParkingCouponMember;
import com.by.model.ParkingCouponMember.MemberCouponId;

public interface ParkingCouponMemberRepository extends PagingAndSortingRepository<ParkingCouponMember, MemberCouponId> {
	Optional<ParkingCouponMember> findByMember(Member member);
}
