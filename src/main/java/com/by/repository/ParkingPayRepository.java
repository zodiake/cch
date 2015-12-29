package com.by.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.ParkingPay;

/**
 * Created by yagamai on 15-11-25.
 */
public interface ParkingPayRepository extends PagingAndSortingRepository<ParkingPay, Long> {
    List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingPay> findByLicense(String license);
}
