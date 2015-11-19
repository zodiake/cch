package com.by.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.ParkingCouponMemberHistory;

public interface ParkingCouponMemberHistroyRepository
		extends PagingAndSortingRepository<ParkingCouponMemberHistory, Long> {
	
}
