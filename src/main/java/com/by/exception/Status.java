package com.by.exception;

// 返回简单成功或失败，不需要附带信息
public class Status {
	protected String status;
	
	public Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
