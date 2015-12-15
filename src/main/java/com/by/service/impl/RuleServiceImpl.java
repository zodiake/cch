package com.by.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.repository.RuleRepository;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-26.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    @Autowired
    private RuleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Rule findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @CachePut({"ruleCard"})
    public Rule save(Rule rule) {
        return repository.save(rule);
    }

    @Override
    @CacheEvict({"ruleCard"})
    public Rule update(Rule rule) {
        Rule source = repository.findOne(rule.getId());
        source.setBeginTime(rule.getBeginTime());
        return source;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rule> findByRuleCategory(RuleCategory category, Pageable pageable) {
        return repository.findByRuleCategory(category, pageable);
    }

    @Override
    @Cacheable({"ruleCategory"})
    public List<Rule> findByRuleCategoryAndValid(RuleCategory category, ValidEnum valid) {
        return repository.findByRuleCategoryAndValid(category, valid);
    }

    @Override
    public int getMaxScore(List<? extends Rule> rules) {
        Calendar today = Calendar.getInstance();
        List<Integer> scoreList = rules.stream()
                .filter(i -> {
                    if (i.getBeginTime() != null && i.getEndTime() != null)
                        return i.getBeginTime().before(today) && i.getEndTime().after(today);
                    return true;
                })
                .map(Rule::getScore)
                .collect(Collectors.toList());
        return Collections.max(scoreList);
    }

    @Override
    public double getMaxRate(List<? extends Rule> rules) {
        Calendar today = Calendar.getInstance();
        List<Double> scoreList = rules.stream()
                .filter(i -> {
                    if (i.getBeginTime() != null && i.getEndTime() != null)
                        return i.getBeginTime().before(today) && i.getEndTime().after(today);
                    return true;
                })
                .map(Rule::getRate)
                .collect(Collectors.toList());
        return Collections.max(scoreList);
    }
}
