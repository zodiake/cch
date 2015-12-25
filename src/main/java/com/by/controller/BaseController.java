package com.by.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.by.model.Menu;
import com.by.model.MenuCategory;
import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.MenuCategoryService;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public abstract class BaseController implements UtilContoller {
	@Autowired
	protected MenuCategoryService menuCategoryService;
	@Autowired
	protected UserContext userContext;
	protected final String SUCCESS = "保存成功";
	private final int maxSize = 7;

	protected Map<MenuCategory, List<Menu>> menus(User user) {
		return menuCategoryService.getCategoryAndMenu(user);
	}

	protected void addMenu(Model uiModel) {
		uiModel.addAttribute("menus", menus(userContext.getCurrentUser()));
		uiModel.addAttribute("subMenu", getSubMenu());
	}

	protected int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages == 0 ? 1 : totalPages;
		else
			return maxSize;
	}

	public abstract Menu getSubMenu();
}
