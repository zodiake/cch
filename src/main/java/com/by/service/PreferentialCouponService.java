package com.by.service;

import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.PreferentialCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-12-3.
 */
public interface PreferentialCouponService {
    PreferentialCoupon save(PreferentialCoupon coupon);

    PreferentialCoupon findOne(Long id);

    PreferentialCoupon findOneCache(Long id);

    PreferentialCoupon findByIdAndValid(Long id, ValidEnum valid);

    Page<PreferentialCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<CouponTemplateJson> findAll(CouponQueryForm form, Pageable pageable);
}
