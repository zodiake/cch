package com.by.model;

import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue("s")
public class ShopRule extends Rule {
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "by_shop_rule", joinColumns = @JoinColumn(name = "rule_id") , inverseJoinColumns = @JoinColumn(name = "shop_id") )
	private List<Shop> shops;

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
}
