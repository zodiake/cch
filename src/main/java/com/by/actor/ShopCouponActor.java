package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.message.ShopCouponMessage;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.service.ShopCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-8.
 */
@Component("ShopCouponActor")
@Scope("prototype")
public class ShopCouponActor extends UntypedActor {
    @Autowired
    private ShopCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ShopCouponMessage) {
            ShopCouponMessage couponMessage = (ShopCouponMessage) message;
            ShopCoupon coupon = couponMessage.getCoupon();
            int total = couponMessage.getTotal();
            Member member = couponMessage.getMember();
            try {
                service.exchangeCoupon(member, coupon, total);
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
