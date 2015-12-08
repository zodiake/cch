package com.by.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yagamai on 15-12-8.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "coupon not found")
public class CouponNotFoundException extends RuntimeException{
}
