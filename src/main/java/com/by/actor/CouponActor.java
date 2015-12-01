package com.by.actor;

import akka.actor.UntypedActor;
import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.service.CouponService;
import com.by.service.MemberService;
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
    @Autowired
    private MemberService memberService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Coupon) {
            Coupon coupon = (Coupon) message;
            CouponSummary couponSummary = coupon.getSummary();
            if (couponSummary.getValid().equals(ValidEnum.VALID)) {
                Calendar today = Calendar.getInstance();
                Member member = coupon.getMember();
                if (couponSummary.getBeginTime() != null && couponSummary.getEndTime() != null) {
                    if (couponSummary.getBeginTime().after(today) && couponSummary.getEndTime().before(today)) {
                        sender().tell("out of date", null);
                    }
                }
                memberService.useScore(member, couponSummary.getScore());
                couponService.bindMember(coupon, member);
            } else {
                sender().tell("not valid", null);
            }
        } else {
            unhandled(message);
        }
    }
}
