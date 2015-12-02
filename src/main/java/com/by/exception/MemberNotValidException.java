package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-2.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "member not valid")
public class MemberNotValidException extends RuntimeException {
}
