package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.PreferentialCouponMessage;
import com.by.model.Coupon;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.service.PreferentialCouponMemberService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("PreferentialCouponActor")
@Scope("prototype")
public class PreferentialCouponActor extends UntypedActor {
    @Autowired
    private PreferentialCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PreferentialCouponMessage) {
            PreferentialCouponMessage couponMessage = (PreferentialCouponMessage) message;
            PreferentialCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            if (isValidCoupon(coupon)) {
                if (!isPermanent(coupon)) {
                    if (!withinValidDate(coupon)) {
                        sender().tell("out of date", null);
                    }
                }
                if (!isDuplicateCoupon(coupon)) {
                    if (hadExchangeCoupon(coupon, member)) {
                        sender().tell("duplicate", null);
                    }
                }
                if (noStorageLimited(coupon)) {
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

    private boolean noStorageLimited(PreferentialCoupon coupon) {
        return coupon.getTotal() == 0;
    }

    private void checkStorageAndExchangeCoupon(PreferentialCoupon couponSummary, Member member, int total) {
        if (outOfStorage(couponSummary, total)) {
            sender().tell("out of storage", null);
        } else {
            exchangeCoupon(couponSummary, member, total);
        }
    }

    private boolean isValidCoupon(Coupon couponSummary) {
        return couponSummary.getValid().equals(ValidEnum.VALID);
    }

    private boolean withinValidDate(Coupon couponSummary) {
        Calendar today = Calendar.getInstance();
        couponSummary.getEndTime().add(1, Calendar.DATE);
        return couponSummary.getBeginTime().before(today) && couponSummary.getEndTime().after(today);
    }

    private boolean isDuplicateCoupon(Coupon couponSummary) {
        return couponSummary.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
    }

    private boolean outOfStorage(PreferentialCoupon coupon, int count) {
        Long total = service.sumTotalGroupByCoupon(coupon);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    private boolean hadExchangeCoupon(PreferentialCoupon coupon, Member member) {
        PreferentialCouponMember result = service.findByCouponAndMember(coupon, member);
        return result != null;
    }

    private boolean isPermanent(Coupon couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

    private void exchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        try {
            service.exchangeCoupon(coupon, member, total);
        } catch (MemberNotValidException e) {
            sender().tell("member not valid", null);
        } catch (PasswordNotMatchException e) {
            sender().tell("password not match", null);
        }
        sender().tell("success", null);
    }
}
