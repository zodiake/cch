package com.by.repository;

import com.by.model.Card;
import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RuleRepository extends PagingAndSortingRepository<Rule, Long> {
    List<Rule> findByCard(Card card);

    Page<Rule> findByCard(Card card, Pageable pageable);

    Rule findByIdAndValid(Long id, ValidEnum valid);

    Page<Rule> findByRuleCategory(RuleCategory category, Pageable pageable);

    List<Rule> findByRuleCategory(RuleCategory category);

    List<Rule> findByRuleCategoryAndValid(RuleCategory category, ValidEnum valid);
}
