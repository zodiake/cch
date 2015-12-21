package com.by.service;

import java.util.List;

import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-15.
 */
public interface CardRuleService {
    List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid);

    List<CardRule> findByRuleCategoryAndCard(RuleCategory category, Card card);
    
    CardRule save(CardRule rule);
    
    CardRule findOne(int id);
}
