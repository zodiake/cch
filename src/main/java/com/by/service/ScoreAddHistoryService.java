package com.by.service;

import com.by.json.ScoreAddHistoryJson;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 15-11-24.
 */
public interface ScoreAddHistoryService {
    ScoreAddHistory save(ScoreAddHistory history);

    ScoreAddHistory save(Member member, int total, String summary);

    void delete(Long id);

    ScoreAddHistory update(ScoreAddHistory history, int total);

    List<ScoreAddHistory> findByMember(Member member);

    Long sumByMember(Member member);

    Page<ScoreAddHistoryJson> findByMember(Member member, Pageable pageable);
}
