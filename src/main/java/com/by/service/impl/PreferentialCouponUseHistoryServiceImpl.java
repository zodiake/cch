package com.by.service.impl;

import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponUseHistory;
import com.by.repository.PreferentialCouponUseHistoryRepository;
import com.by.service.PreferentialCouponUseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-12-4.
 */
@Service
@Transactional
public class PreferentialCouponUseHistoryServiceImpl implements PreferentialCouponUseHistoryService {
    @Autowired
    private PreferentialCouponUseHistoryRepository repository;

    @Override
    public PreferentialCouponUseHistory save(PreferentialCouponUseHistory history) {
        return repository.save(history);
    }

    @Override
    public PreferentialCouponUseHistory save(PreferentialCoupon coupon, Member member) {
        PreferentialCouponUseHistory history = new PreferentialCouponUseHistory();
        history.setCoupon(coupon);
        history.setMember(member);
        return save(history);
    }
}
