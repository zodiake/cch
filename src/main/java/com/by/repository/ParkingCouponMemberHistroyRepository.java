package com.by.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Member;
import com.by.model.ParkingCouponMemberHistory;

public interface ParkingCouponMemberHistroyRepository
		extends PagingAndSortingRepository<ParkingCouponMemberHistory, Long> {
	Page<ParkingCouponMemberHistory> findByMember(Member member,Pageable pageable);
}
