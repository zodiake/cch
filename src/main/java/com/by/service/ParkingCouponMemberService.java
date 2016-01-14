package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.by.json.ParkingCouponMemberJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;

public interface ParkingCouponMemberService {
	Page<ParkingCouponMember> findByMember(Member member, Pageable pageable);

	ParkingCouponMember save(ParkingCouponMember pcm);

	Page<ParkingCouponMemberJson> toJson(Page<ParkingCouponMember> page, Pageable pageable);

	ParkingCouponMember save(Member member, ParkingCoupon pc, int total);

	ParkingCouponMember findByMemberAndCoupon(Member member, ParkingCoupon coupon);
	
	List<ParkingCouponMember> findByMember(Member member,Sort sort);
}
