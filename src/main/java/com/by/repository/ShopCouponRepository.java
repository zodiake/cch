package com.by.repository;

import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponRepository extends CrudRepository<ShopCoupon, Long> {
    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);

    @Query("select g from ShopCoupon g where g.valid=:valid and g.beginTime<:today and :today<g.endTime ")
    Page<ShopCoupon> findAllByValidAndDateBetween(@Param("valid") ValidEnum valid, @Param("today") Calendar today, Pageable pageable);
}
