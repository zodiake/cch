package com.by.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.form.UserQueryForm;
import com.by.model.Menu;
import com.by.model.User;

public interface UserService {
    Optional<User> findId(int id);

    User findByName(String name);

    Set<Menu> getMenus(User user);

    Page<User> findAll(UserQueryForm form, Pageable pageable);

    List<User> findFirstPage(int size);
}
