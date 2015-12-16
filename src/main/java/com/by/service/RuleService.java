package com.by.service;

import com.by.model.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
