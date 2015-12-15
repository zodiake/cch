package by.test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.by.Application;
import com.by.model.CardRule;
import com.by.model.Rule;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class RuleServiceTest {
	@Autowired
	private RuleService service;

	@Test
	public void getMaxScore() {
		List<Rule> rules = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Rule r = new CardRule();
			r.setValid(ValidEnum.VALID);
			r.setScore(i);
			rules.add(r);
		}
		assertEquals(10, service.getMaxScore(rules));
	}

	@Test
	public void getMaxScoreWithTime() {
		List<Rule> rules = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Rule r = new CardRule();
			r.setValid(ValidEnum.VALID);
			r.setScore(i);
			rules.add(r);
		}
		Rule r = new CardRule();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		r.setBeginTime(yesterday);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		r.setEndTime(tomorrow);
		r.setScore(20);
		r.setValid(ValidEnum.VALID);
		rules.add(r);
		assertEquals(20, service.getMaxScore(rules));
	}
}
