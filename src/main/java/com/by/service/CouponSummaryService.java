package com.by.service;

import com.by.model.CouponSummary;
import com.by.typeEnum.ValidEnum;

import java.util.Calendar;
import java.util.List;

public interface CouponSummaryService {
    CouponSummary save(CouponSummary summary);

    CouponSummary updateTotal(CouponSummary summary, int total);

    List<CouponSummary> findByValid(ValidEnum valid);

    List<CouponSummary> findByValidAndTimeInToday(ValidEnum valid,Calendar calendar);

    CouponSummary findOne(Long id);
}
