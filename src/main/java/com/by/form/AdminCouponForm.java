package com.by.form;

// 接受admin界面商铺发放停车券请求数据
public class AdminCouponForm {
	//发放停车券的数量
	private int total;

	//停车券模板id
	private Long couponTemplateId;
	
	//用户手机
	private String mobile;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Long getCouponTemplateId() {
		return couponTemplateId;
	}

	public void setCouponTemplateId(Long couponTemplateId) {
		this.couponTemplateId = couponTemplateId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
