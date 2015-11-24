package com.by.repository;

import com.by.model.Member;
import com.by.model.ParkingCouponUseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * Created by yagamai on 15-11-23.
 */
public interface ParkingCouponUseHistoryRepository extends PagingAndSortingRepository<ParkingCouponUseHistory, Long> {
    Page<ParkingCouponUseHistory> findByMember(Member member, Pageable pageable);

    ParkingCouponUseHistory findByMemberAndCreatedTimeBetween(Member member, @Temporal(TemporalType.TIMESTAMP) Calendar startTime, @Temporal(TemporalType.TIMESTAMP) Calendar endTime);
}
