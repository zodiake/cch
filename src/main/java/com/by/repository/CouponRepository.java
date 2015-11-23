package com.by.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Coupon;
import com.by.model.CouponSummary;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
	Page<Coupon> findBySummary(CouponSummary summary,Pageable pageable);
}
