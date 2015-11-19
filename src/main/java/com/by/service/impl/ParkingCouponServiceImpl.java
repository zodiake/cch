package com.by.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.ParkingCoupon;
import com.by.repository.ParkingCouponRepository;
import com.by.service.ParkingCouponService;
import com.google.common.collect.Lists;

@Service
@Transactional
public class ParkingCouponServiceImpl implements ParkingCouponService {
	@Autowired
	private ParkingCouponRepository repository;

	@Override
	public ParkingCoupon save(ParkingCoupon coupon) {
		return repository.save(coupon);
	}

	@Override
	public Optional<ParkingCoupon> update(ParkingCoupon coupon) {
		// TODO Auto-generated method stub
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

}
