package com.bortni.model.dao.specification;

import com.bortni.model.database_mapper.DatabaseMapper;

import java.sql.Connection;

public class Specification<T> {

    private DatabaseMapper<T> databaseMapper;
    private Connection connection;

    public Specification(DatabaseMapper<T> databaseMapper, Connection connection) {
        this.databaseMapper = databaseMapper;
        this.connection = connection;
    }

    public DatabaseMapper<T> getDatabaseMapper() {
        return databaseMapper;
    }

    public void setDatabaseMapper(DatabaseMapper<T> databaseMapper) {
        this.databaseMapper = databaseMapper;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
