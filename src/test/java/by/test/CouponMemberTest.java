package by.test;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import com.by.model.*;
import com.by.service.ParkingCouponUseHistoryService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.by.Application;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScore;
import com.by.form.AdminCouponForm;
import com.by.service.MemberService;
import com.by.service.ParkingCouponExchangeHistroyService;
import com.by.service.ParkingCouponMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class CouponMemberTest {
    @Autowired
    private ParkingCouponMemberService service;
    @Autowired
    private ParkingCouponExchangeHistroyService exchangeHistroyService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private MemberService memberService;

    @After
    public void reset() {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(new ParkingCoupon(1l));
        pcm.setMember(new Member(2l));
        pcm.setTotal(10);
        service.update(pcm);
    }

    @Test(expected = MemberNotFoundException.class)
    public void getCouponMemberNotExist() {
        AdminCouponForm form = new AdminCouponForm();
        form.setCouponTemplateId(1l);
        form.setMobile("linda");
        Shop shop = new Shop(1l);
        service.getCoupon(form, shop);
    }

    @Test
    public void getCouponMemberExist() {
        AdminCouponForm form = new AdminCouponForm();
        form.setCouponTemplateId(1l);
        form.setMobile("tom");
        form.setTotal(2);
        service.getCoupon(form, new Shop(1l));
        Optional<ParkingCouponMember> pcm = service.findByMember(new Member(1l));
        assertEquals(pcm.get().getTotal(), new Integer(2));
        Page<ParkingCouponExchangeHistory> histories = exchangeHistroyService.findByMember(new Member(1l),
                new PageRequest(1, 10));
        assertEquals(histories.getTotalElements(), 1);
    }

    @Test
    public void exchangeCoupon() {
        Member member = new Member(2l);
        ParkingCoupon coupon = new ParkingCoupon(1l);
        Optional<ParkingCouponMember> pcm = service.findByMember(member);
        assertEquals(new Integer(10), pcm.get().getTotal());
        ParkingCouponMember p = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(12), p.getTotal());
    }

    @Test(expected = NotEnoughScore.class)
    public void exchangeCouponNotEnoughScore() {
        Member member = new Member(2l);
        ParkingCoupon coupon = new ParkingCoupon(1l);
        Optional<ParkingCouponMember> pcm = service.findByMember(member);
        assertEquals(new Integer(10), pcm.get().getTotal());
        service.exchangeCoupon(member, coupon, 10);
        assertEquals(new Integer(12), pcm.get().getTotal());
    }

    @Test
    public void useCoupon() {
        service.useCoupon(new Member(2l), 8, "aaa");
        Optional<ParkingCouponMember> pcm = service.findByMember(new Member(2l));
        Optional<Member> member = memberService.findById(2l);
        Page<ParkingCouponUseHistory> histories = useHistoryService.findByMember(member.get(), new PageRequest(1, 10));
        assertEquals(pcm.get().getTotal(), new Integer(2));
        assertEquals(histories.getTotalElements(), 1);
    }
}
