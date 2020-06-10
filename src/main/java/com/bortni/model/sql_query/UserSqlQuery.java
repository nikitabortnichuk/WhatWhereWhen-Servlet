package com.bortni.model.sql_query;

public class UserSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM users ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE users.user_id = ? ";

    public static final String SAVE = "INSERT INTO users(username, email, password) VALUES (?, ?, ?) ";

    public static final String UPDATE = "UPDATE users SET username = ?, email = ?, password = ? WHERE users.user_id = ? ";

    public static final String DELETE = "DELETE FROM users WHERE users.user_id = ? ";

    public static final String FIND_BY_USERNAME_PASSWORD = FIND_ALL + "WHERE users.username = ? AND users.password = ? ";

    public static final String FIND_BY_USERNAME = FIND_ALL + "WHERE users.username = ? ";
}
