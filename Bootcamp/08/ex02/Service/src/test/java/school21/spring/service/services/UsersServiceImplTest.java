package school21.spring.service.services;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UsersServiceImplTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    private UsersServiceImpl usersService = (UsersServiceImpl)applicationContext.getBean("userServiceBean");

    @Test
    public void testFailedSingUp(){
//        System.out.println(usersService.signUp("mary@mail.ru"));
        assertNull(usersService.signUp("mary@mail.ru"));

    }

    @Test
    public void testSucceededSingUp(){
        assertNotNull(usersService.signUp("mamamamam@mail.ru"));

    }
}