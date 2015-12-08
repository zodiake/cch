package com.by.service;

import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponService {
    ShopCoupon save(ShopCoupon coupon);

    ShopCoupon findOne(Long id);

    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);
}
