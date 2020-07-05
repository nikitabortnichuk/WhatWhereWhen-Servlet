package com.bortni.model.entity;

import java.util.Objects;

public class Admin {

    private int id;
    private String login;
    private String password;

    public Admin() {
    }

    public Admin(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static AdminBuilder builder(){
        return new AdminBuilder();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id &&
                login.equals(admin.login) &&
                password.equals(admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class AdminBuilder {
        private int id;
        private String login;
        private String password;

        public AdminBuilder id(int id){
            this.id = id;
            return this;
        }

        public AdminBuilder login(String login){
            this.login = login;
            return this;
        }

        public AdminBuilder password(String password){
            this.password = password;
            return this;
        }

        public Admin build(){
            return new Admin(id, login, password);
        }
    }
}
