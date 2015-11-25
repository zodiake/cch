package com.by.service.impl;

import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponUseHistory;
import com.by.repository.ParkingCouponUseHistoryRepository;
import com.by.service.ParkingCouponUseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-23.
 */
@Service
@Transactional
public class ParkingCouponUseHistoryServiceImpl implements ParkingCouponUseHistoryService {
    @Autowired
    private ParkingCouponUseHistoryRepository repository;

    @Override
    public ParkingCouponUseHistory save(ParkingCouponUseHistory history) {
        return repository.save(history);
    }

    @Override
    public ParkingCouponUseHistory save(Member member, int total, String license, ParkingCoupon coupon) {
        return repository.save(new ParkingCouponUseHistory(coupon, member, total, license));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCouponUseHistory> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime) {
        return repository.findByLicenseAndCreatedTimeBetween(license, startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCouponUseHistory> findByLicenseAndCreatedTimeBetween(String license, String startTime, String endTime) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cStartTime = Calendar.getInstance();
        Calendar cEndTime = Calendar.getInstance();
        cStartTime.setTime(format.parse(startTime));
        cEndTime.setTime(format.parse(endTime));
        return findByLicenseAndCreatedTimeBetween(license, cStartTime, cEndTime);
    }

    @Override
    public List<ParkingCouponUseHistory> findByLicense(String license) {
        return repository.findByLicense(license);
    }
}
