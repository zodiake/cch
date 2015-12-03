package com.by.message;

import com.by.model.CouponSummary;
import com.by.model.Member;

/**
 * Created by yagamai on 15-12-2.
 */
public class CouponMessage {
    private CouponSummary summary;
    private Member member;

    public CouponMessage(Member m,CouponSummary summary){
        this.member=m;
        this.summary=summary;
    }

    public CouponSummary getSummary() {
        return summary;
    }

    public void setSummary(CouponSummary summary) {
        this.summary = summary;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
