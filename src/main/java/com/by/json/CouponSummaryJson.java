package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.by.model.CouponSummary;

public class CouponSummaryJson {
	private Long id;

	private int score;

	private String beginTime;

	private String endTime;

	private String summary;

	private String couponEndTime;

	public CouponSummaryJson() {
	}

	public CouponSummaryJson(CouponSummary summary) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.id = summary.getId();
		this.score = summary.getScore();
		if (summary.getBeginTime() != null)
			this.beginTime = format.format(summary.getBeginTime().getTime());
		if (summary.getEndTime() != null)
			this.endTime = format.format(summary.getEndTime().getTime());
		if (summary.getCouponEndTime() != null)
			this.couponEndTime = format.format(summary.getCouponEndTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
}
