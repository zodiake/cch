package com.by.repository;

import java.util.List;

import com.by.typeEnum.ValidEnum;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.CouponSummary;

public interface CouponSummaryRepository extends PagingAndSortingRepository<CouponSummary, Long>{
	List<CouponSummary> findByValid(ValidEnum valid);
}
