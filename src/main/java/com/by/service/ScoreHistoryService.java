package com.by.service;

import com.by.json.ScoreHistoryJson;
import com.by.model.Member;
import com.by.model.ScoreHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScoreHistoryService {
    ScoreHistory save(ScoreHistory history);

    ScoreHistory save(Member member, int score, String reason);

    Page<ScoreHistory> findByMember(Member member, Pageable pageable);

    Page<ScoreHistoryJson> findByMemberJson(Member member, Pageable pageable);

    List<ScoreHistory> findByMember(Member member);
}
