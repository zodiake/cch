package com.by.service;

import com.by.model.PreferentialCoupon;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponService {
    PreferentialCoupon save(PreferentialCoupon coupon);

    PreferentialCoupon findOne(Long id);

    PreferentialCoupon findByIdAndValid(Long id, ValidEnum valid);
}
