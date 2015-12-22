package com.by.form;

import java.util.Calendar;

import com.by.model.Card;
import com.by.typeEnum.CouponAdminStateEnum;

/**
 * Created by yagamai on 15-12-11.
 */
public class CouponQueryForm {
    private CouponAdminStateEnum state;
    private Card card;
    private Calendar beginTime;
    private Calendar endTime;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

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
