package com.by.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.by.model.Authority;
import com.by.model.Menu;
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
        for (Authority a : u.getAuthorites()) {
            a.getMenus().size();
        }
        u.getShop();
        return u;
    }

    @Override
    public Set<Menu> getMenus(User user) {
        Set<Menu> menus = new HashSet<>();
        user.getAuthorites().stream().forEach(i -> {
            menus.addAll(i.getMenus());
        });
        return menus;
    }

    @Override
    public Optional<User> findId(Long id) {
        return repository.findById(id);
    }
}
