package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
	Optional<Category> findById(Long id);
}
