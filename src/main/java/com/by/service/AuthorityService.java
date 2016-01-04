package com.by.service;

import com.by.model.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yagamai on 15-11-23.
 */
public interface AuthorityService {
    Page<Authority> findAll(Pageable pageable);

    Authority findOne(int id);

    Authority save(Authority authority);
}
