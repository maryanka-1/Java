package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Messege {
    private int id;
    private User author;
    private Chatroom chat;
    private String message;
    private Timestamp time;

    public Messege(int id, User author, Chatroom chat, String message, Timestamp time) {
        this.id = id;
        this.author = author;
        this.chat = chat;
        this.message = message;
        this.time = time;
    }

    public int getId() {
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

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messege messege = (Messege) o;
        return id == messege.id && Objects.equals(author, messege.author) && Objects.equals(chat, messege.chat) && Objects.equals(message, messege.message) && Objects.equals(time, messege.time);
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
