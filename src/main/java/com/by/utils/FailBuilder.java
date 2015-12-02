package com.by.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.by.exception.Fail;
import com.by.model.ReturnErrors;

public class FailBuilder {
	public static Fail buildFail(BindingResult result) {
		List<ReturnErrors> errors = result.getFieldErrors().stream()
				.map(i -> new ReturnErrors(i.getField(), i.getDefaultMessage())).collect(Collectors.toList());
		return new Fail(errors);
	}
}
