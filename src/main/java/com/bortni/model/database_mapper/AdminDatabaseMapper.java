package com.bortni.model.database_mapper;

import com.bortni.model.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDatabaseMapper implements DatabaseMapper<Admin> {

    @Override
    public Admin getFromResultSet(ResultSet resultSet) throws SQLException {
        return Admin.builder()
                .id(resultSet.getInt(1))
                .login(resultSet.getString(2))
                .password(resultSet.getString(3))
                .build();
    }
}
