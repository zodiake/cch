package com.by.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.by.exception.NotFoundException;
import com.by.model.RuleCategory;
import com.by.model.User;
import com.by.service.CategoryService;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {
	@Autowired
	private CategoryService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public RuleCategory findByName(HttpServletRequest request) {
		User u = (User) request.getAttribute("user");
		return service.findOne(6l).orElseThrow(() -> new NotFoundException());
	}
}
