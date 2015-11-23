package by.test;

import com.by.Application;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.repository.ParkingCouponUseHistoryRepository;
import com.by.service.ParkingCouponUseHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yagamai on 15-11-23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class ParkingCouponUseHistoryTest {
    @Autowired
    private ParkingCouponUseHistoryService service;

    @Test
    public void saveTest() {
        Member m = new Member(1l);
        int total = 1;
        String license = "adf";
        ParkingCoupon coupon = new ParkingCoupon(1l);
        service.save(m, total, license, coupon);
    }
}
