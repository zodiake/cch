package com.by.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "by_member")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@Min(value = 4)
	private String password;

	// 注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@JoinColumn(name="created_time")
	private Calendar createdTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	@JsonManagedReference
	private Card card;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private MemberDetail memberDetail;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<ParkingCouponMember> parkingCoupons;

	// 兑换记录
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Coupon> coupons;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "by_member_license", joinColumns = @JoinColumn(name = "member_id") , inverseJoinColumns = @JoinColumn(name = "license_id") )
	private List<License> licenses;

	public Member() {
	}

	public Member(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public MemberDetail getMemberDetail() {
		return memberDetail;
	}

	public void setMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}

	public List<ParkingCouponMember> getParkingCoupons() {
		return parkingCoupons;
	}

	public void setParkingCoupons(List<ParkingCouponMember> parkingCoupons) {
		this.parkingCoupons = parkingCoupons;
	}
	
	public List<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
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
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
