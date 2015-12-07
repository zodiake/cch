package com.by.repository;

import com.by.model.PreferentialCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponRepository extends PagingAndSortingRepository<PreferentialCoupon, Long> {
    PreferentialCoupon findByIdAndValid(Long id, ValidEnum valid);

    Page<PreferentialCoupon> findByValid(ValidEnum valid, Pageable pageable);
}