package com.by.service;

import java.util.List;
import java.util.Optional;

import com.by.model.ParkingCoupon;

public interface ParkingCouponService {
	public ParkingCoupon save(ParkingCoupon coupon);
	
	public Optional<ParkingCoupon> update(ParkingCoupon coupon);
	
	public List<ParkingCoupon> findAll();
	
	public Optional<ParkingCoupon> findById(Long id);

	ParkingCoupon findOne(Long id);
}
