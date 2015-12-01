package by.test;

import com.by.Application;
import com.by.model.Coupon;
import com.by.model.CouponSummary;
import com.by.service.CouponService;
import com.by.service.CouponSummaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class CouponSummaryTest {
    @Autowired
    private CouponSummaryService summaryService;
    @Autowired
    private CouponService service;

    @Test
    public void save() {
        CouponSummary summary = new CouponSummary();
        summary.setTotal(50);
        CouponSummary s = summaryService.save(summary);
        assertEquals(new Long(50), service.countBySummary(s));
        assertEquals(new Long(50), service.countBySummaryWhereMemberIsNull(s));
    }
}
