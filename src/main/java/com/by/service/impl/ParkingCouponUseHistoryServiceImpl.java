package com.by.service.impl;

import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponUseHistory;
import com.by.repository.ParkingCouponUseHistoryRepository;
import com.by.service.ParkingCouponUseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-11-23.
 */
@Service
@Transactional
public class ParkingCouponUseHistoryServiceImpl implements ParkingCouponUseHistoryService {
    @Autowired
    private ParkingCouponUseHistoryRepository repository;

    @Override
    public ParkingCouponUseHistory save(ParkingCouponUseHistory history) {
        return repository.save(history);
    }

    @Override
    public ParkingCouponUseHistory save(Member member, int total, String license, ParkingCoupon coupon) {
        return repository.save(new ParkingCouponUseHistory(coupon, member, total, license));
    }

    @Override
    public Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member,pageable);
    }

}
