package com.by.repository;

import com.by.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LicenseRepository extends CrudRepository<License, Long> {
    License findByName(String name);
}
