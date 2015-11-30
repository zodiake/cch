package com.by.actor;

import akka.actor.UntypedActor;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;
import com.by.typeEnum.DuplicateEnum;
import com.by.typeEnum.ValidEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;

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
            Calendar calendar = Calendar.getInstance();
            ParkingCouponMember pcm = (ParkingCouponMember) message;
            ParkingCoupon pc = parkingCouponService.findOne(pcm.getCoupon().getId());
            if (pc.getValid().equals(ValidEnum.VALID)) {
                if (pc.getBeginTime().before(calendar) && pc.getEndTime().after(calendar)) {

                }
                if (pc.getTotal() != null) {
                    Long total = parkingCouponMemberService.sumTotalGroupByCoupon(pc);
                    if (pc.getDuplicate().equals(DuplicateEnum.NOTDUPLICATE)) {
                        Long count = parkingCouponMemberService.countByCouponAndMember(pcm.getCoupon(), pcm.getMember());
                        if (count > 0) {
                            sender().tell("duplicate", null);
                        }
                    }
                    if (total >= pc.getTotal()) {
                        sender().tell("hello", null);
                    } else {
                        parkingCouponMemberService.exchangeCoupon(pcm.getMember(), pcm.getCoupon(), 1);
                    }
                }
                parkingCouponMemberService.exchangeCoupon(pcm.getMember(), pcm.getCoupon(), pc.getTotal());
            } else {
                sender().tell("outofdate", null);
            }
        } else {
            unhandled(message);
        }
    }
}
