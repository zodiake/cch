package com.by.json;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CardJson {
	private Long id;
	private String name;
	private ValidEnum valid;
	private int initScore;
	private String createdTime;
	private String createdBy;
	private String updatedTime;
	private String updatedBy;

	public CardJson() {
	}

	public CardJson(Card card) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:dd:ss");
		this.id = card.getId();
		this.name = card.getName();
		this.valid = card.getValid();
		this.initScore = card.getInitScore();
		if (card.getCreatedTime() != null)
			this.createdTime = format.format(card.getCreatedTime().getTime());
		if (card.getUpdatedTime() != null)
			this.updatedTime = format.format(card.getUpdatedTime().getTime());
		this.createdBy = card.getCreatedBy();
		this.updatedBy = card.getUpdatedBy();
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

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

	public int getInitScore() {
		return initScore;
	}

	public void setInitScore(int initScore) {
		this.initScore = initScore;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
