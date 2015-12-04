package com.by.service;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponExchangeHistory;

/**
 * Created by yagamai on 15-12-4.
 */
public interface PreferentialCouponExchangeHistoryService {
    PreferentialCouponExchangeHistory save(PreferentialCouponExchangeHistory history);

    PreferentialCouponExchangeHistory save(Member member, PreferentialCoupon coupon, int total);
}
