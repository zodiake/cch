package by.test;

import com.by.Application;
import com.by.form.ParkingForm;
import com.by.service.ParkingHistoryService;
import com.by.service.ParkingPayService;
import com.by.service.ParkingWithNoMemberHistoryService;
import org.junit.Before;
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
 * Created by yagamai on 15-11-25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class ParkingHistoryServiceTest {
    @Autowired
    private ParkingHistoryService service;
    @Autowired
    private ParkingWithNoMemberHistoryService noMemberHistoryService;
    @Autowired
    private ParkingPayService parkingPayService;

    private Calendar startTime = Calendar.getInstance();

    private Calendar endTime = Calendar.getInstance();

    @Before
    public void init() {
        startTime.set(2015, 10, 25, 13, 0, 22);
        endTime.set(2015, 10, 25, 15, 0, 22);
    }

    @Test
    public void bindLicenseIfLicenseNotExist() {
        ParkingForm form = new ParkingForm();
        form.setLicense("沪A12345");
        form.setStartTime(startTime);
        form.setEndTime(endTime);
        service.bindLicenseWithMember(form);
        assertEquals(1, noMemberHistoryService.findByLicense("沪A12345").size());
        assertEquals(0, service.findByLicense("沪A12345").size());
    }

    @Test
    public void bindLicenseIfLicenseExistButNoPayRecord() {
        ParkingForm form = new ParkingForm();
        form.setLicense("沪A54321");
        startTime.set(2015, 10, 25, 16, 0, 22);
        endTime.set(2015, 10, 25, 17, 0, 22);
        form.setStartTime(startTime);
        form.setEndTime(endTime);
        service.bindLicenseWithMember(form);
        assertEquals(1, noMemberHistoryService.findByLicense("沪A54321").size());
        assertEquals(0, service.findByLicense("沪A54321").size());
    }

    @Test
    public void bindLicenseIfLicenseExistHavingPayRecord() {
        ParkingForm form = new ParkingForm();
        form.setLicense("沪A54321");
        form.setStartTime(startTime);
        form.setEndTime(endTime);
        service.bindLicenseWithMember(form);
        assertEquals(1, parkingPayService.findByLicenseAndCreatedTimeBetween("沪A54321", startTime, endTime).size());
        assertEquals(1, service.findByLicense("沪A54321").size());
    }
}
