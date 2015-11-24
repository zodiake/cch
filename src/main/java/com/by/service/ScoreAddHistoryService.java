package com.by.service;

import com.by.model.ScoreAddHistory;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ScoreAddHistoryService {
    ScoreAddHistory save(ScoreAddHistory history);

    void delete(Long id);
}
