package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-26.
 */
public interface RuleService {
	Rule findByIdAndValid(Long id, ValidEnum valid);

	Rule save(Rule rule);

	Rule update(Rule rule);

	Page<Rule> findByRuleCategory(RuleCategory category, Pageable pageable);

	List<Rule> findByRuleCategoryAndValid(RuleCategory category, ValidEnum valid);

	int getMaxScore(List<? extends Rule> rules);

	double getMaxRate(List<? extends Rule> rules);
}
