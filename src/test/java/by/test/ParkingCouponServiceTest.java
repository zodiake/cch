package by.test;

import com.by.Application;
import com.by.model.ParkingCoupon;
import com.by.service.ParkingCouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by yagamai on 15-12-29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class ParkingCouponServiceTest {
    @Autowired
    private ParkingCouponService service;

    @Test
    public void sortTest() {
        ParkingCoupon parkingCoupon = service.findActivate();
    }

    @Test
    public void saveTest() {
        ParkingCoupon p = new ParkingCoupon();
        p.setName("test");
        p.setSummary("haha");
        p.setEndTime(Calendar.getInstance());
        ParkingCoupon pc = service.save(p);
        assertEquals(new Double(10), pc.getAmount());
    }

}
