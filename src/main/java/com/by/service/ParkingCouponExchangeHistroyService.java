package com.by.service;

import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Member;
import com.by.model.ParkingCouponExchangeHistory;

public interface ParkingCouponExchangeHistroyService {
    public ParkingCouponExchangeHistory save(ParkingCouponExchangeHistory pceh);

    public ParkingCouponExchangeHistory save(Member member, int total, Shop shop);

    public Page<ParkingCouponExchangeHistory> findByMember(Member member, Pageable pageable);
}
