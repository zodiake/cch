package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByName(String name);

	Optional<User> findById(Long id);
}
