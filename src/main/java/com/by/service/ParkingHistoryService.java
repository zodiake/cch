package com.by.service;

import com.by.form.ParkingForm;
import com.by.model.Member;
import com.by.model.ParkingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingHistoryService {
    ParkingHistory save(ParkingHistory history);

    Page<ParkingHistory> findAll(Pageable pageable);

    Page<ParkingHistory> findByMember(Member member, Pageable pageable);

    List<ParkingHistory> findByLicense(String license);

    void bindLicenseWithMember(ParkingForm form);
}
