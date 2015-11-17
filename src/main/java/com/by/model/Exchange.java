package com.by.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.by.model.Exchange.MemberCouponId;

@Entity
@Table(name = "by_exchange")
@IdClass(MemberCouponId.class)
public class Exchange {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	private Coupon coupon;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
			result = prime * result + ((member == null) ? 0 : member.hashCode());
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
			MemberCouponId other = (MemberCouponId) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (coupon == null) {
				if (other.coupon != null)
					return false;
			} else if (!coupon.equals(other.coupon))
				return false;
			if (member == null) {
				if (other.member != null)
					return false;
			} else if (!member.equals(other.member))
				return false;
			return true;
		}

		private Exchange getOuterType() {
			return Exchange.this;
		}

	}

	public Exchange() {
	}

	public Exchange(Member member, Coupon coupon) {
		this.coupon = coupon;
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
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
		Exchange other = (Exchange) obj;
		if (coupon == null) {
			if (other.coupon != null)
				return false;
		} else if (!coupon.equals(other.coupon))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		return true;
	}

}