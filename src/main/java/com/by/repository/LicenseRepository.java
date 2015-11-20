package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.by.model.License;

public interface LicenseRepository extends CrudRepository<License, Long> {
	Optional<License> findByName(String name);
}
