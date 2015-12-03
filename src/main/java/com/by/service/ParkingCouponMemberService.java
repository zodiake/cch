package com.by.service;

import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.Shop;

import java.util.List;
import java.util.Optional;

public interface ParkingCouponMemberService {
    /*
     * 用户获得停车券,由商铺发放
     */
    ParkingCouponMember getCouponFromShop(AdminCouponForm form, Shop shop);

    /*
     * 用户通过积分兑换
     *
     * @param member 用户
     *
     * @param coupon 兑换的停车券
     *
     * @param count 兑换的数量
     */
    ParkingCouponMember exchangeCoupon(Member member, ParkingCoupon coupon, int count);

    /*
     * 用户使用停车券
     *
     * @param member 使用人
     *
     * @param total 使用的数量
     *
     * @param license 充值的车牌
     */
    ParkingCouponMember useCoupon(Member member, ParkingCoupon parkingCoupon, int total, String license);

    /*
     * 根据用户查找
     *
     * @param member
     */
    List<ParkingCouponMember> findByMember(Member member);

    ParkingCouponMember save(ParkingCoupon coupon, Member m, int total);

    ParkingCouponMember update(ParkingCouponMember coupon);

    Optional<ParkingCouponMember> findByMemberAndCoupon(Member member, ParkingCoupon parkingCoupon);

    Long sumTotalGroupByCoupon(ParkingCoupon coupon);

    Long countByCouponAndMember(ParkingCoupon parkingCoupon, Member member);
}
