package com.by.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Member;
import com.by.model.ScoreHistory;

public interface ScoreHistoryService {
	public ScoreHistory save(ScoreHistory history);

	public ScoreHistory save(Member member, int score);

	public Page<ScoreHistory> findByMember(Member member,Pageable pageable);
}
