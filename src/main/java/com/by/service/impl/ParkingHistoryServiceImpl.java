package com.by.service.impl;

import com.by.form.ParkingForm;
import com.by.model.*;
import com.by.repository.ParkingHistoryRepository;
import com.by.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
@Service
@Transactional
public class ParkingHistoryServiceImpl implements ParkingHistoryService {
    @Autowired
    private ParkingHistoryRepository repository;
    @Autowired
    private ParkingWithNoMemberHistoryService noMemberHistoryService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private ParkingPayService parkingPayService;
    @Autowired
    private LicenseService licenseService;

    @Override
    public ParkingHistory save(ParkingHistory history) {
        return repository.save(history);
    }

    @Override
    public void bindLicenseWithMember(ParkingForm form) {
        ParkingHistory history = new ParkingHistory(form);
        License license = licenseService.findByName(history.getLicense());
        // 该车牌是否存在会员记录中
        if (license != null) {
            List<ParkingCouponUseHistory> useHistories = useHistoryService.findByLicenseAndCreatedTimeBetween(license.getName(), history.getStartTime(), history.getEndTIme());
            Member m = null;
            //是否有付费记录
            if (useHistories.size() > 0) {
                ParkingCouponUseHistory ph = useHistories.get(0);
                history.setMember(ph.getMember());
                history.setLicense(license.getName());
                save(history);
            } else {
                //是否有使用卡券纪录
                List<ParkingPay> pays = parkingPayService.findByLicenseAndCreatedTimeBetween(license.getName(), history.getStartTime(), history.getEndTIme());
                if (pays.size() > 0) {
                    ParkingPay parkingPay = pays.get(0);
                    history.setMember(parkingPay.getMember());
                    history.setLicense(license.getName());
                    save(history);
                } else {
                    noMemberHistoryService.save(new ParkingHistoryWithNoMemberHistory(history));
                }
            }
        } else {
            noMemberHistoryService.save(new ParkingHistoryWithNoMemberHistory(history));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingHistory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingHistory> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable);
    }

    @Override
    public List<ParkingHistory> findByLicense(String license) {
        return repository.findByLicense(license);
    }
}
