package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.message.GiftCouponMessage;
import com.by.model.GiftCoupon;
import com.by.model.Member;
import com.by.service.GiftCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-1.
 */
@Component("GiftCouponActor")
@Scope("prototype")
public class GiftCouponActor extends UntypedActor {
    @Autowired
    private GiftCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof GiftCouponMessage) {
            GiftCouponMessage couponMessage = (GiftCouponMessage) message;
            GiftCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            try {
                service.exchangeCoupon(coupon, member, total);
                sender().tell("success", null);
            } catch (AlreadyExchangeException e) {
                sender().tell("alreadyExchanged", null);
            } catch (OutOfStorageException e) {
                sender().tell("OutOfStorageException ", null);
            } catch (NotValidException e) {
                sender().tell("NotValidException ", null);
            } catch (NotEnoughScoreException e) {
                sender().tell("NotEnoughScoreException ", null);
            }
        } else {
            unhandled(message);
        }
    }
}
