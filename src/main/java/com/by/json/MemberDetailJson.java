package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.validator.constraints.Length;

import com.by.model.MemberDetail;

public class MemberDetailJson {
	@Length(max = 10)
	private String realName;

	@Length(max = 225)
	private String address;

	private String password;

	private Calendar birthday;

	private String birth;

	public MemberDetailJson() {
	}

	public MemberDetailJson(MemberDetail detail) {
		DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		this.realName = detail.getRealName();
		this.address = detail.getAddress();
		if (detail.getBirthday() != null)
			this.birth = formate.format(detail.getBirthday().getTime());
		this.birthday = detail.getBirthday();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
}
