package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.by.model.ParkingCoupon;

public interface ParkingCouponRepository extends CrudRepository<ParkingCoupon, Long> {
	Optional<ParkingCoupon> findById(Long id);
}
