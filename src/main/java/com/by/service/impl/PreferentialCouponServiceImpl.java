package com.by.service.impl;

import com.by.model.PreferentialCoupon;
import com.by.repository.PreferentialCouponRepository;
import com.by.service.PreferentialCouponService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by yagamai on 15-12-4.
 */
@Service
@Transactional
public class PreferentialCouponServiceImpl implements PreferentialCouponService {
    @Autowired
    private PreferentialCouponRepository repository;

    @Override
    public PreferentialCoupon save(PreferentialCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public PreferentialCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public PreferentialCoupon findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }
}
