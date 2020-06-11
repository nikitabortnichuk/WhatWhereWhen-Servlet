package com.bortni.model.entity;


import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;

    private List<Game> gameList;

    public User(int id, String username, String email, String password, List<Game> gameList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gameList = gameList;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Game> getGameList() {
        return this.gameList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                gameList == user.gameList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, gameList);
    }

    public String toString() {
        return "User(id=" + this.getId() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", gameList=" + this.getGameList() + ")";
    }

    public static class UserBuilder {
        private int id;
        private String username;
        private String email;
        private String password;
        private List<Game> gameList;

        UserBuilder() {
        }

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder gameList(List<Game> gameList) {
            this.gameList = gameList;
            return this;
        }

        public User build() {
            return new User(id, username, email, password, gameList);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", username=" + this.username + ", email=" + this.email + ", password=" + this.password + ", gameList=" + this.gameList + ")";
        }
    }
}
