package com.by.repository;

import com.by.model.Coupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
    Page<Coupon> findByValid(ValidEnum valid,Pageable pageable);
}
