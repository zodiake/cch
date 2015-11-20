package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "not enough coupon")
public class NotEnoughScore extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
