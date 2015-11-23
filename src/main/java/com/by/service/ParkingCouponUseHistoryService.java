package com.by.service;

import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponUseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-11-23.
 */
public interface ParkingCouponUseHistoryService {
    public ParkingCouponUseHistory save(ParkingCouponUseHistory history);

    public ParkingCouponUseHistory save(Member member, int total, String license, ParkingCoupon coupon);

    public Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable);
}