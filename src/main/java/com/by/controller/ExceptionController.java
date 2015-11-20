package com.by.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.NotEnoughCouponException;
import com.by.exception.NotFoundException;

@ControllerAdvice
public class ExceptionController {
	Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ResponseEntity<Fail> notFound() {
		return new ResponseEntity<Fail>(new Fail("not found"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotEnoughCouponException.class)
	@ResponseBody
	public ResponseEntity<Fail> notEnouthCouponException() {
		return new ResponseEntity<Fail>(new Fail("not enough coupon"), HttpStatus.BAD_REQUEST);
	}

	/*
	 * @ExceptionHandler(value = { Exception.class, RuntimeException.class })
	 * 
	 * @ResponseBody public ResponseEntity<Fail>
	 * defaultErrorHandler(HttpServletRequest request, Exception e) {
	 * log.error(null, request.getParameterMap()); return new
	 * ResponseEntity<Fail>(new Fail("system fail"), HttpStatus.NOT_FOUND); }
	 */
}
