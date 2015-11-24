package com.by.service;

import com.by.model.Member;
import com.by.model.ParkingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingHistoryService {
    ParkingHistory save(ParkingHistory history);

    Page<ParkingHistory> findAll(Pageable pageable);

    Page<ParkingHistory> findByMember(Member member, Pageable pageable);
}
