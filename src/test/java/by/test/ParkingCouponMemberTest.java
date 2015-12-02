package by.test;

import com.by.Application;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScore;
import com.by.form.AdminCouponForm;
import com.by.model.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class ParkingCouponMemberTest {
    @Autowired
    private ParkingCouponMemberService service;
    @Autowired
    private ParkingCouponExchangeHistoryService exchangeHistroyService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private MemberService memberService;

    @After
    public void reset() {
        ParkingCouponMember pcm = new ParkingCouponMember();
        pcm.setCoupon(new ParkingCoupon(1L));
        pcm.setMember(new Member(2L));
        pcm.setTotal(10);
        service.update(pcm);
    }

    @Test(expected = MemberNotFoundException.class)
    public void getCouponMemberNotExist() {
        AdminCouponForm form = new AdminCouponForm();
        form.setCouponTemplateId(1L);
        form.setMobile("linda");
        Shop shop = new Shop(1L);
        service.getCouponFromShop(form, shop);
    }

    @Test
    public void getCouponMemberExist() {
        AdminCouponForm form = new AdminCouponForm();
        form.setCouponTemplateId(1L);
        form.setMobile("13611738422");
        form.setTotal(2);
        service.getCouponFromShop(form, new Shop(1L));
        Optional<ParkingCouponMember> pcm = service.findByMemberAndCoupon(new Member(1L), new ParkingCoupon(1L));
        assertNotNull(pcm.get());
        assertEquals(pcm.get().getTotal(), new Integer(12));
        Page<ParkingCouponExchangeHistory> histories = exchangeHistroyService.findByMember(new Member(1L),
                new PageRequest(1, 10));
        assertEquals(1, histories.getTotalElements());
    }

    @Test
    public void exchangeCoupon() {
        Member member = new Member(2L);
        member.setName("aaaaaaaaaaa");
        ParkingCoupon coupon = new ParkingCoupon(1L);
        Optional<ParkingCouponMember> pcm = service.findByMemberAndCoupon(member, new ParkingCoupon(1L));
        assertEquals(new Integer(10), pcm.get().getTotal());
        ParkingCouponMember p = service.exchangeCoupon(member, coupon, 2);
        assertEquals(new Integer(12), p.getTotal());
    }

    @Test(expected = NotEnoughScore.class)
    public void exchangeCouponNotEnoughScore() {
        Member member = new Member(2L);
        ParkingCoupon coupon = new ParkingCoupon(1L);
        Optional<ParkingCouponMember> pcm = service.findByMemberAndCoupon(member, new ParkingCoupon(1L));
        assertEquals(new Integer(10), pcm.get().getTotal());
        service.exchangeCoupon(member, coupon, 10);
        assertEquals(new Integer(12), pcm.get().getTotal());
    }

    @Test
    public void useCoupon() {
        service.useCoupon(new Member(2L), new ParkingCoupon(1L), 8, "aaa");
        Optional<ParkingCouponMember> pcm = service.findByMemberAndCoupon(new Member(2L), new ParkingCoupon(1L));
        Optional<Member> member = memberService.findById(2L);
        Page<ParkingCouponUseHistory> histories = useHistoryService.findByMember(member.get(), new PageRequest(1, 10));
        assertEquals(pcm.get().getTotal(), new Integer(2));
        assertEquals(histories.getTotalElements(), 1);
    }
}
