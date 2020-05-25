package com.bortni.model.database_mapper;

import com.bortni.model.entity.Variant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariantDatabaseMapper implements DatabaseMapper<Variant> {
    @Override
    public Variant getFromResultSet(ResultSet resultSet) throws SQLException {
        return Variant.builder()
                .id(resultSet.getInt(1))
                .text(resultSet.getString(2))
                .isCorrect(resultSet.getBoolean("is_correct"))
                .build();
    }
}
