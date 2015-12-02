package com.by.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.by.typeEnum.ValidEnum;
import org.hibernate.validator.constraints.Length;

import com.by.json.MemberRequestJson;
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
	@Length(max = 11, min = 11)
	private String name;

	private String password;

	// 注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	@JsonManagedReference
	private Card card;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private MemberDetail memberDetail;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<ParkingCouponMember> parkingCoupons;

	// 兑换记录
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Coupon> coupons;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "by_member_license", joinColumns = @JoinColumn(name = "member_id") , inverseJoinColumns = @JoinColumn(name = "license_id") )
	private List<License> licenses;

	private int score;

	@Enumerated
	private ValidEnum valid;

	public Member() {
	}

	public Member(Long id) {
		this.id = id;
	}

	public Member(MemberRequestJson json) {
		this.name = json.getName();
		this.password = json.getPassword();
		this.card = new Card(json.getCard());
	}

	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
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
