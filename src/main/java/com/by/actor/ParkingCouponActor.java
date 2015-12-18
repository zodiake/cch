package com.by.actor;

import com.by.message.ParkingCouponMessage;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.service.ParkingCouponMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-11-30.
 */
@Component("ParkingCouponActor")
@Scope("prototype")
public class ParkingCouponActor extends AbstractCouponActor<ParkingCoupon> {
    @Autowired
    private ParkingCouponMemberService parkingCouponMemberService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ParkingCouponMessage) {
            ParkingCouponMessage pcm = (ParkingCouponMessage) message;
            ParkingCoupon coupon = pcm.getParkingCoupon();
            Member member = pcm.getMember();
            int total = pcm.getTotal();
            checkAndExchangeCoupon(coupon, member, total);
        } else {
            unhandled(message);
        }
    }

    @Override
    protected boolean outOfStorage(ParkingCoupon coupon, int count) {
        Long total = parkingCouponMemberService.sumTotalGroupByCoupon(coupon);
        if (total == null)
            total = new Long(0);
        return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
    }

    @Override
    protected boolean hadExchangeCoupon(ParkingCoupon coupon, Member member) {
        ParkingCouponMember result = parkingCouponMemberService.findByCouponAndMember(member, coupon);
        return result != null;
    }

    @Override
    protected void serviceExchangeCoupon(ParkingCoupon coupon, Member member, int total) {
        parkingCouponMemberService.exchangeCoupon(member, coupon, total);
    }
}
