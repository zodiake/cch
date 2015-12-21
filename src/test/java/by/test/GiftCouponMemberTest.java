package by.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.by.Application;
import com.by.exception.NotEnoughScoreException;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.service.GiftCouponMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class GiftCouponMemberTest {
	@Autowired
	private GiftCouponMemberService service;

	@Test(expected = NotEnoughScoreException.class)
	public void exchangeTest() {
		service.exchangeCoupon(new GiftCoupon(3l), new Member(1l), 50);
		List<GiftCouponMember> list = service.findByCouponAndMember(new GiftCoupon(3l), new Member(1l));
		assertEquals(0, list.size());
	}
}
