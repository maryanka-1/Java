package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.DatabaseInitializer;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws NotSavedSubEntityException {


        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);

        DatabaseInitializer initializer = new DatabaseInitializer(ds);
        initializer.initialize();

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
;
        Optional<Message> messageOptional = messagesRepository.findById(10L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setMessage("Bye");
            message.setTime(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
            messagesRepository.update(message);
        } else System.out.println("Message not found.");

    }
}
