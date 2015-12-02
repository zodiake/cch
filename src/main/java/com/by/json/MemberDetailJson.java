package com.by.json;

import org.hibernate.validator.constraints.Length;

import com.by.model.MemberDetail;

public class MemberDetailJson {
	@Length(max = 10)
	private String realName;
	@Length(max = 225)
	private String address;

	public MemberDetailJson() {
	}

	public MemberDetailJson(MemberDetail detail) {
		this.realName = detail.getRealName();
		this.address = detail.getAddress();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
