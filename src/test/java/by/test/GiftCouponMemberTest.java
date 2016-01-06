package by.test;

import com.by.Application;
import com.by.exception.NotEnoughScoreException;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.repository.GiftCouponMemberRepository;
import com.by.service.GiftCouponMemberService;
import com.by.service.GiftCouponService;
import com.by.typeEnum.ValidEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class GiftCouponMemberTest {
    @Autowired
    private GiftCouponMemberService service;
    @Autowired
    private GiftCouponService giftCouponService;
    @Autowired
    private GiftCouponMemberRepository repository;

    @Test(expected = NotEnoughScoreException.class)
    public void exchangeTest() {
        service.exchangeCoupon(new GiftCoupon(3), new Member(1l), 50);
        List<GiftCouponMember> list = service.findByCouponAndMember(new GiftCoupon(3), new Member(1l));
        assertEquals(0, list.size());
    }

    @Test
    public void te() {
        Page<GiftCoupon> lists = giftCouponService.findAllByValidAndDateBetween(ValidEnum.VALID, Calendar.getInstance(),
                new PageRequest(0, 10));
        assertEquals(2, lists.getContent().size());
    }

    @Test
    public void other() {
        Page<GiftCouponMember> lists = repository.findByMemberAndValid(new Member(1l), ValidEnum.VALID, Calendar.getInstance(), new PageRequest(0, 10));
        assertEquals(2, lists.getContent().size());
    }
}
