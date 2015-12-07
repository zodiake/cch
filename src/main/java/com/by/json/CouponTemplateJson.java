package com.by.json;

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

    public CouponTemplateJson() {
    }

    public CouponTemplateJson(Long id, String name, Calendar couponEndTime, int score, Calendar beginTime, Calendar endTime, String summary) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(couponEndTime.getTime());
        this.id = id;
        this.name = name;
        this.couponEndTime = time;
        this.score = score;
        this.beginTime = format.format(beginTime.getTime());
        this.endTime = format.format(endTime.getTime());
        this.summary = summary;
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
