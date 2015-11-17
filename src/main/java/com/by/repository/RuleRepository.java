package com.by.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.by.model.Card;
import com.by.model.Rule;

public interface RuleRepository extends CrudRepository<Rule, Long> {
	public List<Rule> findByCard(Card card);
}
