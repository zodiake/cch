package com.by.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.repository.CouponSummaryRepository;
import com.by.service.CouponSummaryService;

@Service
@Transactional
public class CouponSummaryServiceImpl implements CouponSummaryService {
	@Autowired
	private CouponSummaryRepository repository;

	@Override
	public CouponSummary save(CouponSummary summary) {
		summary.getTotal();
		List<Coupon> coupons = new ArrayList<>();
		for (int i = 1; i <= summary.getTotal(); i++) {
			Coupon c = new Coupon();
			String uuid = UUID.randomUUID().toString();
			String code = uuid.replace("-", "");
			c.setCode(code);
			c.setSummary(summary);
			coupons.add(c);
		}
		summary.setCoupons(coupons);
		return repository.save(summary);
	}
}
