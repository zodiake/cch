package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.by.model.User;

/**
 * Created by yagamai on 15-12-11.
 */
public class UserJson {
	private int id;
	private String name;
	private String isValid;
	private String role;
	private String updatedTime;

	public UserJson(User user) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.id = user.getId();
		this.name = user.getName();
		this.isValid = user.getValid().toString();
		List<String> list = user.getUserAuthorities().stream().map(i->i.getName()).collect(Collectors.toList());
		this.role = String.join(",", list);
		if (user.getUpdatedTime() != null)
			this.updatedTime = format.format(user.getUpdatedTime().getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
}
