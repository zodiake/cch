package com.by.repository;

import com.by.model.GiftCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */
public interface GiftCouponRepository extends PagingAndSortingRepository<GiftCoupon, Integer> {
	GiftCoupon findByIdAndValid(int id, ValidEnum valid);

	Page<GiftCoupon> findByValid(ValidEnum valid, Pageable pageable);

	@Query("select g from GiftCoupon g where g.valid=:valid and g.beginTime<:today and :today<g.endTime")
	Page<GiftCoupon> findAllByValidAndDateBetween(@Param("valid") ValidEnum VALID, @Param("today") Calendar calendar,
			Pageable pageable);

	GiftCoupon findByName(String name);

	Long countByName(String name);
}
