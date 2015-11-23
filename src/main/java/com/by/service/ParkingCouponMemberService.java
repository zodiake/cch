package com.by.service;

import java.util.Optional;

import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.Shop;

public interface ParkingCouponMemberService {
	/*
	 * 用户获得停车券,由商铺发放
	 */
	public ParkingCouponMember getCoupon(AdminCouponForm form,Shop shop);

	/*
	 * 用户通过积分兑换
	 * 
	 * @param member 用户
	 * 
	 * @param coupon 兑换的停车券
	 * 
	 * @param count 兑换的数量
	 */
	public ParkingCouponMember exchangeCoupon(Member member, ParkingCoupon coupon, int count);

	/*
	 * 用户使用停车券
	 * 
	 * @param member 使用人
	 * 
	 * @param total 使用的数量
	 * 
	 * @param license 充值的车牌
	 */
	public ParkingCouponMember useCoupon(Member member, int total, String license);

	/*
	 * 根据用户查找
	 * 
	 * @param member
	 */
	public Optional<ParkingCouponMember> findByMember(Member member);

	public ParkingCouponMember save(ParkingCoupon coupon, Member m, int total);

	public ParkingCouponMember update(ParkingCouponMember coupon);
}
