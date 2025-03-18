package edu.school21.chat.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom chat;
    private String message;
    private LocalDateTime time;

    public Message() {};

    public Message(Long id, User author, Chatroom chat, String message, LocalDateTime time) {
        this.id = id;
        this.author = author;
        this.chat = chat;
        this.message = message;
        this.time = time;
    }



    public long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getChat() {
        return chat;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(author, message.author) && Objects.equals(chat, message.chat) && Objects.equals(message, message.message) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chat, message, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author.getLogin() +
                ", chat=" + chat +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
