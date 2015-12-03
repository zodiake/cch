package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.CouponMessage;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.service.CouponService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("CouponActor")
@Scope("prototype")
public class CouponActor extends UntypedActor {
    @Autowired
    private CouponService couponService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof CouponMessage) {
            CouponMessage coupon = (CouponMessage) message;
            CouponSummary couponSummary = coupon.getSummary();
            if (isValidCoupon(couponSummary)) {
                if (!isPermanent(couponSummary)) {
                    if (withinValidDate(couponSummary)) {
                        if (!isDuplicateCoupon(couponSummary)) {
                            if (hadExchangeCoupon(couponSummary, coupon.getMember())) {
                                sender().tell("duplicate", null);
                            }
                            checkStorageAndBindMember(couponSummary, coupon.getMember());
                        }
                        checkStorageAndBindMember(couponSummary, coupon.getMember());
                    } else {
                        sender().tell("out of date", null);
                    }
                } else {
                    bindMember(couponSummary, coupon.getMember());
                }
            } else {
                sender().tell("invalid parkingCoupon", null);
            }
        } else {
            unhandled(message);
        }
    }

    private void checkStorageAndBindMember(CouponSummary couponSummary, Member member) {
        if (outOfStorage(couponSummary)) {
            sender().tell("out of storage", null);
        } else {
            bindMember(couponSummary, member);
        }
    }

    private boolean isValidCoupon(CouponSummary couponSummary) {
        return couponSummary.getValid().equals(ValidEnum.VALID);
    }

    private boolean withinValidDate(CouponSummary couponSummary) {
        Calendar today = Calendar.getInstance();
        couponSummary.getEndTime().add(1, Calendar.DATE);
        return couponSummary.getBeginTime().before(today) && couponSummary.getEndTime().after(today);
    }

    private boolean isDuplicateCoupon(CouponSummary couponSummary) {
        return couponSummary.getDuplicate().equals(DuplicateEnum.ISDUPLICATE);
    }

    private boolean outOfStorage(CouponSummary summary) {
        Long total = couponService.countBySummaryWhereMemberIsNull(summary);
        return total == 0;
    }

    private boolean hadExchangeCoupon(CouponSummary summary, Member member) {
        Long count = couponService.countBySummaryAndMember(summary, member);
        return count > 0;
    }

    private boolean isPermanent(CouponSummary couponSummary) {
        return couponSummary.getBeginTime() == null && couponSummary.getEndTime() == null;
    }

    private void bindMember(CouponSummary summary, Member member) {
        try {
            couponService.bindMember(summary, member);
        } catch (MemberNotValidException e) {
            sender().tell("member not valid", null);
        } catch (PasswordNotMatchException e) {
            sender().tell("password not match", null);
        }
        sender().tell("success", null);
    }
}
