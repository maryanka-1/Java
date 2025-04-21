package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
// проверить реализации исходя из параметра - пароль
@Component("jdbcTemplateBean")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        userRowMapper= (rs, rowNum) -> {
            return new User(rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"));
        };
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> list = jdbcTemplate.query("select * from users where email = '" + email + "'", userRowMapper);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE id = " + id,userRowMapper);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", userRowMapper);
    }

    @Override
    public void save(User entity) {
        if(entity!=null) {
            jdbcTemplate.update("INSERT INTO users (email, password) VALUES (?, ?)", entity.getEmail(), entity.getPassword());
            List<User> temp = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", userRowMapper, entity.getEmail());
            entity.setId(temp.getFirst().getId());
        }
    }

    @Override
    public void update(User entity) {
        if(entity != null) jdbcTemplate.update("UPDATE user SET email=? WHERE id=?", entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        if(id!=null) jdbcTemplate.update("delete from users where id = " + id);
    }
}
