package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Member;
import com.by.model.MemberCouponId;
import com.by.model.ParkingCouponMember;

public interface ParkingCouponMemberRepository extends PagingAndSortingRepository<ParkingCouponMember, MemberCouponId> {
	Optional<ParkingCouponMember> findByMember(Member member);
}
