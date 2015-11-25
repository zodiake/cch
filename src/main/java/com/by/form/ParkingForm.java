package com.by.form;

import java.util.Calendar;

/**
 * Created by yagamai on 15-11-25.
 */
public class ParkingForm {
    private String license;

    private Calendar startTime;

    private Calendar endTime;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
