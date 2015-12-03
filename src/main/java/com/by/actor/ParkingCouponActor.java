package com.by.actor;

import akka.actor.UntypedActor;
import com.by.exception.MemberNotValidException;
import com.by.exception.PasswordNotMatchException;
import com.by.message.ParkingCouponMessage;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
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
        if (message instanceof ParkingCouponMessage) {
            Calendar calendar = Calendar.getInstance();
            ParkingCouponMessage pcm = (ParkingCouponMessage) message;
            ParkingCoupon parkingCoupon = pcm.getParkingCoupon();
            ParkingCoupon pc = parkingCouponService.findOne(parkingCoupon.getId());
            // 停车券无效，不能兑换
            if (pc.getValid().equals(ValidEnum.VALID)) {
                if (pc.getBeginTime() != null && pc.getEndTime() != null) {
                    if (pc.getBeginTime().before(calendar) && pc.getEndTime().after(calendar)) {
                        if (pc.getDuplicate().equals(DuplicateEnum.NOTDUPLICATE)) {
                            Long count = parkingCouponMemberService.countByCouponAndMember(parkingCoupon, pcm.getMember());
                            if (count > 0) {
                                sender().tell("duplicate", null);
                            }
                        }
                        Long total = parkingCouponMemberService.sumTotalGroupByCoupon(pc);
                        if (total >= pc.getTotal()) {
                            //全部兑换
                            sender().tell("out of storage", null);
                        } else {
                            exchangeCoupon(pcm.getMember(), parkingCoupon, 1);
                        }
                    } else {
                        sender().tell("out of date", null);
                    }
                } else {
                    exchangeCoupon(pcm.getMember(), parkingCoupon, pcm.getTotal());
                }
            } else {
                sender().tell("invalid parkingCoupon", null);
            }
        } else {
            unhandled(message);
        }
    }

    private void exchangeCoupon(Member member, ParkingCoupon parkingCoupon, int total) {
        try {
            parkingCouponMemberService.exchangeCoupon(member, parkingCoupon, 1);
        } catch (MemberNotValidException e) {
            sender().tell("member not valid", null);
        } catch (PasswordNotMatchException e) {
            sender().tell("password not match", null);
        }
        sender().tell("success", null);
    }
}
