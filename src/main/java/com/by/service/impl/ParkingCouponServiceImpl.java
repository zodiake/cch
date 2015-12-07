package com.by.service.impl;

import com.by.model.ParkingCoupon;
import com.by.repository.ParkingCouponRepository;
import com.by.service.CouponService;
import com.by.service.ParkingCouponService;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingCouponServiceImpl implements ParkingCouponService {
    @Autowired
    private ParkingCouponRepository repository;
    @Autowired
    private CouponService couponService;

    @Override
    public ParkingCoupon save(ParkingCoupon coupon) {
        return repository.save(coupon);
    }

    @Override
    public Optional<ParkingCoupon> update(ParkingCoupon coupon) {
        return repository.findById(coupon.getId()).map(i -> {
            i.setAmount(coupon.getAmount());
            i.setName(coupon.getName());
            i.setScore(coupon.getScore());
            return i;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCoupon> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParkingCoupon> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ParkingCoupon findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Cacheable(value = "parkingCoupon")
    @Transactional(readOnly = true)
    public List<ParkingCoupon> findByValid(ValidEnum valid) {
        return repository.findByValid(valid);
    }

}
