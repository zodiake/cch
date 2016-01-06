package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-11-23.
 */
@Entity
@Table(name = "by_parking_coupon_use_history")
public class ParkingCouponUseHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    @ManyToOne
    @JoinColumn(name = "parking_coupon_id")
    private ParkingCoupon coupon;

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

    public ParkingCouponUseHistory(Member member, int total, String license) {
        this.member = member;
        this.total = total;
        this.license = license;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingCouponUseHistory other = (ParkingCouponUseHistory) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
