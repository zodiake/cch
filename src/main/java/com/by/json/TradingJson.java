package com.by.json;

import com.by.model.Trading;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 15-12-9.
 */
public class TradingJson {
	private Long id;
	private double amount;
	private String shopName;
	private String createdTime;

	public TradingJson() {
	}

	public TradingJson(Trading trading) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.id = trading.getId();
		this.amount = trading.getAmount();
		if (trading.getShop() != null)
			this.shopName = trading.getShop().getName();
		if (trading.getCreatedTime() != null)
			this.createdTime = format.format(trading.getCreatedTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
}
