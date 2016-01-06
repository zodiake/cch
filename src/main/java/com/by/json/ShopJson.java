package com.by.json;

import com.by.model.Menu;
import com.by.model.Shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-9.
 */
public class ShopJson {
	private int id;
	private String name;
	private String key;
	private Integer[] menus;
	private String updateTime;

	public ShopJson() {
	}

	public ShopJson(Shop shop) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:dd:ss");
		this.id = shop.getId();
		this.name = shop.getName();
		this.key = shop.getShopKey();
		this.menus = shop.getMenus().stream().map(Menu::getId).collect(Collectors.toList()).toArray(new Integer[] {});
		 if (shop.getUpdatedTime() != null)
	            this.updateTime = format.format(shop.getUpdatedTime().getTime());
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer[] getMenus() {
		return menus;
	}

	public void setMenus(Integer[] menus) {
		this.menus = menus;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
