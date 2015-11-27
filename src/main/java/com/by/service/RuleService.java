package com.by.service;

import com.by.model.Card;
import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yagamai on 15-11-26.
 */
public interface RuleService {
    Page<Rule> findByCard(Card card, Pageable pageable);

    List<Rule> findByCard(Card card);

    Rule findByIdAndValid(Long id, ValidEnum valid);

    Rule save(Rule rule);

    Rule update(Rule rule);

    Page<Rule> findByRuleCategory(RuleCategory category, Pageable pageable);

    List<Rule> findByRuleCategory(RuleCategory category);

    List<Rule> findByRuleCategoryAndValid(RuleCategory category, ValidEnum valid);

    int getMaxScore(List<Rule> rules);
}
