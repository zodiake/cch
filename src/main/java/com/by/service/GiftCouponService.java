package com.by.service;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
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

    GiftCoupon findOne(Long id);

    GiftCoupon findOneCache(Long id);

    GiftCoupon findByIdAndValid(Long id, ValidEnum valid);

    Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable);

    Page<GiftCoupon> findAllByValidAndDateBetween(ValidEnum VALID, Calendar calendar, Pageable pageable);
}
