package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotValidException;
import com.by.message.ParkingCouponMessage;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.service.ParkingCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-11-30.
 */
@Component("ParkingCouponActor")
@Scope("prototype")
public class ParkingCouponActor extends UntypedActor {
    @Autowired
    private ParkingCouponMemberService service;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ParkingCouponMessage) {
            ParkingCouponMessage pcm = (ParkingCouponMessage) message;
            ParkingCoupon coupon = pcm.getParkingCoupon();
            Member member = pcm.getMember();
            int total = pcm.getTotal();
            try {
                service.exchangeCoupon(member, coupon, total);
                sender().tell("success", null);
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
