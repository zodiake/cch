package com.by.service.impl;

import com.by.model.ParkingPay;
import com.by.repository.ParkingPayRepository;
import com.by.service.ParkingPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-25.
 */
@Service
@Transactional
public class ParkingPayServiceImpl implements ParkingPayService {
    @Autowired
    private ParkingPayRepository repository;

    @Override
    public ParkingPay save(ParkingPay pay) {
        return repository.save(pay);
    }

    @Override
    public List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime) {
        return repository.findByLicenseAndCreatedTimeBetween(license, startTime, endTime);
    }

    @Override
    public List<ParkingPay> findByLicense(String license) {
        return repository.findByLicense(license);
    }
}
