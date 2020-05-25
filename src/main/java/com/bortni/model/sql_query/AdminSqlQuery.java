package com.bortni.model.sql_query;

public class AdminSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM admins ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE admins.admin_id = ? ";

    public static final String FIND_BY_LOGIN_AND_PASSWORD = FIND_ALL + "WHERE login = ? AND password = ? ";

    public static final String SAVE = "INSERT INTO admins(login, password) VALUES (?, ?) ";

    public static final String UPDATE = "UPDATE admins SET login = ?, password = ? WHERE admins.admin_id = ? ";

    public static final String DELETE = "DELETE FROM admins WHERE admins.admin_id = ? ";

}
