package com.by.service;

import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;

import java.util.List;
import java.util.Optional;

public interface ParkingCouponService {
    ParkingCoupon save(ParkingCoupon coupon);

    Optional<ParkingCoupon> update(ParkingCoupon coupon);

    List<ParkingCoupon> findAll();

    Optional<ParkingCoupon> findById(Long id);

    ParkingCoupon findOne(Long id);

    List<ParkingCoupon> findByValid(ValidEnum valid);
}
