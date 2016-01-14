package com.by.service;

import java.util.List;
import java.util.Optional;

import com.by.json.ParkingCouponHistoryJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.form.BaseCouponForm;
import com.by.json.CouponTemplateJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;

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

    Member useCoupon(Member member,ParkingCoupon coupon, int total, String license);

	ParkingCoupon findActivate();

	Page<ParkingCouponHistoryJson> findByMemberHistory(Member member, Pageable pageable);

	Long countByName(String name);
	
	ParkingCoupon findByName(String name);
}
