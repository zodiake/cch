package com.by.service.impl;

import com.by.model.ScoreAddHistory;
import com.by.repository.ScoreAddHistoryRepository;
import com.by.service.ScoreAddHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 15-11-24.
 */
@Service
@Transactional
public class ScoreAddHistoryServiceImpl implements ScoreAddHistoryService {
    @Autowired
    private ScoreAddHistoryRepository repository;

    @Override
    public ScoreAddHistory save(ScoreAddHistory history) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
