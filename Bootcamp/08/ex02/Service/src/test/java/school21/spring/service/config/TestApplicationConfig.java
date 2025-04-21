package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("postgres")
                .addScript("data.sql")
                .build();
    }
//
//    @Bean("usersJdbcBean")
//    public UsersRepositoryJdbcImpl getUsersRepositoryJdbcImpl() {
//        return new UsersRepositoryJdbcImpl(getDataSource());
//    }

    @Bean("usersJdbcTemplateBean")
    public UsersRepositoryJdbcTemplateImpl getUsersRepositoryJdbcTemplateImpl() {
        return new UsersRepositoryJdbcTemplateImpl(getDataSource());
    }

    @Bean("userServiceBean")
    public UsersServiceImpl getUsersServiceImpl() {
        return new UsersServiceImpl(getUsersRepositoryJdbcTemplateImpl());
    }
}
