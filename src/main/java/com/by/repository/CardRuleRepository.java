package com.by.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;

public interface CardRuleRepository extends PagingAndSortingRepository<CardRule, Integer> {
	List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid);

	List<CardRule> findByRuleCategoryAndCard(RuleCategory category, Card card);
	
	Long countByName(String name);
}
