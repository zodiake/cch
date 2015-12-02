package com.by.service.impl;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.repository.CouponSummaryRepository;
import com.by.service.CouponService;
import com.by.service.CouponSummaryService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CouponSummaryServiceImpl implements CouponSummaryService {
    @Autowired
    private CouponSummaryRepository repository;
    @Autowired
    private CouponService couponService;

    @Override
    @CachePut("couponSummary")
    public CouponSummary save(CouponSummary summary) {
        summary.getTotal();
        List<Coupon> coupons = new ArrayList<>();
        for (int i = 1; i <= summary.getTotal(); i++) {
            Coupon c = new Coupon();
            c.setSummary(summary);
            coupons.add(c);
        }
        summary.setCoupons(coupons);
        return repository.save(summary);
    }

    @Override
    public CouponSummary updateTotal(CouponSummary summary, int total) {
        for (int i = 1; i <= total; i++) {
            Coupon c = new Coupon();
            c.setSummary(summary);
            couponService.save(c);
        }
        return repository.findOne(summary.getId());
    }

    @Override
    @Cacheable("couponSummary")
    public List<CouponSummary> findByValid(ValidEnum valid) {
        return repository.findByValid(valid);
    }

    @Override
    public List<CouponSummary> findByValidAndTimeInToday(ValidEnum valid, Calendar calendar) {
        List<CouponSummary> lists = findByValid(valid);
        return lists.stream().filter(i -> {
            if (i.getBeginTime() == null && i.getEndTime() == null)
                return true;
            if (i.getBeginTime() != null && i.getEndTime() != null) {
                return i.getBeginTime().before(calendar) && i.getEndTime().after(calendar);
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public CouponSummary findOne(Long id) {
        return repository.findOne(id);
    }
}
