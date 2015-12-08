package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.ShopCouponMessage;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.service.CouponService;
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
public class ShopCouponActor extends UntypedActor {
    @Autowired
    private ShopCouponMemberService service;
    @Autowired
    private CouponService couponService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ShopCouponMessage) {
            ShopCouponMessage couponMessage = (ShopCouponMessage) message;
            ShopCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
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

    public boolean outOfStorage(ShopCoupon coupon, int count) {
        Long total = service.countByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    public boolean hadExchangeCoupon(ShopCoupon coupon, Member member) {
        List<ShopCouponMember> result = service.findByCouponAndMember(coupon, member);
        return result.size() > 0;
    }

    public void checkStorageAndExchangeCoupon(ShopCoupon coupon, Member member, int total) {
        if (outOfStorage(coupon, total)) {
            sender().tell("out of storage", null);
        } else {
            exchangeCoupon(coupon, member, total);
        }
    }

    private void exchangeCoupon(ShopCoupon coupon, Member member, int total) {
        try {
            service.exchangeCoupon(member, coupon, total);
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
