package com.by.repository;

import java.util.List;
import java.util.Optional;

import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.by.model.ParkingCoupon;

public interface ParkingCouponRepository extends CrudRepository<ParkingCoupon, Long> {
	Optional<ParkingCoupon> findById(Long id);

	Page<ParkingCoupon> findByValid(ValidEnum valid,Pageable pageable);

	Page<ParkingCoupon> findAll(Pageable pageable);
}
