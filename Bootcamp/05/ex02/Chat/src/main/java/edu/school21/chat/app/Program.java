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

        User creator = new User(1L,"Maria", "123", new ArrayList(), new ArrayList());
        User author = creator;
        Chatroom room = new Chatroom(2L, "Room_Maria", creator, new ArrayList());
        Message message = new Message(null, author, room, "What`s up?", LocalDateTime.now());

        messagesRepository.save(message);
        System.out.println(message.getId());




    }
}
