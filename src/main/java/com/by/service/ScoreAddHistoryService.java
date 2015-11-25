package com.by.service;

import com.by.model.Member;
import com.by.model.ScoreAddHistory;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ScoreAddHistoryService {
    ScoreAddHistory save(ScoreAddHistory history);

    void delete(Long id);

    ScoreAddHistory update(ScoreAddHistory history, int total);

    List<ScoreAddHistory> findByMember(Member member);
}
