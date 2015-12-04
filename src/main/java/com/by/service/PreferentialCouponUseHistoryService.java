package com.by.service;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponUseHistory;

/**
 * Created by yagamai on 15-12-4.
 */
public interface PreferentialCouponUseHistoryService {
    PreferentialCouponUseHistory save(PreferentialCouponUseHistory history);

    PreferentialCouponUseHistory save(PreferentialCoupon coupon, Member member,int total);
}
