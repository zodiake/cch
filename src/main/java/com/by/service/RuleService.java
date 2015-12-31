package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.model.Rule;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    <T extends Rule> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb);
}
