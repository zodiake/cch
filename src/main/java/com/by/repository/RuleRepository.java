package com.by.repository;

import com.by.model.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RuleRepository extends PagingAndSortingRepository<Rule, Long> {
}
