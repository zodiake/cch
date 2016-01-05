package com.by.repository;

import com.by.model.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yagamai on 15-11-23.
 */
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
    Page<Authority> findAll(Pageable pageable);
}
