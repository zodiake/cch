package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "by_parking_coupon_exchange_history")
@IdClass(MemberCouponId.class)
public class ParkingCouponExchangeHistory {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private ParkingCoupon coupon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;

    private Integer total;

    public ParkingCouponExchangeHistory() {
    }

    public ParkingCouponExchangeHistory(Member member, ParkingCoupon coupon, int total) {
        this.member = member;
        this.total = total;
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

    public ParkingCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(ParkingCoupon coupon) {
        this.coupon = coupon;
    }

    @PrePersist
    private void prePersist() {
        this.createdTime = Calendar.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof ParkingCouponExchangeHistory) {
            ParkingCouponExchangeHistory that = (ParkingCouponExchangeHistory) o;
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
