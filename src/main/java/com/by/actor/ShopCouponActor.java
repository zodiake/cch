package com.by.actor;

import com.by.message.ShopCouponMessage;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.service.ShopCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
@Component("ShopCouponActor")
@Scope("prototype")
public class ShopCouponActor extends AbstractCouponActor<ShopCoupon> {
    @Autowired
    private ShopCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ShopCouponMessage) {
            ShopCouponMessage couponMessage = (ShopCouponMessage) message;
            ShopCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            checkAndExchangeCoupon(coupon, member, total);
        } else {
            unhandled(message);
        }
    }

    @Override
    public boolean outOfStorage(ShopCoupon coupon, int count) {
        Long total = service.countByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    protected void serviceExchangeCoupon(ShopCoupon coupon, Member member, int total) {
        service.exchangeCoupon(member, coupon, total);
    }
}
