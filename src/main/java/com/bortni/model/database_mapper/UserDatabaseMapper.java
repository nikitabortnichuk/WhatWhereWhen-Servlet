package com.bortni.model.database_mapper;

import com.bortni.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabaseMapper implements DatabaseMapper<User> {

    @Override
    public User getFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(1))
                .username(resultSet.getString(2))
                .email(resultSet.getString(3))
                .password(resultSet.getString(4))
                .build();

    }
}
