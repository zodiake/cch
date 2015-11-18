package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Coupon;
import com.by.model.Member;
import com.by.repository.CouponRepository;
import com.by.service.CouponService;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
	@Autowired
	private CouponRepository repository;

	@Override
	public Coupon bindMember(Coupon coupon, Member member) {
		return null;
	}

	@Override
	public Coupon save(Coupon coupon) {
		return repository.save(coupon);
	}
	
	public void save(){
		
	}

}
