package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.AuthorityJson;
import com.by.model.Authority;
import com.by.repository.AuthorityRepository;
import com.by.service.AuthorityService;
import com.by.typeEnum.ValidEnum;

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
    @Transactional(readOnly = true)
    @Cacheable("auth")
    public List<Authority> findAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<AuthorityJson> toJson(Page<Authority> lists, Pageable pageable) {
        List<Authority> authorities = lists.getContent();
        List<AuthorityJson> json = authorities.stream().map(i -> new AuthorityJson(i)).collect(Collectors.toList());
        return new PageImpl<>(json, pageable, lists.getTotalElements());
    }

    @Override
    @Cacheable(value = "auth")
    public Authority save(Authority authority) {
        authority.setValid(ValidEnum.VALID);
        return repository.save(authority);
    }

	@Override
	public Authority update(Authority authority) {
		Authority source=repository.findOne(authority.getId());
		source.setUpdatedBy(authority.getUpdatedBy());
		source.setName(authority.getName());
		source.setMenus(authority.getMenus());
		return source;
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public Authority findByName(String name) {
		return repository.findByName(name);
	}
}
