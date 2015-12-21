package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.MemberNotValidException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.PasswordNotMatchException;
import com.by.model.Coupon;
import com.by.model.Member;
import com.by.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-18.
 */
@Component
public abstract class AbstractCouponActor<T extends Coupon> extends UntypedActor {
    @Autowired
    private CouponService couponService;

    public abstract void onReceive(Object message) throws Exception;

    protected void checkAndExchangeCoupon(T coupon, Member member, int total) {
        if (couponService.isValidCoupon(coupon)) {
            if (couponService.noStorageLimited(coupon)) {
                exchangeCoupon(coupon, member, total);
            } else {
                checkStorageAndExchangeCoupon(coupon, member, total);
            }
        } else {
            sender().tell("invalid coupon", null);
        }
    }

    private void checkStorageAndExchangeCoupon(T coupon, Member member, int total) {
        if (outOfStorage(coupon, total)) {
            sender().tell("out of storage", null);
        } else {
            exchangeCoupon(coupon, member, total);
        }
    }

    private void exchangeCoupon(T coupon, Member member, int total) {
        try {
            serviceExchangeCoupon(coupon, member, total);
        } catch (MemberNotValidException e) {
            sender().tell("member not valid", null);
        } catch (PasswordNotMatchException e) {
            sender().tell("password not match", null);
        } catch (AlreadyExchangeException e) {
            sender().tell("duplicate", null);
        } catch (NotEnoughScoreException e) {
            sender().tell("not enough coupon", null);
        }
        sender().tell("success", null);
    }

    protected abstract boolean outOfStorage(T coupon, int count);

    protected abstract boolean alreadyExchangeCoupon(T coupon, Member member);

    protected abstract void serviceExchangeCoupon(T coupon, Member member, int total);

}
