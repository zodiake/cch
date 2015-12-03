package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-3.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "resource not found")
public class PasswordNotMatchException extends RuntimeException {
}
