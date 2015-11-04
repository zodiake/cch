package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.User;
import com.by.service.UserService;

@Controller
public class SigninController {
	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String loginIn(Model uiModel) {
		User u=new User();
		uiModel.addAttribute("user",u);
		return "login";
	}
}
