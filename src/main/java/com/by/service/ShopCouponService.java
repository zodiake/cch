package com.by.service;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponService {
    ShopCoupon save(ShopCoupon coupon);

    ShopCoupon findOne(int id);

    ShopCoupon findOneCache(int id);

    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable);

    Page<ShopCoupon> findAllByValidAndDateBetween(ValidEnum valid, Calendar calendar, Pageable pageable);
}
