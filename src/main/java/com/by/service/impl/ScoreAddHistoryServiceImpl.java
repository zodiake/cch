package com.by.service.impl;

import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.repository.ScoreAddHistoryRepository;
import com.by.service.ScoreAddHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return repository.save(history);
    }

    @Override
    public ScoreAddHistory save(Member member, int total, String summary) {
        ScoreAddHistory sah = new ScoreAddHistory();
        sah.setMember(member);
        sah.setTotal(total);
        sah.setSummary(summary);
        return save(sah);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public ScoreAddHistory update(ScoreAddHistory history, int total) {
        ScoreAddHistory h = repository.findOne(history.getId());
        h.setTotal(total);
        return h;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreAddHistory> findByMember(Member member) {
        Sort sort = new Sort(Sort.Direction.ASC, "createdTime");
        return repository.findByMember(member, sort);
    }
}
