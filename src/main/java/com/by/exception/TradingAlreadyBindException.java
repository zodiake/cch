package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-1.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "trading already bind")
public class TradingAlreadyBindException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
