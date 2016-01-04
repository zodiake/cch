package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.by.model.CardRule;
import com.by.model.ShopRule;
import com.by.typeEnum.ValidEnum;

/**
* Created by yagamai on 15-12-10.
*/
public class RuleJson {
	private String beginTime;
	private String endTime;
	private String cardName;
	private String category;
	private String cssClass;
	private String state;
	private String name;

	public RuleJson() {
	}

	public RuleJson(CardRule rule) {
		Calendar today = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (rule.getBeginTime() == null && rule.getEndTime() == null) {
			this.cssClass = "text-success";
			this.state = "使用中";
		} else if (rule.getBeginTime() != null && rule.getEndTime() != null) {
			if (rule.getBeginTime().before(today) && rule.getEndTime().after(today)) {
				this.cssClass = "text-success";
				this.state = "使用中";
			} else if (rule.getBeginTime().after(today)) {
				this.cssClass = "text-primary";
				this.state = "未生效";
			} else if (rule.getEndTime().before(today)) {
				this.cssClass = "text-muted";
				this.state = "已过期";
			}
			this.beginTime = format.format(rule.getBeginTime().getTime());
			this.endTime = format.format(rule.getEndTime().getTime());
		}
		if (rule.getValid().equals(ValidEnum.INVALID)) {
			this.cssClass = "text-danger";
			this.state = "已关闭";
		}
		this.name = rule.getName();
		this.cardName = rule.getCard().getName();
		this.category = rule.getRuleCategory().getName();
	}

	public RuleJson(ShopRule rule) {
		Calendar today = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (rule.getBeginTime() == null && rule.getEndTime() == null) {
			this.cssClass = "text-success";
			this.state = "使用中";
		} else if (rule.getBeginTime() != null && rule.getEndTime() != null) {
			if (rule.getBeginTime().before(today) && rule.getEndTime().after(today)) {
				this.cssClass = "text-success";
				this.state = "使用中";
			} else if (rule.getBeginTime().after(today)) {
				this.cssClass = "text-primary";
				this.state = "未生效";
			} else if (rule.getEndTime().before(today)) {
				this.cssClass = "text-muted";
				this.state = "已过期";
			}
			this.beginTime = format.format(rule.getBeginTime().getTime());
			this.endTime = format.format(rule.getEndTime().getTime());
		}
		if (rule.getValid().equals(ValidEnum.INVALID)) {
			this.cssClass = "text-danger";
			this.state = "已关闭";
		}
		this.name = rule.getName();
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

