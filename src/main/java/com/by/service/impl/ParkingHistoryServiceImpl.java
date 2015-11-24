package com.by.service.impl;

import com.by.model.Member;
import com.by.model.ParkingHistory;
import com.by.repository.ParkingHistoryRepository;
import com.by.service.ParkingHistoryService;
import com.by.service.ParkingWithNoMemberHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public ParkingHistory save(ParkingHistory history) {
        return repository.save(history);
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
}
