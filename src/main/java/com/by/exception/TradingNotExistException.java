package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-1.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "trading not found")
public class TradingNotExistException extends RuntimeException {
}
