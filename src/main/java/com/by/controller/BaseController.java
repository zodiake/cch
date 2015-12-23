package com.by.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.by.model.Member;
import com.by.model.Menu;
import com.by.model.MenuCategory;
import com.by.model.User;
import com.by.service.MenuCategoryService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public class BaseController {
	@Autowired
	private MenuCategoryService menuCategoryService;

	protected boolean isValidMember(Member m) {
		if (m.getValid().equals(ValidEnum.VALID)) {
			return true;
		}
		return false;
	}

	protected Map<MenuCategory, List<Menu>> menus(User user) {
		return menuCategoryService.getCategoryAndMenu(user);
	}
}
