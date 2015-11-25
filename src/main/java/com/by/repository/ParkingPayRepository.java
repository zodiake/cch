package com.by.repository;

import com.by.model.Member;
import com.by.model.ParkingPay;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-25.
 */
public interface ParkingPayRepository extends PagingAndSortingRepository<ParkingPay, Long> {
    List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingPay> findByLicense(String license);
}
