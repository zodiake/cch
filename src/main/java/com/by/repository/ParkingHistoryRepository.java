package com.by.repository;

import com.by.model.Member;
import com.by.model.ParkingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ParkingHistoryRepository extends PagingAndSortingRepository<ParkingHistory, Long> {
    Page<ParkingHistory> findByMember(Member member, Pageable pageable);
}
