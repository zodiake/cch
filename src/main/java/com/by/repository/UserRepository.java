package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	User findByName(String name);

	Optional<User> findById(Integer id);
}
