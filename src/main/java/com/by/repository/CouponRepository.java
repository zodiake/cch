package com.by.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Coupon;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
}
