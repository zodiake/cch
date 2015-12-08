package com.by.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yagamai on 15-11-23.
 */
@Entity
@Table(name = "by_shop")
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "by_shop_menu", joinColumns = @JoinColumn(name = "shop_id") , inverseJoinColumns = @JoinColumn(name = "menu_id") )
	private List<Menu> menus;

	private String key;

	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	private List<ShopCoupon> coupons;

	public Shop() {
	}

	public Shop(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<ShopCoupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<ShopCoupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Shop shop = (Shop) o;

		return !(id != null ? !id.equals(shop.id) : shop.id != null);

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
