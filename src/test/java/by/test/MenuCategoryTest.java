package by.test;

import com.by.Application;
import com.by.model.Menu;
import com.by.model.MenuCategory;
import com.by.model.User;
import com.by.service.MenuCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by yagamai on 15-12-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class MenuCategoryTest {
    @Autowired
    private MenuCategoryService service;

    @Test
    public void menuCountTest() {
        User u = new User(1, "tom");
        Map<MenuCategory, List<Menu>> results = service.getCategoryAndMenu(u);
        assertEquals(3, results.size());
    }
}
