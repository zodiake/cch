package com.by.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
