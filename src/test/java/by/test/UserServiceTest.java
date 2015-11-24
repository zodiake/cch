package by.test;

import com.by.Application;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by yagamai on 15-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void adminMenuCountTest() {
        User u = userService.findByName("tom");
        Set<Menu> menus = userService.getMenus(u);
        assertEquals(menus.size(), 2);
    }

    @Test
    public void shopUserMenuCountTest(){
        User u = userService.findByName("mary");
        Set<Menu> menus = userService.getMenus(u);
        assertEquals(menus.size(),3);
    }
}
