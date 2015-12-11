package com.by.json;

import com.by.model.Coupon;

import java.text.SimpleDateFormat;

/**
 * Created by yagamai on 15-12-3.
 */
public class CouponTemplateJson {
    private Long id;

    private String name;

    private String couponEndTime;

    private int score;

    private String beginTime;

    private String endTime;

    private String summary;

    private String shopName;

    public CouponTemplateJson() {
    }

    public CouponTemplateJson(Coupon coupon) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.couponEndTime = format.format(coupon.getCouponEndTime().getTime());
        this.score = coupon.getScore();
        this.beginTime = format.format(coupon.getBeginTime().getTime());
        this.endTime = format.format(coupon.getEndTime().getTime());
        this.summary = coupon.getSummary();
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
