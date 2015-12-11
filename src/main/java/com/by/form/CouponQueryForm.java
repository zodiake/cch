package com.by.form;

import com.by.typeEnum.ValidEnum;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-11.
 */
public class CouponQueryForm {
    private ValidEnum valid;
    private Calendar beginTime;
    private Calendar endTime;

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
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
