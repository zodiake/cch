package com.by.model;

import javax.persistence.*;

/**
 * Created by yagamai on 15-12-3.
 */

@Entity
@Table(name = "by_preferential_coupon_member")
@IdClass(MemberCouponId.class)
public class PreferentialCouponMember {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_coupon_id")
    private PreferentialCoupon coupon;

    private Integer total;

    public PreferentialCouponMember() {
    }

    public PreferentialCouponMember(Member member, PreferentialCoupon coupon) {
        this.member = member;
        this.coupon = coupon;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public boolean equals(Object o) {
        if (o != null && o instanceof PreferentialCouponMember) {
            PreferentialCouponMember that = (PreferentialCouponMember) o;
            return this.member.equals(that.member) && this.coupon.equals(that.coupon);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return member.hashCode() + coupon.hashCode();
    }
}
