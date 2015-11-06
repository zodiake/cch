package com.by.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.JWTSigner;
import com.by.model.User;
import com.by.service.UserService;

@Controller
public class SigninController {
	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginIn(Model uiModel) {
		User u = new User();
		uiModel.addAttribute("user", u);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String signin(@Valid User user,BindingResult result) {
		JWTSigner signer = new JWTSigner("crm");
		Map<String, Object> u = new HashMap<>();
		u.put("user", new User(1l, "tom"));
		return signer.sign(u);
	}
}
