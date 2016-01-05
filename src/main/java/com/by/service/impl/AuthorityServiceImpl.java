package com.by.service.impl;

import com.by.model.Authority;
import com.by.repository.AuthorityRepository;
import com.by.service.AuthorityService;
import com.by.typeEnum.ValidEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 16-1-4.
 */
@Component
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Authority> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Authority findOne(int id) {
		Authority authority = repository.findOne(id);
		authority.getMenus().size();
		return authority;
	}

	@Override
	public Authority save(Authority authority) {
		authority.setValid(ValidEnum.VALID);
		return repository.save(authority);
	}
}
