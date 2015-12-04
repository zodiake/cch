package by.test;

import com.by.Application;
import com.by.exception.AlreadyExchangeException;
import com.by.exception.NotEnoughScoreException;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.service.MemberService;
import com.by.service.ParkingCouponExchangeHistoryService;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponUseHistoryService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class ParkingCouponMemberTest {
    @Autowired
    private ParkingCouponMemberService service;
    @Autowired
    private ParkingCouponExchangeHistoryService exchangeHistoryService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private MemberService memberService;

    @After
    public void reset() {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(new ParkingCoupon(1L));
        pcm.setMember(new Member(1L));
        pcm.setTotal(0);
        service.update(pcm);
    }

    @Test
    public void exchangeCoupon() {
        Member member = new Member(1L);
        ParkingCoupon coupon = new ParkingCoupon(1L);
        ParkingCouponMember pcm = service.findByCouponAndMember(member, new ParkingCoupon(1L));
        assertNull(pcm);
        ParkingCouponMember p = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(2), p.getTotal());
        Optional<Member> mary = memberService.findById(member.getId());
        assertEquals(90, mary.get().getScore());
    }

    @Test(expected = NotEnoughScoreException.class)
    public void exchangeCouponNotEnoughScore() {
        Member member = new Member(1L);
        ParkingCoupon coupon = new ParkingCoupon(1L);
        service.exchangeCoupon(member, coupon, 200);
    }

    @Test
    public void duplicateExchangeDuplicateCoupon() {
        Member member = new Member(1L);
        ParkingCoupon coupon = new ParkingCoupon(1L);
        ParkingCouponMember p = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(2), p.getTotal());
        ParkingCouponMember p2 = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(4), p2.getTotal());
    }

    @Test(expected = AlreadyExchangeException.class)
    public void duplicateExchangeNotDuplicateCoupon() {
        Member member = new Member(1L);
        ParkingCoupon coupon = new ParkingCoupon(2L);
        ParkingCouponMember pcm = service.findByCouponAndMember(member, new ParkingCoupon(2L));
        ParkingCouponMember p = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(1), p.getTotal());
        ParkingCouponMember p2 = service.exchangeCoupon(member, coupon, 2);
    }

}
