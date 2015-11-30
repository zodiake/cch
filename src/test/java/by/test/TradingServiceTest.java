package by.test;

import com.by.Application;
import com.by.model.Member;
import com.by.model.Trading;
import com.by.service.TradingService;
import org.hibernate.envers.Audited;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by yagamai on 15-11-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class TradingServiceTest {
    @Autowired
    private TradingService tradingService;

    @Test
    public void tradeToScoreTest() {
        Member m = new Member(1l);
        Trading trading = new Trading();
        trading.setAmount(1000.0);
        trading.setMember(m);
        tradingService.tradeToScore(trading);
    }
}
