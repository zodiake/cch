package com.by.service.impl;

import com.by.model.Menu;
import com.by.model.User;
import com.by.repository.UserRepository;
import com.by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public User findByName(String name) {
        User user = repository.findByName(name);
        user.getUserAuthorities().size();
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Menu> getMenus(User user) {
        Set<Menu> menus = new HashSet<>();
        if (user.getShop() != null) {
            User u = repository.findOne(user.getId());
            menus.addAll(u.getShop().getMenus());
        } else {
            user.getUserAuthorities().stream().forEach(i -> {
                menus.addAll(i.getMenus());
            });
        }
        return menus;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findId(Long id) {
        return repository.findById(id);
    }
}
