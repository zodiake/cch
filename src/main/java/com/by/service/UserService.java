package com.by.service;

import com.by.form.UserQueryForm;
import com.by.model.Menu;
import com.by.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findId(Long id);

    User findByName(String name);

    Set<Menu> getMenus(User user);

    Page<User> findAll(UserQueryForm form, Pageable pageable);

    List<User> findFirstPage(int size);
}
