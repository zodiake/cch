package com.by.service.impl;

import com.by.model.Authority;
import com.by.repository.AuthorityRepository;
import com.by.service.AuthorityService;
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
    public Page<Authority> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
