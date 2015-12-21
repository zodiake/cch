package com.by.repository;

import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponMemberRepository extends PagingAndSortingRepository<ShopCouponMember, Long> {
    Long countByCoupon(ShopCoupon coupon);

    List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member);

    Page<ShopCouponMember> findByMember(Member member, Pageable pageable);

    ShopCouponMember findByCode(String code);
}
