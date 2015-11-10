package com.by.exception;

// 成功，附带成功信息
public class Success<T> extends Status {
	private T obj;

	public Success(T obj) {
		super("success");
		this.obj = obj;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
