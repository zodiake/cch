package com.by.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Member;
import com.by.model.ParkingCouponMemberHistory;

public interface ParkingCouponMemberHistroyService {
	public ParkingCouponMemberHistory save(ParkingCouponMemberHistory pceh);

	public ParkingCouponMemberHistory save(Member member, int total, String license);
	
	public Page<ParkingCouponMemberHistory> findByMember(Member member,Pageable pageable);
}
