package com.by.message;

public class SuccessMessage extends StatusMessage{
	public SuccessMessage(String msg) {
		this.setStatus("success");
		this.setMessage(msg);
	}
}
