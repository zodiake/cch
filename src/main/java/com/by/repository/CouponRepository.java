package com.by.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Coupon;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
	Coupon findByCode(String code);
}
