package com.by.service;

import java.util.Optional;
import java.util.Set;

import com.by.model.Menu;
import com.by.model.User;

public interface UserService {
	Optional<User> findId(Long id);
	
	User findByName(String name);
	
	Set<Menu> getMenus(User user);
}
