package com.by.service.impl;

import com.by.model.Card;
import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.repository.RuleRepository;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Page<Rule> findByCard(Card card, Pageable pageable) {
        return repository.findByCard(card, pageable);
    }

    @Override
    @Cacheable(value = "ruleList", key = "#card.id")
    @Transactional(readOnly = true)
    public List<Rule> findByCard(Card card) {
        return repository.findByCard(card);
    }

    @Override
    @Cacheable("rule")
    @Transactional(readOnly = true)
    public Rule findByIdAndValid(Long id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    @CachePut({"rule", "ruleList"})
    public Rule save(Rule rule) {
        return repository.save(rule);
    }

    @Override
    @CacheEvict({"rule", "ruleList"})
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
    @Transactional(readOnly = true)
    public List<Rule> findByRuleCategory(RuleCategory category) {
        return repository.findByRuleCategory(category);
    }

}
