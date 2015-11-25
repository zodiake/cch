package com.by.repository;

import com.by.model.ParkingHistoryWithNoMemberHistory;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingWithNoMemberHistoryRepository extends PagingAndSortingRepository<ParkingHistoryWithNoMemberHistory, Long> {
    List<ParkingHistoryWithNoMemberHistory> findByLicense(String license);
}
