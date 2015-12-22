package com.by.json;

import com.by.model.Coupon;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.ValidEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    private CouponAdminStateEnum state;

    public CouponTemplateJson() {
    }

    public CouponTemplateJson(Coupon coupon) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();

        this.id = coupon.getId();
        this.name = coupon.getName();
        this.couponEndTime = format.format(coupon.getCouponEndTime().getTime());
        this.score = coupon.getScore();
        this.beginTime = format.format(coupon.getBeginTime().getTime());
        this.endTime = format.format(coupon.getEndTime().getTime());
        this.summary = coupon.getSummary();

        if (coupon.getBeginTime() != null && coupon.getEndTime() != null) {
            if (coupon.getValid().equals(ValidEnum.INVALID)) {
                this.state = CouponAdminStateEnum.CLOSED;
            } else if (coupon.getBeginTime().after(today)) {
                this.state = CouponAdminStateEnum.NOEXPIRE;
            } else if (coupon.getBeginTime().before(today) && coupon.getEndTime().after(today)) {
                this.state = CouponAdminStateEnum.USING;
            } else {
                this.state = CouponAdminStateEnum.EXPIRE;
            }
        }
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

    public CouponAdminStateEnum getState() {
        return state;
    }

    public void setState(CouponAdminStateEnum state) {
        this.state = state;
    }
}
