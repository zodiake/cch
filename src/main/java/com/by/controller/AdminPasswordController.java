package com.by.controller;

import com.by.exception.Success;
import com.by.json.PasswordJson;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yagamai on 16-1-7.
 */
@Controller
@RequestMapping("/admin/password")
public class AdminPasswordController extends BaseController {
	private final Menu subMenu = new Menu(16);
	private final String EDIT = "admin/password/edit";
	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Success<String> update(@RequestBody PasswordJson json) {
		User user = userContext.getCurrentUser();
		user.setPassword(json.getPassword());
		System.out.println(json.getPassword());
		service.updatePassword(user);
		return new Success<>("success");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String edit(Model uiModel) {
		addMenu(uiModel);
		return EDIT;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
