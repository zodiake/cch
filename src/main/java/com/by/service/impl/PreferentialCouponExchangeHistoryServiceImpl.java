package com.by.service.impl;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponExchangeHistory;
import com.by.repository.PreferentialCouponExchangeHistoryRepository;
import com.by.service.MemberService;
import com.by.service.PreferentialCouponExchangeHistoryService;
import com.by.service.PreferentialCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-12-4.
 */
@Service
@Transactional
public class PreferentialCouponExchangeHistoryServiceImpl implements PreferentialCouponExchangeHistoryService {
    @Autowired
    private PreferentialCouponExchangeHistoryRepository repository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PreferentialCouponService preferentialCouponService;

    @Override
    public PreferentialCouponExchangeHistory save(PreferentialCouponExchangeHistory history) {
        return repository.save(history);
    }

    @Override
    public PreferentialCouponExchangeHistory save(Member member, PreferentialCoupon coupon, int total) {
        Member m=memberService.findOne(member.getId());
        PreferentialCoupon c=preferentialCouponService.findOne(coupon.getId());
        return repository.save(new PreferentialCouponExchangeHistory(m, c, total));
    }
}
