package com.by.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.ParkingCouponMessage;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.service.CouponService;
import com.by.service.ParkingCouponMemberService;

import akka.actor.UntypedActor;

/**
 * Created by yagamai on 15-11-30.
 */
// todo need change code;
@Component("ParkingCouponActor")
@Scope("prototype")
public class ParkingCouponActor extends UntypedActor {
	private ParkingCouponMemberService parkingCouponMemberService;
	private CouponService couponService;

	private Logger log = LoggerFactory.getLogger(TestActor.class);

	@Autowired
	public ParkingCouponActor(ParkingCouponMemberService parkingCouponMemberService, CouponService couponService) {
		this.parkingCouponMemberService = parkingCouponMemberService;
		this.couponService = couponService;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof ParkingCouponMessage) {
			ParkingCouponMessage pcm = (ParkingCouponMessage) message;
			ParkingCoupon coupon = pcm.getParkingCoupon();
			Member member = pcm.getMember();
			int total = pcm.getTotal();
			if (couponService.isWithinValidDate(coupon)) {
				if (!couponService.isPermanent(coupon)) {
					if (!couponService.withinValidDate(coupon)) {
						sender().tell("out of date", null);
						return;
					}
				}
				if (!couponService.isDuplicateCoupon(coupon)) {
					if (hadExchangeCoupon(coupon, member)) {
						sender().tell("duplicate", null);
						return;
					}
				}
				if (couponService.noStorageLimited(coupon)) {
					exchangeCoupon(coupon, member, total);
				} else {
					checkStorageAndExchangeCoupon(coupon, member, total);
				}
			} else {
				sender().tell("invalid parkingCoupon", null);
			}
		} else {
			unhandled(message);
		}
	}

	public boolean outOfStorage(ParkingCoupon coupon, int count) {
		Long total = parkingCouponMemberService.sumTotalGroupByCoupon(coupon);
		if (total == null)
			total = new Long(0);
		return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
	}

	public boolean hadExchangeCoupon(ParkingCoupon coupon, Member member) {
		ParkingCouponMember result = parkingCouponMemberService.findByCouponAndMember(member, coupon);
		return result != null;
	}

	public void checkStorageAndExchangeCoupon(ParkingCoupon coupon, Member member, int total) {
		if (outOfStorage(coupon, total)) {
			sender().tell("out of storage", null);
		} else {
			exchangeCoupon(coupon, member, total);
		}
	}

	private void exchangeCoupon(ParkingCoupon coupon, Member member, int total) {
		try {
			parkingCouponMemberService.exchangeCoupon(member, coupon, total);
		} catch (MemberNotValidException e) {
			sender().tell("member not valid", null);
		} catch (PasswordNotMatchException e) {
			sender().tell("password not match", null);
		} catch (AlreadyExchangeException e) {
			sender().tell("duplicate", null);
		}
		sender().tell("success", null);
	}
}
