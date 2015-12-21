package com.by.service;

import java.util.List;

import com.by.model.Card;
import com.by.model.Rule;

/**
 * Created by yagamai on 15-11-26.
 */
public interface RuleService {
    Rule save(Rule rule);

    Rule update(Rule rule);

    int getMaxScore(List<? extends Rule> rules);

    double getMaxRate(List<? extends Rule> rules);

    boolean withinValidDate(Rule rule);

}
