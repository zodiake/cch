package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.User;
import com.by.repository.UserRepository;
import com.by.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	public User findByName(String name) {
		User u = repository.findByName(name);
		u.getAuthorites().size();
		return u;
	}

	@Override
	public Optional<User> findId(Long id) {
		return repository.findById(id);
	}
}
