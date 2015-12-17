package by.test;

import com.by.Application;
import com.by.exception.TradingAlreadyBindException;
import com.by.json.TradingRequestJson;
import com.by.model.Member;
import com.by.model.Trading;
import com.by.service.MemberService;
import com.by.service.ScoreAddHistoryService;
import com.by.service.TradingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
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
    @Autowired
    private MemberService memberService;
    @Autowired
    private ScoreAddHistoryService scoreAddHistoryService;

    @Before
    public void init() {
        Member m = new Member(1l);
        m.setScore(110);
    }

    @Test
    public void tradeToScoreTest() {
        Member m = new Member(1l);
        Trading trading = new Trading();
        trading.setAmount(1000.0);
        trading.setMember(m);
        tradingService.tradeToScore(trading);
    }

    @Test(expected = TradingAlreadyBindException.class)
    public void bindMemberTestTradingAlreadyBindException() {
        Trading t = new Trading();
        t.setId(1l);
        t.setCode("321");
        tradingService.bindMember(t, "13611738422");
    }

    @Test
    public void bindMemberTest() {
        Trading t = new Trading();
        t.setId(1l);
        t.setCode("123");
        t.setAmount(100.0);
        tradingService.bindMember(t, "13611738422");
        Optional<Member> member = memberService.findByName("13611738422");
        assertEquals(110 + 100 * 2 + 100, member.get().getScore());
        assertEquals(5, scoreAddHistoryService.findByMember(new Member(1l)).size());
    }

    @Test
    public void tradeSaveWithOutMember() {
        Trading t = new Trading();
        t.setCode("asdf");
        t.setAmount(100.0);
        Trading trading = tradingService.save(t);
        assertNotNull(trading.getId());
    }

    @Test
    public void tradeSaveWithMember() {
        TradingRequestJson t = new TradingRequestJson();
        t.setMobile("13611738422");
        t.setShopKey("abc");
        tradingService.save(t);
        tradingService.save(t);
        Optional<Member> member = memberService.findByName("13611738422");
        assertEquals(110 + 100 * 2 + 100, member.get().getScore());
        assertEquals(5, scoreAddHistoryService.findByMember(new Member(1l)).size());
    }
}
