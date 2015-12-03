package com.by.json;

public class ExchangeCouponJson {
	//需要兑换的coupon id
	private Long id;
	//密码
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
