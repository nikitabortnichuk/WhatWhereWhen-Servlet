package com.bortni.model.database_mapper;

import com.bortni.model.entity.Variant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariantMapper implements DatabaseMapper<Variant> {
    @Override
    public Variant getFromResultSet(ResultSet resultSet) throws SQLException {
        return Variant.builder()
                .id(resultSet.getInt(1))
                .text(resultSet.getString(2))
                .questionId(resultSet.getInt(3))
                .isCorrect(resultSet.getBoolean(4))
                .build();
    }
}
