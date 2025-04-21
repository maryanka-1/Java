package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//проверить реализацию исходя из пароля
@Component("jdbcBean")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Statement st = dataSource.getConnection().createStatement()) {
            st.executeQuery("select * from users where email = '" + email + "'");
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getLong("id"),
                        rs.getString("email")
                ));
            }else return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) return Optional.empty();
        try (Statement st = dataSource.getConnection().createStatement()) {
            st.executeQuery("SELECT * FROM users WHERE id = " + id);
            ResultSet rs = st.getResultSet();
            if (!rs.next()) return Optional.empty();
            return Optional.of(new User(
                    rs.getLong("id"),
                    rs.getString("email")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Statement st = dataSource.getConnection().createStatement()) {
            st.executeQuery("SELECT * FROM users");
            ResultSet rs = st.getResultSet();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("email")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User entity) {
        try (PreparedStatement pr = dataSource.getConnection().prepareStatement(
                "INSERT INTO users (email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, entity.getEmail());
            pr.setString(2, entity.getPassword());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            } else {
                throw new SQLException("Failed to insert user");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save user", e);
        }

    }

    @Override
    public void update(User entity) {
        if (entity != null) {
            try (PreparedStatement pr = dataSource.getConnection().prepareStatement(
                    "UPDATE users SET email=? WHERE id=?")) {
                pr.setString(1, entity.getEmail());
                pr.setLong(2, entity.getId());
                pr.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot update user", e);
            }
        }

    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            try (Statement st = dataSource.getConnection().createStatement()) {
                st.executeUpdate("DELETE FROM users WHERE id=" + id);
            } catch (SQLException e) {
                throw new RuntimeException("Cannot delete user", e);
            }
        }
    }
}
