package com.by.service;

import com.by.model.CouponSummary;

public interface CouponSummaryService {
    CouponSummary save(CouponSummary summary);

    CouponSummary updateTotal(CouponSummary summary, int total);
}
