package by.test;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

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
import com.by.form.AdminCouponForm;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.model.ParkingCouponMemberHistory;
import com.by.service.MemberService;
import com.by.service.ParkingCouponMemberHistroyService;
import com.by.service.ParkingCouponMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class CouponMemberTest {
	@Autowired
	private ParkingCouponMemberService service;
	@Autowired
	private ParkingCouponMemberHistroyService historyService;
	@Autowired
	private MemberService memberService;

	@Test(expected = MemberNotFoundException.class)
	public void getCouponMemberNotExist() {
		AdminCouponForm form = new AdminCouponForm();
		form.setCouponTemplateId(1l);
		form.setMobile("linda");
		service.getCoupon(form);
	}

	@Test
	public void getCouponMemberExist() {
		AdminCouponForm form = new AdminCouponForm();
		form.setCouponTemplateId(1l);
		form.setMobile("tom");
		form.setTotal(2);
		service.getCoupon(form);
		Optional<ParkingCouponMember> pcm = service.findByMember(new Member(1l));
		assertEquals(pcm.get().getTotal(), new Integer(2));
		Page<ParkingCouponMemberHistory> histories = historyService.findByMember(new Member(1l),
				new PageRequest(1, 10));
		assertEquals(histories.getTotalElements(), 1);
	}

	@Test
	public void exchangeCoupon() {
		Member member = new Member(2l);
		ParkingCoupon coupon = new ParkingCoupon(1l);
		int count = 2;
		Optional<ParkingCouponMember> pcm = service.findByMember(member);
		assertEquals(new Integer(2), pcm.get().getTotal());
	}

	@Test
	public void useCoupon() {
		service.useCoupon(new Member(2l), 8, "aaa");
		Optional<ParkingCouponMember> pcm = service.findByMember(new Member(2l));
		Optional<Member> member = memberService.findById(2l);
		Page<ParkingCouponMemberHistory> histories = historyService.findByMember(member.get(), new PageRequest(1, 10));
		assertEquals(pcm.get().getTotal(), new Integer(2));
		assertEquals(histories.getTotalElements(), 1);
	}
}
