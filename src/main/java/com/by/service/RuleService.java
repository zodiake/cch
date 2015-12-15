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
	Rule save(Rule rule);

	Rule update(Rule rule);

	int getMaxScore(List<? extends Rule> rules);

	double getMaxRate(List<? extends Rule> rules);

	boolean withValidDate(Rule rule);
}
