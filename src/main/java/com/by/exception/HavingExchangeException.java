package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-11-30.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "already exchanged")
public class HavingExchangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
