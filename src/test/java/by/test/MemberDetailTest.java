package by.test;

import com.by.Application;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.service.MemberDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by yagamai on 15-11-24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class MemberDetailTest {
    @Autowired
    private MemberDetailService service;

    @Test
    public void auditTest() {
        MemberDetail detail = new MemberDetail();
        detail.setMember(new Member(2l));
        detail.setRealName("tom");
        service.save(detail);
        List<MemberDetail> audits = service.findAllAuditRevision(2l);
        assertEquals(1, audits.size());
    }

    @Test
    public void auditUpdateTest() {
        Optional<MemberDetail> d = service.findByMember(new Member(1l));
        MemberDetail detail = d.get();
        detail.setUpdatedBy("asd");
        service.update(detail);
        assertEquals("asd", service.findAuditByRevision(detail.getId(), 2).getUpdatedBy());
    }
}
