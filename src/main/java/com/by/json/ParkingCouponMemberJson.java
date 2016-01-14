package com.by.json;

import com.by.model.ParkingCouponMember;

public class ParkingCouponMemberJson {
	private String contentImg;
	private int total;
	private int score;
	private double amount;
	private String name;

	public ParkingCouponMemberJson() {
	}

	public ParkingCouponMemberJson(ParkingCouponMember pcm) {
		this.contentImg = pcm.getCoupon().getContentImg();
		this.total = pcm.getTotal();
		this.score = pcm.getCoupon().getScore();
		this.amount = pcm.getCoupon().getAmount();
		this.name = pcm.getCoupon().getName();
	}

	public String getContentImg() {
		return contentImg;
	}

	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
