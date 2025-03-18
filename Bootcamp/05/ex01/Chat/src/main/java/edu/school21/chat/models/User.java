package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public final class User {
    private final int id;
    private final String login;
    private final String password;
    private List<Chatroom> createdChatroom;
    private List<Chatroom> usedChatroom;

    public User(int id, String login, String password, List<Chatroom> createdChatroom, List<Chatroom> usedChatroom) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatroom = createdChatroom;
        this.usedChatroom = usedChatroom;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(createdChatroom, user.createdChatroom) &&
                Objects.equals(usedChatroom, user.usedChatroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdChatroom, usedChatroom);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdChatroom=" + createdChatroom +
                ", usedChatroom=" + usedChatroom +
                '}';
    }
}
