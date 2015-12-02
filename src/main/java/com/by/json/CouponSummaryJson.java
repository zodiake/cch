package com.by.json;

import java.util.Calendar;

import com.by.model.CouponSummary;

public class CouponSummaryJson {
	private Long id;

	private int score;

	private Calendar beginTime;

	private Calendar endTime;

	private String summary;

	private Calendar couponEndTime;

	public CouponSummaryJson() {
	}

	public CouponSummaryJson(CouponSummary summary) {
		this.id=summary.getId();
		this.score = summary.getScore();
		this.beginTime = summary.getBeginTime();
		this.endTime = summary.getEndTime();
		this.couponEndTime = summary.getCouponEndTime();
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

	public Calendar getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Calendar getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Calendar couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
}
