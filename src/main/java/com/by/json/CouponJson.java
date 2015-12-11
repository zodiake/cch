package com.by.json;

import com.by.model.Coupon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-7.
 */
public class CouponJson {
    private Long id;

    private String name;

    private String couponEndTime;

    private String shopName;

    public CouponJson() {
    }

    public CouponJson(Long id, String name, Calendar endTime,String shopName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.couponEndTime = format.format(endTime.getTime());
        this.shopName=shopName;
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

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
