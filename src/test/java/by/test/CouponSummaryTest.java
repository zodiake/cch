package by.test;

import com.by.Application;
import com.by.model.CouponSummary;
import com.by.service.CouponService;
import com.by.service.CouponSummaryService;
import com.by.typeEnum.ValidEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

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

    @Test
    public void findByValidAndTimeInToday() {
        Calendar day = Calendar.getInstance();
        day.set(2015, 11, 2);
        List<CouponSummary> lists = summaryService.findByValidAndTimeInToday(ValidEnum.VALID, day);
        assertEquals(1, lists.size());
    }

    @Test
    public void findBySummary() {
        Long count = service.countBySummaryWhereMemberIsNull(new CouponSummary(1l));
        assertEquals(Long.valueOf(1l), count);
    }
}
