package com.by.repository;

import com.by.model.Coupon;
import com.by.model.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
    Coupon findByCode(String code);

    List<Coupon> findByMember(Member member);
}
