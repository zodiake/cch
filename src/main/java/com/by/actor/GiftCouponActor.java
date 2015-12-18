package com.by.actor;

import com.by.message.PreferentialCouponMessage;
import com.by.model.Member;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.service.GiftCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("GiftCouponActor")
@Scope("prototype")
public class GiftCouponActor extends AbstractCouponActor<GiftCoupon> {
    @Autowired
    private GiftCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PreferentialCouponMessage) {
            PreferentialCouponMessage couponMessage = (PreferentialCouponMessage) message;
            GiftCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            checkAndExchangeCoupon(coupon, member, total);
        } else {
            unhandled(message);
        }
    }

    @Override
    protected boolean outOfStorage(GiftCoupon coupon, int count) {
        Long total = service.sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    protected boolean alreadyExchangeCoupon(GiftCoupon coupon, Member member) {
        List<GiftCouponMember> result = service.findByCouponAndMember(coupon, member);
        return result.size() > 0;
    }

    @Override
    protected void serviceExchangeCoupon(GiftCoupon coupon, Member member, int total) {
        service.exchangeCoupon(coupon, member, total);
    }
}
