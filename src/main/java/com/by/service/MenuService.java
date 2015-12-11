package com.by.service;

import java.util.List;

import com.by.model.Menu;

/**
 * Created by yagamai on 15-12-11.
 */
public interface MenuService {
    List<Menu> findAll();
    
    Menu save(Menu menu);
}
