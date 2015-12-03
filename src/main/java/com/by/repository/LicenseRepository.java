package com.by.repository;

import org.springframework.data.repository.CrudRepository;

import com.by.model.License;

public interface LicenseRepository extends CrudRepository<License, Long> {
    License findByName(String name);
}
