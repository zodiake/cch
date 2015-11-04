package com.by.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class Fail extends Status {
	private List<ObjectError> errors;
	private String message;

	public Fail(String message) {
		this.status = "fail";
		this.message = message;
		this.errors = null;
	}

	public Fail(List<ObjectError> errors) {
		this.status = "fail";
		errors.stream().map(i->i.getDefaultMessage());
		this.errors = errors;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
