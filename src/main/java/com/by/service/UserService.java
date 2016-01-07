package com.by.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.form.UserQueryForm;
import com.by.json.UserJson;
import com.by.model.Menu;
import com.by.model.User;

public interface UserService {
	Optional<User> findId(int id);

	User findOne(int id);

	User findByName(String name);

	Set<Menu> getMenus(User user);

	Page<User> findAll(UserQueryForm form, Pageable pageable);

	User save(User user);

	Long countByName(String name);

	User validate(int id);

	Page<UserJson> toJson(Page<User> user, Pageable pageable);

	User updatePassword(User user);
}
