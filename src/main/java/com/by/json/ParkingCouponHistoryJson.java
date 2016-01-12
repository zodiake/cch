package com.by.json;

import com.by.model.ParkingCouponExchangeHistory;
import com.by.model.ParkingCouponUseHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 16-1-6.
 */
public class ParkingCouponHistoryJson {
    private Calendar createdTime;
    private String created;
    private int total;
    private String couponName;

    public ParkingCouponHistoryJson(ParkingCouponExchangeHistory history) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdTime = history.getCreatedTime();
        this.created = dateFormat.format(this.createdTime.getTime());
        this.total = history.getTotal();
        this.couponName = history.getCoupon().getName();
    }

    public ParkingCouponHistoryJson(ParkingCouponUseHistory history) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.createdTime = history.getCreatedTime();
        this.created = dateFormat.format(this.createdTime.getTime());
        this.total = history.getTotal();
        this.couponName = history.getCoupon().getName();
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
