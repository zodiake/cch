package com.by.json;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.by.model.Coupon;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-3.
 */
public class CouponTemplateJson extends CouponJson{
	private int score;

	private String beginTime;

	private String endTime;

	private String summary;

	private String shopName;

	private String state;

	private String cssClass;

	public CouponTemplateJson() {
	}

	public CouponTemplateJson(Coupon coupon) {
		super(coupon);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar today = Calendar.getInstance();

		if (coupon.getBeginTime() == null && coupon.getEndTime() == null) {
			this.cssClass = "text-success";
			this.state = "使用中";
		} else if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
			if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
				this.cssClass = "text-success";
				this.state = "使用中";
			} else if (coupon.getBeginTime().after(today)) {
				this.cssClass = "text-primary";
				this.state = "未生效";
			} else if (coupon.getEndTime().before(today)) {
				this.cssClass = "text-muted";
				this.state = "已过期";
			}
			this.beginTime = format.format(coupon.getBeginTime().getTime());
			this.endTime = format.format(coupon.getEndTime().getTime());
		}
		if (coupon.getValid().equals(ValidEnum.INVALID)) {
			this.cssClass = "text-danger";
			this.state = "已关闭";
		}
		this.score = coupon.getScore();
		this.beginTime = format.format(coupon.getBeginTime().getTime());
		this.endTime = format.format(coupon.getEndTime().getTime());
		this.summary = coupon.getSummary();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
