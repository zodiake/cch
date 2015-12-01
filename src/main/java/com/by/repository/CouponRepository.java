package com.by.repository;

import com.by.model.Coupon;
import com.by.model.CouponSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
    Page<Coupon> findBySummary(CouponSummary summary, Pageable pageable);

    @Query("select count(*) from Coupon c where c.summary=:summary and c.member is null")
    Long countBySummaryWhereMemberIsNull(@Param("summary") CouponSummary summary);

    Coupon findByCode(String code);

    Long countBySummary(CouponSummary summary);
}
