package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
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

                long messageId = resultSet.getLong("id");
                String text = resultSet.getString("text");
                LocalDateTime datetime = resultSet.getTimestamp("datetime").toLocalDateTime();

                long authorId = resultSet.getLong("author");
                User author = findUserById(authorId);

                int roomId = resultSet.getInt("room");
                Chatroom room = findChatroomById(roomId);

                return Optional.of(new Message(messageId, author, room, text, datetime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        String query = "INSERT INTO messages (author, room, text, datetime) VALUES (?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getChat().getId());
            statement.setString(3, message.getMessage());
            statement.setTimestamp(4, Timestamp.valueOf(message.getTime()));
            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new NotSavedSubEntityException("Failed to assign ID.");
                }
            }
        } catch (SQLException| NullPointerException e) {
            throw new NotSavedSubEntityException("Failed to save message: " + e.getMessage());
        }
    }

    private User findUserById(long userId) throws SQLException {
        String query = "SELECT * FROM \"users\" WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }
        }
        return null;
    }

    private Chatroom findChatroomById(long roomId) throws SQLException {
        String query = "SELECT * FROM chatroom WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Chatroom(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        findUserById(resultSet.getLong("owner")),
                        null // Список сообщений можно заполнить позже
                );
            }
        }
        return null;
    }

}
