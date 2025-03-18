package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DatabaseInitializer;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {


        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);

        DatabaseInitializer initializer = new DatabaseInitializer(ds);
        initializer.initialize();

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter message ID");
        Long id = null;
        while (true) {
            if (sc.hasNextLong()) {
                id = sc.nextLong();
                break;
            } else {
                System.out.println("Invalid input. You must enter a number.");
                sc.next();
            }
        }

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
        Optional<Message> byId = messagesRepository.findById(id);
        if (byId.isPresent()) {
            System.out.println(byId.get());
        } else {
            System.out.println("Message not found");
        }

    }
}
