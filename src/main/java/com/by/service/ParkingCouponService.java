package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ParkingCouponService {
	ParkingCoupon save(ParkingCoupon coupon);

	Optional<ParkingCoupon> update(ParkingCoupon coupon);

	List<ParkingCoupon> findAll();

	Optional<ParkingCoupon> findById(int id);

	ParkingCoupon findOne(int id);

	ParkingCoupon findOneCache(int id);

	Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable);

	Page<CouponTemplateJson> findAll(BaseCouponForm from, Pageable pageable);

	Page<ParkingCoupon> findAll(Pageable pageable);

	Page<ParkingCoupon> findFirstPage(int size);

	ParkingCoupon validOrInValid(ParkingCoupon coupon);

	void exchangeCoupon(Member member, ParkingCoupon coupon, int total);

	Member useCoupon(Member member, int total, String license);

	ParkingCoupon findActivate();
}
