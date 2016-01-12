package com.by.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Member;
import com.by.model.ParkingCouponUseHistory;

/**
 * Created by yagamai on 15-11-23.
 */
public interface ParkingCouponUseHistoryService {
    ParkingCouponUseHistory save(ParkingCouponUseHistory history);

    ParkingCouponUseHistory save(Member member, int total, String license);

    Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable);

    List<ParkingCouponUseHistory> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingCouponUseHistory> findByLicenseAndCreatedTimeBetween(String license, String startTime, String endTime) throws ParseException;

    List<ParkingCouponUseHistory> findByLicense(String license);
}
