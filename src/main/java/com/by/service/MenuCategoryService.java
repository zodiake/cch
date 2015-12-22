package com.by.service;

import com.by.model.Menu;
import com.by.model.MenuCategory;
import com.by.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by yagamai on 15-12-22.
 */
public interface MenuCategoryService {
    Map<MenuCategory, List<Menu>> getCategoryAndMenu(User user);
}
