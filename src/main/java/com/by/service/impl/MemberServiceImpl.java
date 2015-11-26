package com.by.service.impl;

import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScore;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.repository.MemberRepository;
import com.by.service.MemberService;
import com.by.service.ScoreAddHistoryService;
import com.by.service.ScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private ScoreAddHistoryService scoreAddHistoryService;
    @Autowired
    private ScoreHistoryService scoreHistoryService;

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> countByName(String name) {
        return repository.countByName(name);
    }

    @Override
    public Optional<Member> update(Member member) {
        return repository.findById(member.getId()).map(i -> {
            i.setPassword(member.getPassword());
            return i;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByNameAndPassword(Member member) {
        return repository.findByNameAndPassword(member.getName(), member.getPassword());
    }

    @Override
    public Member getScore(Member member, int total) {
        Member source = repository.findOne(member.getId());
        source.setScore(source.getScore() + total);
        scoreAddHistoryService.save(member, total);
        return source;
    }

    @Override
    public Member useScore(Member member, int total) {
        Member source = repository.findOne(member.getId());
        if (source == null)
            throw new MemberNotFoundException();
        if (source.getScore() < total)
            throw new NotEnoughScore();
        List<ScoreAddHistory> historyList = scoreAddHistoryService.findByMember(member);
        List<ScoreAddHistory> results = extractScoreHistory(historyList, total);
        int sum = results.stream().map(ScoreAddHistory::getTotal).reduce((i, s) -> i + s).get();
        if (sum > total) {
            ScoreAddHistory last = results.get(results.size() - 1);
            last.setTotal(sum - total);
            results.remove(last);
            for (ScoreAddHistory h : results) {
                scoreAddHistoryService.delete(h.getId());
            }
        } else if (sum == total) {
            for (ScoreAddHistory h : results) {
                scoreAddHistoryService.delete(h.getId());
            }
        }
        source.setScore(source.getScore() - total);
        scoreHistoryService.save(member, -total);
        return source;
    }

    @Override
    public List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total) {
        List<ScoreAddHistory> results = new ArrayList<>();
        int init = 0;
        for (ScoreAddHistory s : allList) {
            if (init < total) {
                init += s.getTotal();
                results.add(s);
            } else {
                break;
            }
        }
        return results;
    }
}
