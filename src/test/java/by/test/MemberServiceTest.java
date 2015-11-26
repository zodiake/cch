package by.test;

import com.by.Application;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.service.MemberService;
import com.by.service.ScoreAddHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by yagamai on 15-11-25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class MemberServiceTest {
    @Autowired
    private MemberService service;
    @Autowired
    private ScoreAddHistoryService scoreAddHistoryService;

    private List<ScoreAddHistory> results = new ArrayList<>();

    @Before
    public void before() {
        results = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ScoreAddHistory h = new ScoreAddHistory();
            h.setId(Long.valueOf(i));
            h.setTotal(i);
            results.add(h);
        }
    }

    @Test
    public void extractScoreHistory() {
        List<ScoreAddHistory> result1 = service.extractScoreHistory(results, 20);
        assertEquals(6, result1.size());
        List<ScoreAddHistory> result2 = service.extractScoreHistory(results, 3);
        assertEquals(2, result2.size());
    }

    @Test
    public void useHistory() {
        service.useScore(new Member(1l), 5);
        List<ScoreAddHistory> histories = scoreAddHistoryService.findByMember(new Member(1l));
        assertEquals(2, histories.size());
        assertEquals(1, histories.get(0).getTotal());
        Member m = service.findById(1l).get();
        assertEquals(105, m.getScore());
    }
}
