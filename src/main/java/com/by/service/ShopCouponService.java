package com.by.service;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
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

    ShopCoupon findOneCache(Long id);

    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable);
}
