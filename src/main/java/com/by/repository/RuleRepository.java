package com.by.repository;

import com.by.model.Rule;
import com.by.model.RuleCategory;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RuleRepository extends PagingAndSortingRepository<Rule, Long> {
}
