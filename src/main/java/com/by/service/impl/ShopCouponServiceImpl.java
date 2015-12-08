package com.by.service.impl;

import com.by.model.ShopCoupon;
import com.by.repository.ShopCouponRepository;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-12-8.
 */
@Service
@Transactional
public class ShopCouponServiceImpl implements ShopCouponService {
    @Autowired
    private ShopCouponRepository repository;

    @Override
    public ShopCoupon save(ShopCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("shopCoupon")
    public Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }
}
