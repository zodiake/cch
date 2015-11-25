package com.by.service;

import com.by.model.License;
import com.by.model.Member;
import com.by.model.ParkingPay;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-25.
 */
public interface ParkingPayService {
    ParkingPay save(ParkingPay pay);

    List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingPay> findByLicense(String license);
}
