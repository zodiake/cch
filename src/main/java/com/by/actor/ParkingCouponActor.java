package com.by.actor;

import akka.actor.UntypedActor;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-11-30.
 */
@Component("ParkingCouponActor")
@Scope("prototype")
public class ParkingCouponActor extends UntypedActor {
    private ParkingCouponService parkingCouponService;
    private ParkingCouponMemberService parkingCouponMemberService;

    private Logger log = LoggerFactory.getLogger(TestActor.class);

    @Autowired
    public ParkingCouponActor(ParkingCouponService parkingCouponService, ParkingCouponMemberService parkingCouponMemberService) {
        this.parkingCouponService = parkingCouponService;
        this.parkingCouponMemberService = parkingCouponMemberService;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ParkingCouponMember) {
            ParkingCouponMember pcm = (ParkingCouponMember) message;
            ParkingCoupon pc = parkingCouponService.findOne(pcm.getCoupon().getId());
            Long total = parkingCouponMemberService.sumTotalGroupByCoupon(pc);
            if (total < pcm.getTotal()) {
                parkingCouponMemberService.exchangeCoupon(pcm.getMember(), pcm.getCoupon(), 1);
                sender().tell("success", null);
            } else {
                sender().tell("fail", null);
            }
        } else {
            unhandled(message);
        }
    }
}
