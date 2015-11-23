package com.by.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Member;
import com.by.model.ScoreHistory;
import com.by.repository.ScoreHistoryRepository;
import com.by.service.ScoreHistoryService;

@Service
@Transactional
public class ScoreHistoryServiceImpl implements ScoreHistoryService {
	@Autowired
	private ScoreHistoryRepository repository;

	@Override
	public ScoreHistory save(ScoreHistory history) {
		return repository.save(history);
	}

	@Override
	public ScoreHistory save(Member member, int score) {
		ScoreHistory history = new ScoreHistory();
		history.setMember(member);
		history.setDeposit(score);
		return repository.save(history);
	}

	@Override
	public Page<ScoreHistory> findByMember(Member member, Pageable pageable) {
		return repository.findByMember(member, pageable);
	}

}
