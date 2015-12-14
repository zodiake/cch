package com.by.json;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.by.model.Member;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-7.
 */
public class MemberJson {
	@NotNull
	@Length(min = 11, max = 11)
	private String mobile;

	@NotNull
	@Length(min = 4, max = 10)
	private String password;

	private int score;

	private ValidEnum valid;

	public MemberJson() {
	}

	public MemberJson(Member member) {
		this.mobile = member.getName();
		this.score = member.getScore();
		this.score = member.getScore();
		this.valid = member.getValid();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}
}
