package com.bortni.model.database_mapper;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDatabaseMapper implements DatabaseMapper<Question> {
    @Override
    public Question getFromResultSet(ResultSet resultSet) throws SQLException {
        DatabaseMapper<Question> questionDatabaseMapper;

        QuestionType type = QuestionType.valueOf(resultSet.getString("question_type"));

        switch (type){
            case NO_VARIANTS:
                questionDatabaseMapper = new QuestionWithNoVariantsDatabaseMapper();
                break;
            case WITH_VARIANTS:
                questionDatabaseMapper = new QuestionWithVariantsDatabaseMapper();
                break;
            default:
                throw new RuntimeException();
        }

        return questionDatabaseMapper.getFromResultSet(resultSet);
    }

    public List<Variant> getVariantList(ResultSet resultSet) throws SQLException {
        List<Variant> variantList = new ArrayList<>();

        while (resultSet.next()) {
            variantList.add(new VariantDatabaseMapper().getFromResultSet(resultSet));
        }

        return variantList;
    }
}
