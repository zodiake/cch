package by.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.by.Application;
import com.by.utils.UpYun;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class UploadTest {
	@Autowired
	private UpYun cloud;

	@Test
	public void upload() throws IOException {
		File file = new File("/home/yagamai/Pictures/Screenshot from 2015-10-15 11:22:18.png");
		boolean result3 = cloud.writeFile("/ad.txt", file, true);
		assertEquals(true,result3);
	}
}
