package com.by.repository;

import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponRepository extends CrudRepository<ShopCoupon, Long> {
    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);
}
