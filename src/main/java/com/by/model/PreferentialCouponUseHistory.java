package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-4.
 */
@Entity
@Table(name = "by_preferential_coupon_use_history")
@IdClass(MemberCouponId.class)
public class PreferentialCouponUseHistory {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preferential_coupon_id")
    private PreferentialCoupon coupon;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;

    private Integer total;

    public PreferentialCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(PreferentialCoupon coupon) {
        this.coupon = coupon;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof PreferentialCouponUseHistory) {
            PreferentialCouponUseHistory that = (PreferentialCouponUseHistory) o;
            return this.member.equals(that.member) && this.coupon.equals(that.coupon);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return member.hashCode() + coupon.hashCode();
    }
}
