package com.by.json;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */
public class CouponTemplateJson {
	private Long id;

	private String name;

	private String couponEndTime;

	private int score;

	private String beginTime;

	private String endTime;

	private String summary;

	private String shopName;

	public CouponTemplateJson() {
	}

	public CouponTemplateJson(Long id, String name, Calendar couponEndTime, int score, Calendar beginTime,
			Calendar endTime, String summary, String shopName) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(couponEndTime.getTime());
		this.id = id;
		this.name = name;
		this.couponEndTime = time;
		this.score = score;
		this.beginTime = format.format(beginTime.getTime());
		this.endTime = format.format(endTime.getTime());
		this.summary = summary;
		this.shopName = shopName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
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
}
