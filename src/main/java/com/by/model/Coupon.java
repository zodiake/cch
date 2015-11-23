package com.by.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.by.typeEnum.ExchangeState;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "by_coupon")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "summary_id")
	@JsonBackReference
	private CouponSummary summary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exchange_time")
	private Calendar exchangeTime;
	
	@Enumerated
	@Column(name="exchange_state")
	private ExchangeState state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "use_time")
	private Calendar useTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public CouponSummary getSummary() {
		return summary;
	}

	public void setSummary(CouponSummary summary) {
		this.summary = summary;
	}

	public Calendar getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Calendar exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	
	public ExchangeState getState() {
		return state;
	}

	public void setState(ExchangeState state) {
		this.state = state;
	}

	public Calendar getUseTime() {
		return useTime;
	}

	public void setUseTime(Calendar useTime) {
		this.useTime = useTime;
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
		Coupon other = (Coupon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}