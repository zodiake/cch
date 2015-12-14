package com.by.service.impl;

import com.by.json.ParkingPayJson;
import com.by.model.Member;
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

    public ParkingPay save(ParkingPayJson pay) {
        ParkingPay parkingPay = new ParkingPay();
        parkingPay.setLicense(pay.getLicense());
        parkingPay.setAmount(pay.getAmount());
        parkingPay.setCreatedTime(pay.getCreatedTime());
        parkingPay.setMember(new Member(pay.getMember()));
        return repository.save(parkingPay);
    }

    @Override
    public ParkingPay save(ParkingPay pay) {
        return repository.save(pay);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingPay> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime) {
        return repository.findByLicenseAndCreatedTimeBetween(license, startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingPay> findByLicense(String license) {
        return repository.findByLicense(license);
    }
}
