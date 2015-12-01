package com.by.service.impl;

import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScore;
import com.by.exception.NotValidException;
import com.by.model.*;
import com.by.repository.MemberRepository;
import com.by.service.*;
import com.by.typeEnum.ValidEnum;
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
    @Autowired
    private CardService cardService;
    @Autowired
    private RuleService ruleService;

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
        Card card = cardService.findByIdAndValid(member.getCard().getId(), ValidEnum.VALID);
        if (card != null)
            throw new NotValidException();
        List<Rule> rules = ruleService.findByRuleCategoryAndCardAndValid(new RuleCategory(1L), card, ValidEnum.VALID);
        int score = member.getCard().getInitScore();
        if (rules.size() > 0) {
            score += ruleService.getMaxScore(rules);
        }
        member.setScore(score);
        scoreAddHistoryService.save(member, score, "sign in score");
        scoreHistoryService.save(member, score);
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
    public Member updateScore(Member member, int total, String reason) {
        Member source = repository.findOne(member.getId());
        source.setScore(source.getScore() + total);
        scoreAddHistoryService.save(member, total, reason);
        scoreHistoryService.save(member, total);
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

    /*
       find which scoreAddHistory should deleted from history
       @param allList member all scoreAddedHistory list
     */
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
