package com.by.service;

import com.by.model.Member;
import com.by.model.ScoreHistory;

public interface ScoreHistoryService {
	public ScoreHistory save(ScoreHistory history);

	public ScoreHistory save(Member member, int score);
}
