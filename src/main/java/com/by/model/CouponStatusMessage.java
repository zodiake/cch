package com.by.model;

public class CouponStatusMessage {
	private String status;
	private boolean canValid;
	private boolean canUpdate;
	private String state;

	public CouponStatusMessage() {
	}

	public CouponStatusMessage(String status, boolean canValid, boolean canUpdate) {
		super();
		this.status = status;
		this.canValid = canValid;
		this.canUpdate = canUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCanValid() {
		return canValid;
	}

	public void setCanValid(boolean canValid) {
		this.canValid = canValid;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}