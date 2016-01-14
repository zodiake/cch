package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Member;
import com.by.model.MemberCouponId;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;

public interface ParkingCouponMemberRepository extends PagingAndSortingRepository<ParkingCouponMember, MemberCouponId> {
	Page<ParkingCouponMember> findByMember(Member member, Pageable pageable);

	ParkingCouponMember findByMemberAndCoupon(Member member,ParkingCoupon coupon);
	
	List<ParkingCouponMember> findByMember(Member member,Sort sort);
}
