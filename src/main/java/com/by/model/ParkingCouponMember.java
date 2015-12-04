package com.by.model;

import javax.persistence.*;

@Entity
@Table(name = "by_parking_coupon_member")
@IdClass(MemberCouponId.class)
public class ParkingCouponMember {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private ParkingCoupon coupon;

    private Integer total;

    public ParkingCouponMember() {
    }

    public ParkingCouponMember(Member member, ParkingCoupon coupon) {
        this.coupon = coupon;
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ParkingCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(ParkingCoupon coupon) {
        this.coupon = coupon;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof ParkingCouponMember) {
            ParkingCouponMember that = (ParkingCouponMember) o;
            return this.member.equals(that.member) && this.coupon.equals(that.coupon);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return member.hashCode() + coupon.hashCode();
    }

    @Override
    public String toString() {
        return "ParkingCouponMember{" +
                "member=" + member.getId() +
                ", coupon=" + coupon.getId() +
                ", total=" + total +
                '}';
    }
}