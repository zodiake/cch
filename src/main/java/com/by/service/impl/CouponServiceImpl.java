package com.by.service.impl;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.repository.CouponRepository;
import com.by.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository repository;

    @Override
    public Coupon bindMember(Coupon coupon, Member member) {
        Coupon source = repository.findOne(coupon.getId());
        assert source.getMember() == null;
        source.setMember(member);
        return source;
    }

    public Coupon useCoupon(Coupon coupon) {
        return null;
    }

    @Override
    public Coupon save(Coupon coupon) {
        return repository.save(coupon);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Coupon findById(Long id) {
        Coupon coupon = repository.findOne(id);
        coupon.getSummary();
        return coupon;
    }

    @Override
    public Long countBySummaryWhereMemberIsNull(CouponSummary summary) {
        return repository.countBySummaryWhereMemberIsNull(summary);
    }

    @Override
    public Long countBySummary(CouponSummary summary) {
        return repository.countBySummary(summary);
    }

}
