package com.by.model;

import com.by.typeEnum.CouponStateEnum;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */

@Entity
@Table(name = "by_preferential_coupon_member")
public class PreferentialCouponMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private PreferentialCoupon coupon;

    private String code;

    @Enumerated
    private CouponStateEnum state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exchanged_time")
    private Calendar exchangedTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "used_time")
    private Calendar usedTime;

    public PreferentialCouponMember() {
    }

    public PreferentialCouponMember(Member member, PreferentialCoupon coupon) {
        this.member = member;
        this.coupon = coupon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CouponStateEnum getState() {
        return state;
    }

    public void setState(CouponStateEnum state) {
        this.state = state;
    }

    public Calendar getExchangedTime() {
        return exchangedTime;
    }

    public void setExchangedTime(Calendar exchangedTime) {
        this.exchangedTime = exchangedTime;
    }

    public Calendar getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Calendar usedTime) {
        this.usedTime = usedTime;
    }

    @PrePersist
    private void prePersist() {
        this.exchangedTime = Calendar.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferentialCouponMember that = (PreferentialCouponMember) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
