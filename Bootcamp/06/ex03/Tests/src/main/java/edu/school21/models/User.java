package edu.school21.models;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private boolean statusAuthenticate;

    public User(int id, String login, String password, boolean statusAuthenticate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.statusAuthenticate = statusAuthenticate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatusAuthenticate() {
        return statusAuthenticate;
    }

    public void setStatusAuthenticate(boolean statusAuthenticate) {
        this.statusAuthenticate = statusAuthenticate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && statusAuthenticate == user.statusAuthenticate && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, statusAuthenticate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", statusAuthenticate=" + statusAuthenticate +
                '}';
    }
}
