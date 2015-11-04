package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.by.exception.NotFoundException;
import com.by.model.Category;
import com.by.service.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
	@Autowired
	private CategoryService service;

	@RequestMapping(method = RequestMethod.GET)
	public Category findByName() {
		return service.findOne(6l).orElseThrow(() -> new NotFoundException());
	}
}
