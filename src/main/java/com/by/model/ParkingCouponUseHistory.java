package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-11-23.
 */
@Entity
@Table(name = "by_parking_coupon_use_history")
@IdClass(MemberCouponId.class)
public class ParkingCouponUseHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "parking_coupon_id")
    private ParkingCoupon coupon;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Calendar createdTime;

    private Integer total;

    private String license;

    public ParkingCouponUseHistory() {
    }

    public ParkingCouponUseHistory( Member member, int total, String license) {
        this.member = member;
        this.total = total;
        this.license = license;
    }

    public ParkingCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(ParkingCoupon coupon) {
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof ParkingCouponUseHistory) {
            ParkingCouponUseHistory that = (ParkingCouponUseHistory) o;
            return this.member.equals(that.member) && this.coupon.equals(that.coupon);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return member.hashCode() + coupon.hashCode();
    }
}
