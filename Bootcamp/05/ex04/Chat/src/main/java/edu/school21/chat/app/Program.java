package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws NotSavedSubEntityException {

        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);

        DatabaseInitializer initializer = new DatabaseInitializer(ds);
        initializer.initialize();

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(ds);
        int page = 1;
        int size = 2;
        List<User> users = usersRepository.findAll(page, size);

        for (User user : users) {
            System.out.println(user);
        }
    }
}
