package school21.spring.service.application;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        initializeDb();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService usersService = context.getBean("userServiceBean",UsersService.class);
        usersService.signUp("fafafaff@mail.ru");
        UsersRepository usersRepository = context.getBean("jdbcTemplateBean", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        context.close();

    }

    private static void initializeDb() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        try {
            DataSource ds = new HikariDataSource(config);
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            InputStream schemaStream = Main.class.getClassLoader().getResourceAsStream("./data.sql");
            if (schemaStream != null) {
                BufferedReader schemaReader = new BufferedReader(new InputStreamReader(schemaStream));
                String line;
                StringBuilder schemaSql = new StringBuilder();
                while ((line = schemaReader.readLine()) != null) {
                    schemaSql.append(line).append("\n");
                }
                statement.execute(schemaSql.toString());
            }
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
