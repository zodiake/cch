package com.by.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Member;
import com.by.model.ParkingCouponExchangeHistory;

public interface ParkingCouponExchangeHistoryRepository
		extends PagingAndSortingRepository<ParkingCouponExchangeHistory, Long> {
	Page<ParkingCouponExchangeHistory> findByMember(Member member, Pageable pageable);
}
