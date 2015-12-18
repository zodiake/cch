package com.by.actor;

import com.by.message.PreferentialCouponMessage;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.model.PreferentialCouponMember;
import com.by.service.PreferentialCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("PreferentialCouponActor")
@Scope("prototype")
public class PreferentialCouponActor extends AbstractCouponActor<PreferentialCoupon> {
    @Autowired
    private PreferentialCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PreferentialCouponMessage) {
            PreferentialCouponMessage couponMessage = (PreferentialCouponMessage) message;
            PreferentialCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            checkAndExchangeCoupon(coupon, member, total);
        } else {
            unhandled(message);
        }
    }

    @Override
    protected boolean outOfStorage(PreferentialCoupon coupon, int count) {
        Long total = service.sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    protected boolean alreadyExchangeCoupon(PreferentialCoupon coupon, Member member) {
        List<PreferentialCouponMember> result = service.findByCouponAndMember(coupon, member);
        return result.size() > 0;
    }

    @Override
    protected void serviceExchangeCoupon(PreferentialCoupon coupon, Member member, int total) {
        service.exchangeCoupon(coupon, member, total);
    }
}
