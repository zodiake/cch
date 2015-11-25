package com.by.service.impl;

import com.by.model.ParkingHistoryWithNoMemberHistory;
import com.by.repository.ParkingWithNoMemberHistoryRepository;
import com.by.service.ParkingWithNoMemberHistoryService;
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
public class ParkingWithNoMemberHistoryServiceImpl implements ParkingWithNoMemberHistoryService {
    @Autowired
    private ParkingWithNoMemberHistoryRepository repository;

    @Override
    public ParkingHistoryWithNoMemberHistory save(ParkingHistoryWithNoMemberHistory history) {
        return repository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingHistoryWithNoMemberHistory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingHistoryWithNoMemberHistory findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingHistoryWithNoMemberHistory> findByLicense(String license) {
        return repository.findByLicense(license);
    }
}
