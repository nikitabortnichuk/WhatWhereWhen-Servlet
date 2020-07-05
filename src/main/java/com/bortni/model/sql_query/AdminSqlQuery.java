package com.bortni.model.sql_query;

public class  AdminSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM admin ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE admin.admin_id = ? ";

    public static final String FIND_BY_LOGIN_AND_PASSWORD = FIND_ALL + "WHERE login = ? AND password = ? ";

    public static final String SAVE = "INSERT INTO admin(login, password) VALUES (?, ?) ";

    public static final String UPDATE = "UPDATE admin SET login = ?, password = ? WHERE admin.admin_id = ? ";

    public static final String DELETE = "DELETE FROM admin WHERE admin.admin_id = ? ";

}
