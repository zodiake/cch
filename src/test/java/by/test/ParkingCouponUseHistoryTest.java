package by.test;

import com.by.Application;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponUseHistory;
import com.by.service.ParkingCouponUseHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

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
    private Calendar start;
    private Calendar end;
    private Member member;

    @Before
    public void before() {
        start = Calendar.getInstance();
        start.set(2015, 10, 25, 13, 0);
        end = Calendar.getInstance();
        end.set(2015, 10, 25, 15, 0);
    }

    @Test
    public void saveTest() {
        int total = 1;
        String license = "adf";
        ParkingCoupon coupon = new ParkingCoupon(1l);
        service.save(member, total, license, coupon);
    }

    @Test
    public void findByMemberAndCreatedTimeBetween() {
        List<ParkingCouponUseHistory> results = service.findByLicenseAndCreatedTimeBetween("abc", start, end);
        assertEquals(2, results.size());
    }

    @Test
    public void findByMemberAndCreatedTimeBetweenString() throws ParseException {
        List<ParkingCouponUseHistory> results = service.findByLicenseAndCreatedTimeBetween("abc", "2015-11-25 13:00:00", "2015-11-25 15:00:00");
        assertEquals(2, results.size());
    }
}
