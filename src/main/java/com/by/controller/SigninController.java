package com.by.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.by.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	@ResponseBody
	public String signin() {
		JWTSigner signer = new JWTSigner("crm");
		Map<String, Object> u = new HashMap<>();
		u.put("member", new Member(2L, "13811738422"));
		return signer.sign(u);
	}
}
