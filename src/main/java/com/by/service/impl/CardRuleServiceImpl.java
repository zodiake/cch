package com.by.service.impl;

import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.repository.CardRuleRepository;
import com.by.service.CardRuleService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 15-12-15.
 */
@Service
@Transactional
public class CardRuleServiceImpl implements CardRuleService {
    @Autowired
    private CardRuleRepository repository;

    @Override
    @Cacheable(value = "cardRule", key = "#category.id")
    public List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid) {
        return repository.findByRuleCategoryAndCardAndValid(category, card, ValidEnum.VALID);
    }
}
