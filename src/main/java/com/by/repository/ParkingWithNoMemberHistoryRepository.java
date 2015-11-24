package com.by.repository;

import com.by.model.ParkingHistoryWithNoMemberHistory;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingWithNoMemberHistoryRepository extends PagingAndSortingRepository<ParkingHistoryWithNoMemberHistory, Long> {
}
