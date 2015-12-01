package com.by.json;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class MemberRequestJson {
	@NotNull
	@Length(max = 11, min = 11)
	private String name;
	private String password;
	private Long card;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCard() {
		return card;
	}

	public void setCard(Long card) {
		this.card = card;
	}

}
