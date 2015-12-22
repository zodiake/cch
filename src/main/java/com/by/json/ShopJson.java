package com.by.json;

import com.by.model.Menu;
import com.by.model.Shop;

import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-9.
 */
public class ShopJson {
    private Long id;
    private String name;
    private String key;
    private Long[] menus;

    public ShopJson() {
    }

    public ShopJson(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.key = shop.getShopKey();
        this.menus = shop.getMenus().stream().map(Menu::getId).collect(Collectors.toList()).toArray(new Long[]{});
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long[] getMenus() {
        return menus;
    }

    public void setMenus(Long[] menus) {
        this.menus = menus;
    }
}
