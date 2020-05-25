package com.bortni.model.database_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseMapper<T> {
    T getFromResultSet(ResultSet resultSet) throws SQLException;
}
