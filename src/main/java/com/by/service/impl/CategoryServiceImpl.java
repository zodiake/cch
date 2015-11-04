package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Category;
import com.by.repository.CategoryRepository;
import com.by.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;

	public Optional<Category> findOne(Long id) {
		return repository.findById(id);
	}

}
