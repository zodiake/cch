package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-4.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "coupon already exchange")
public class AlreadyExchangeException extends RuntimeException {
}
