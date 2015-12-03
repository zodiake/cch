package com.by.service.impl;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.service.PreferentialCouponMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-12-3.
 */
@Service
@Transactional
public class PreferentialCouponMemberServiceImpl implements PreferentialCouponMemberService{
    @Override
    public PreferentialCoupon useCoupon(PreferentialCoupon coupon, Member member, int total) {
        return null;
    }
}
