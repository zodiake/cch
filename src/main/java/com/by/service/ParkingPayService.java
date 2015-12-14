package com.by.service;

import java.util.Calendar;
import java.util.List;

import com.by.json.ParkingPayJson;
import com.by.model.ParkingPay;

/**
 * Created by yagamai on 15-11-25.
 */
public interface ParkingPayService {
    ParkingPay save(ParkingPay pay);

    List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingPay> findByLicense(String license);

    ParkingPay save(ParkingPayJson pay);
}
