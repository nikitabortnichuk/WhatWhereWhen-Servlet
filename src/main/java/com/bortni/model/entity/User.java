package com.bortni.model.entity;


import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String password;

    private List<Game> gameList;

    public User(int id, String nickname, String email, String password, List<Game> gameList) {
        this.id = id;
        this.nickname = nickname;
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

    public String getNickname() {
        return this.nickname;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
                nickname.equals(user.nickname) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                gameList.equals(user.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password, gameList);
    }

    public String toString() {
        return "User(id=" + this.getId() + ", nickname=" + this.getNickname() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", gameList=" + this.getGameList() + ")";
    }

    public static class UserBuilder {
        private int id;
        private String nickname;
        private String email;
        private String password;
        private List<Game> gameList;

        UserBuilder() {
        }

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder nickname(String nickname) {
            this.nickname = nickname;
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
            return new User(id, nickname, email, password, gameList);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", nickname=" + this.nickname + ", email=" + this.email + ", password=" + this.password + ", gameList=" + this.gameList + ")";
        }
    }
}
