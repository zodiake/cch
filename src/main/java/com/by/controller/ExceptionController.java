package com.by.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.NotFoundException;
import com.by.exception.Status;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public Status notFound() {
		return new Status("fail", "not found");
	}

}
