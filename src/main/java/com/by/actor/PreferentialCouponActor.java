package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.PreferentialCouponMessage;
import com.by.model.Coupon;
import com.by.model.Member;
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
            Coupon coupon = couponMessage.getCoupon();
            Member member = couponMessage.getMember();
            if (isValidCoupon(coupon)) {
                if (!isPermanent(coupon)) {
                    if (withinValidDate(coupon)) {
                        if (!isDuplicateCoupon(coupon)) {
                            if (hadExchangeCoupon(coupon, member)) {
                                sender().tell("duplicate", null);
                            }
                            checkStorageAndBindMember(coupon, member);
                        }
                        checkStorageAndBindMember(coupon, member);
                    } else {
                        sender().tell("out of date", null);
                    }
                } else {
                    bindMember(coupon, member);
                }
            } else {
                sender().tell("invalid parkingCoupon", null);
            }
        } else {
            unhandled(message);
        }
    }

    private void checkStorageAndBindMember(Coupon couponSummary, Member member) {
        if (outOfStorage(couponSummary)) {
            sender().tell("out of storage", null);
        } else {
            bindMember(couponSummary, member);
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

    private boolean outOfStorage(Coupon summary) {
        // todo
        return true;
    }

    private boolean hadExchangeCoupon(Coupon summary, Member member) {
        // todo
        return false;
    }

    private boolean isPermanent(Coupon couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

    private void bindMember(Coupon summary, Member member) {
        try {
            // todo
            ;
        } catch (MemberNotValidException e) {
            sender().tell("member not valid", null);
        } catch (PasswordNotMatchException e) {
            sender().tell("password not match", null);
        }
        sender().tell("success", null);
    }
}
