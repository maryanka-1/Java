package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM messages WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Получение данных о сообщении
                int messageId = resultSet.getInt("id");
                String text = resultSet.getString("text");
                Timestamp datetime = resultSet.getTimestamp("datetime");

                // Получение данных об авторе (User)
                int authorId = resultSet.getInt("author");
                User author = findUserById(authorId);

                // Получение данных о комнате (Chatroom)
                int roomId = resultSet.getInt("room");
                Chatroom room = findChatroomById(roomId);

                // Создание объекта Message
                return Optional.of(new Message(messageId, author, room, text, datetime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User findUserById(int userId) throws SQLException {
        String query = "SELECT * FROM \"users\" WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }
        }
        return null;
    }

    private Chatroom findChatroomById(int roomId) throws SQLException {
        String query = "SELECT * FROM chatroom WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Chatroom(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        findUserById(resultSet.getInt("owner")),
                        null // Список сообщений можно заполнить позже
                );
            }
        }
        return null;
    }

}
