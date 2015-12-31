package com.by.form;

import com.by.typeEnum.CouponAdminStateEnum;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-31.
 */
public class BaseCouponForm {
    private CouponAdminStateEnum state;
    private Calendar beginTime;
    private Calendar endTime;

    public CouponAdminStateEnum getState() {
        return state;
    }

    public void setState(CouponAdminStateEnum state) {
        this.state = state;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
