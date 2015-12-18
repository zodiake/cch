package by.test;

import com.by.Application;
import com.by.model.ParkingCoupon;
import com.by.service.CouponService;
import com.by.typeEnum.ValidEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by yagamai on 15-12-7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class CouponServiceTest {
    @Autowired
    private CouponService service;

    @Test
    public void validTest() {
        ParkingCoupon coupon = new ParkingCoupon();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2014, 2, 1);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2014, 11, 1);
        coupon.setValid(ValidEnum.VALID);
        coupon.setBeginTime(beginTime);
        coupon.setEndTime(endTime);
        assertEquals(false, coupon.getEndTime().after(Calendar.getInstance()));
        assertEquals(false, service.isValidCoupon(coupon));
    }
}
