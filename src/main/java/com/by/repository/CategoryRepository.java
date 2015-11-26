package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.RuleCategory;

public interface CategoryRepository extends PagingAndSortingRepository<RuleCategory, Long> {
	Optional<RuleCategory> findById(Long id);
}
