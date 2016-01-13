package com.by.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;

public interface ParkingCouponRepository extends CrudRepository<ParkingCoupon, Integer> {
	Optional<ParkingCoupon> findById(int id);

	Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable);

	Page<ParkingCoupon> findAll(Pageable pageable);

	List<ParkingCoupon> findAll();

	Long countByName(String name);

	ParkingCoupon findByName(String name);
}
