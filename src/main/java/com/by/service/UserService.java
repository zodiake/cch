package com.by.service;

import java.util.Optional;

import com.by.model.User;

public interface UserService {
	Optional<User> findId(Long id);
}
