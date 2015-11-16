package com.by.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.User;

@Controller
public class AdminLoginController {
	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	public String login(Model uiModel) {
		uiModel.addAttribute("user",new User());
		return "admin/login";
	}
	
	
}
