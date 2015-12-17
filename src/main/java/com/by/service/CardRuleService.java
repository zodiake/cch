package com.by.service;

import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;

import java.util.List;

/**
 * Created by yagamai on 15-12-15.
 */
public interface CardRuleService {
    List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid);
}
