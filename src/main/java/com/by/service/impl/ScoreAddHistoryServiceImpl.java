package com.by.service.impl;

import com.by.json.ScoreAddHistoryJson;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.repository.ScoreAddHistoryRepository;
import com.by.service.ScoreAddHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public ScoreAddHistory save(Member member, int total, String reason) {
        ScoreAddHistory sah = new ScoreAddHistory();
        sah.setMember(member);
        sah.setTotal(total);
        sah.setReason(reason);
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

    @Override
    public Long sumByMember(Member member) {
        return repository.sumByMember(member);
    }

    @Override
    public Page<ScoreAddHistoryJson> findByMember(Member member, Pageable pageable) {
        Page<ScoreAddHistory> histories = repository.findByMember(member, pageable);
        List<ScoreAddHistoryJson> json = histories.getContent().stream().map(i -> new ScoreAddHistoryJson(i)).collect(Collectors.toList());
        return new PageImpl<ScoreAddHistoryJson>(json, pageable, histories.getTotalElements());
    }
}
