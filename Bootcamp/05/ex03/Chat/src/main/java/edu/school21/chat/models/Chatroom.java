package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private long id;
    private String nameChat;
    private User owner;
    List<Message> messages;

    public Chatroom(long id, String nameChat, User owner, List<Message> messages) {
        this.id = id;
        this.nameChat = nameChat;
        this.owner = owner;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public String getNameChat() {
        return nameChat;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && Objects.equals(nameChat, chatroom.nameChat) && Objects.equals(owner, chatroom.owner) && Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameChat, owner, messages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", nameChat='" + nameChat + '\'' +
                ", owner=" + owner.getLogin() +
                ", messages=" + messages +
                '}';
    }
}
