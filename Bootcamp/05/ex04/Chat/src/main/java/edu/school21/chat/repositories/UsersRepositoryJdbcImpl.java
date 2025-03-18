package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        String query = """
                WITH user_chats AS (
                    SELECT
                        u.id AS user_id,
                        u.login,
                        u.password,
                        cr.id AS created_room_id,
                        cr.name AS created_room_name,
                        cr.owner AS created_room_owner,
                        m.room AS used_room_id,
                        cr2.name AS used_room_name,
                        cr2.owner AS used_room_owner
                    FROM users u
                    LEFT JOIN chatroom cr ON u.id = cr.owner
                    LEFT JOIN messages m ON u.id = m.author
                    LEFT JOIN chatroom cr2 ON m.room = cr2.id
                )
                SELECT
                    user_id,
                    login,
                    password,
                    created_room_id,
                    created_room_name,
                    created_room_owner,
                    used_room_id,
                    used_room_name,
                    used_room_owner
                FROM user_chats
                ORDER BY user_id
                LIMIT ? OFFSET ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, size);
            statement.setInt(2, page * size);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");

                User user = new User(userId, login, password);

                Long createdRoomId = resultSet.getLong("created_room_id");
                if (!resultSet.wasNull()) {
                    String createdRoomName = resultSet.getString("created_room_name");
                    Long createdRoomOwnerId = resultSet.getLong("created_room_owner");
                    User createdRoomOwner = new User(createdRoomOwnerId, null, null);
                    Chatroom createdRoom = new Chatroom(createdRoomId, createdRoomName, createdRoomOwner, null);
                    user.getCreatedChatroom().add(createdRoom);
                }

                Long usedRoomId = resultSet.getLong("used_room_id");
                if (!resultSet.wasNull()) {
                    String usedRoomName = resultSet.getString("used_room_name");
                    Long usedRoomOwnerId = resultSet.getLong("used_room_owner");
                    User usedRoomOwner = new User(usedRoomOwnerId, null, null);
                    Chatroom usedRoom = new Chatroom(usedRoomId, usedRoomName, usedRoomOwner, null);
                    user.getUsedChatroom().add(usedRoom);
                }

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}

