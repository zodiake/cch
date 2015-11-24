package com.by.service;

import com.by.model.ParkingHistoryWithNoMemberHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingWithNoMemberHistoryService {
    ParkingHistoryWithNoMemberHistory save(ParkingHistoryWithNoMemberHistory history);

    Page<ParkingHistoryWithNoMemberHistory> findAll(Pageable pageable);

    ParkingHistoryWithNoMemberHistory findOne(Long id);
}
