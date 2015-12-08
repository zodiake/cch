package com.by.json;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-7.
 */
public class CouponJson {
    private Long id;

    private String name;

    private String couponEndTime;


    public CouponJson() {
    }

    public CouponJson(Long id, String name, Calendar endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.couponEndTime = format.format(endTime.getTime());
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
}
