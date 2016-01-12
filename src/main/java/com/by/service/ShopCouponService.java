package com.by.service;

import java.util.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.exception.Status;
import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponService {
    ShopCoupon save(ShopCoupon coupon);

    ShopCoupon update(ShopCoupon coupon);

    ShopCoupon findOne(int id);

    ShopCoupon findOneCache(int id);

    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<ShopCouponJson> findAll(ShopCouponForm form, Pageable pageable);

    Page<ShopCoupon> findAllByValidAndDateBetween(ValidEnum valid, Calendar calendar, Pageable pageable);

	Status valid(int id);
}
