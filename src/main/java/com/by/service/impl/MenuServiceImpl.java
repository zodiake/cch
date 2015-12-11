package com.by.service.impl;

import com.by.model.Menu;
import com.by.repository.MenuRepository;
import com.by.service.MenuService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 15-12-11.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuRepository repository;

	@Override
	@Cacheable("menu")
	@Transactional(readOnly = true)
	public List<Menu> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	@Override
	@CachePut("menu")
	public Menu save(Menu menu) {
		return repository.save(menu);
	}
}
