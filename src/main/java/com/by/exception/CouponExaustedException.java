package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.IM_USED, reason = "resource not found")
public class CouponExaustedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
