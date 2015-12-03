package com.by.json;

/**
 * Created by yagamai on 15-12-3.
 */
public class ParkingCouponJson {
    private Long id;

    private String name;

    private String couponEndTime;

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
