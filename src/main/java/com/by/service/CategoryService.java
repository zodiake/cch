package com.by.service;

import java.util.Optional;

import com.by.model.Category;

public interface CategoryService {
	Optional<Category> findOne(Long id);
}
