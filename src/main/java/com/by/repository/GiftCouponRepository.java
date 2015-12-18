package com.by.repository;

import com.by.model.GiftCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-12-3.
 */
public interface GiftCouponRepository extends PagingAndSortingRepository<GiftCoupon, Long> {
    GiftCoupon findByIdAndValid(Long id, ValidEnum valid);

    Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable);
}
