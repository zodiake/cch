package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.json.GiftCouponJson;
import com.by.model.GiftCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */
public interface GiftCouponService {
    GiftCoupon save(GiftCoupon coupon);

    GiftCoupon update(GiftCoupon coupon);

    GiftCoupon findOne(int id);

    GiftCoupon findOneCache(int id);

    GiftCoupon findByIdAndValid(int id, ValidEnum valid);

    Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<GiftCouponJson> findAll(BaseCouponForm form, Pageable pageable);

    Page<GiftCoupon> findAllByValidAndDateBetween(ValidEnum VALID, Calendar calendar, Pageable pageable);
}
