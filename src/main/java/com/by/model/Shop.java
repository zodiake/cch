package com.by.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-23.
 */
@Entity
@Table(name = "by_shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty(message="{NotEmpty.shop.name}")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "by_shop_menu", joinColumns = @JoinColumn(name = "shop_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;

    @NotNull
    @Column(name="shop_key")
    private String shopKey;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<ShopCoupon> coupons;

    @ManyToMany(mappedBy = "shops")
    private List<ShopRule> rules;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Calendar updatedTime;

    @Column(name = "updated_by")
    private String updatedBy;
    
    @Column(name="img_href")
    private String imgHref;

    public Shop() {
    }

    public Shop(int id) {
        this.id = id;
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

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public List<ShopCoupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<ShopCoupon> coupons) {
        this.coupons = coupons;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<ShopRule> getRules() {
        return rules;
    }

    public void setRules(List<ShopRule> rules) {
        this.rules = rules;
    }

    @PrePersist
    private void prePersist(){
        this.createdTime=Calendar.getInstance();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedTime=Calendar.getInstance();
    }

    public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        return id == shop.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
