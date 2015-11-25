package com.by.repository;

import com.by.model.Member;
import com.by.model.ParkingCouponUseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-23.
 */
public interface ParkingCouponUseHistoryRepository extends PagingAndSortingRepository<ParkingCouponUseHistory, Long> {
    Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable);

    List<ParkingCouponUseHistory> findByLicenseAndCreatedTimeBetween(String license, Calendar startTime, Calendar endTime);

    List<ParkingCouponUseHistory> findByLicense(String license);
}
