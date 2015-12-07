package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.PreferentialCouponMessage;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.service.CouponService;
import com.by.service.PreferentialCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("PreferentialCouponActor")
@Scope("prototype")
public class PreferentialCouponActor extends UntypedActor {
    @Autowired
    private PreferentialCouponMemberService service;
    @Autowired
    private CouponService couponService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PreferentialCouponMessage) {
            PreferentialCouponMessage couponMessage = (PreferentialCouponMessage) message;
            PreferentialCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            if (couponService.isWithinValidDate(coupon)) {
                if (!couponService.isPermanent(coupon)) {
                    if (!couponService.withinValidDate(coupon)) {
                        sender().tell("out of date", null);
                    }
                }
                if (!couponService.isDuplicateCoupon(coupon)) {
                    if (hadExchangeCoupon(coupon, member)) {
                        sender().tell("duplicate", null);
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

    public boolean outOfStorage(PreferentialCoupon coupon, int count) {
        Long total = service.sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    public boolean hadExchangeCoupon(PreferentialCoupon coupon, Member member) {
        PreferentialCouponMember result = service.findByCouponAndMember(coupon, member);
        return result != null;
    }

    public void checkStorageAndExchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        if (outOfStorage(coupon, total)) {
            sender().tell("out of storage", null);
        } else {
            exchangeCoupon(coupon, member, total);
        }
    }

    private void exchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        try {
            service.exchangeCoupon(coupon, member, total);
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
