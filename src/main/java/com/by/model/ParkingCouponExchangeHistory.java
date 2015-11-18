package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "by_parking_coupon_exchange_history")
public class ParkingCouponExchangeHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mobile;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar exchangeTime;

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private ParkingCoupon coupon;

	@Column(name = "created_by")
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Calendar getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Calendar exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public ParkingCoupon getCoupon() {
		return coupon;
	}

	public void setCoupon(ParkingCoupon coupon) {
		this.coupon = coupon;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ParkingCouponExchangeHistory other = (ParkingCouponExchangeHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
