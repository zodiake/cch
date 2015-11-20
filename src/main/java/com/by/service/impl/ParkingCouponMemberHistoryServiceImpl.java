package com.by.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.by.model.Member;
import com.by.model.ParkingCouponMemberHistory;
import com.by.repository.ParkingCouponMemberHistroyRepository;
import com.by.service.ParkingCouponMemberHistroyService;

@Service
public class ParkingCouponMemberHistoryServiceImpl implements ParkingCouponMemberHistroyService {
	@Autowired
	private ParkingCouponMemberHistroyRepository repository;

	@Override
	public ParkingCouponMemberHistory save(ParkingCouponMemberHistory pceh) {
		return repository.save(pceh);
	}

	@Override
	public ParkingCouponMemberHistory save(Member member, int total, String license) {
		return repository.save(new ParkingCouponMemberHistory(member, license, total));
	}

	@Override
	public Page<ParkingCouponMemberHistory> findByMember(Member member, Pageable pageable) {
		return repository.findByMember(member, pageable);
	}
}
