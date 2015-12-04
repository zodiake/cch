package com.by.service;

import com.by.model.ParkingCoupon;
import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Member;
import com.by.model.ParkingCouponExchangeHistory;

public interface ParkingCouponExchangeHistoryService {
    ParkingCouponExchangeHistory save(ParkingCouponExchangeHistory pceh);

    ParkingCouponExchangeHistory save(Member member ,ParkingCoupon coupon,int total);

    Page<ParkingCouponExchangeHistory> findByMember(Member member, Pageable pageable);
}
