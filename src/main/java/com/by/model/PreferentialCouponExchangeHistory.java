package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */
@Entity
@Table(name = "by_preferential_coupon_exchange_history")
@IdClass(MemberCouponId.class)
public class PreferentialCouponExchangeHistory {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private PreferentialCoupon coupon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;


    private Integer total;

    public PreferentialCouponExchangeHistory() {
    }

    public PreferentialCouponExchangeHistory(Member member, PreferentialCoupon coupon, int total) {
        this.member = member;
        this.coupon = coupon;
        this.total = total;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public PreferentialCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(PreferentialCoupon coupon) {
        this.coupon = coupon;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @PrePersist
    private void prePersist() {
        this.createdTime = Calendar.getInstance();
    }


    public boolean equals(Object o) {
        if (o != null && o instanceof PreferentialCouponExchangeHistory) {
            PreferentialCouponExchangeHistory that = (PreferentialCouponExchangeHistory) o;
            return this.member.equals(that.member) && this.coupon.equals(that.coupon);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return member.hashCode() + coupon.hashCode();
    }
}
