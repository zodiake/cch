package com.by.service;

import java.util.Optional;

import com.by.model.RuleCategory;

public interface CategoryService {
	Optional<RuleCategory> findOne(Long id);
}
