package com.by.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.by.model.ParkingCouponMember.MemberCouponId;

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
	@JoinColumn(name = "parking_coupon_id")
	private ParkingCoupon coupon;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

	private Integer total;

	public class MemberCouponId implements Serializable {
		private static final long serialVersionUID = 1L;
		private Long member;
		private Long coupon;

		public Long getMember() {
			return member;
		}

		public void setMember(Long member) {
			this.member = member;
		}

		public Long getCoupon() {
			return coupon;
		}

		public void setCoupon(Long coupon) {
			this.coupon = coupon;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof MemberCouponId) {
				MemberCouponId that = (MemberCouponId) o;
				return this.member.equals(that.member) && this.coupon.equals(that.coupon);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return member.hashCode() + coupon.hashCode();
		}
	}

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

	@PrePersist
	private void prePersist() {
		this.createdTime = Calendar.getInstance();
	}

}