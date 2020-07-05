package com.bortni.model.database_mapper;

import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionWithNoVariantsDatabaseMapper implements DatabaseMapper<Question> {
    @Override
    public Question getFromResultSet(ResultSet resultSet) throws SQLException {

        Question question = new Question();
        question.setId(resultSet.getInt(1));
        question.setQuestionText(resultSet.getString(2));
        question.setQuestionType(QuestionType.valueOf(resultSet.getString(3)));
        question.setAnswer(resultSet.getString(4));

        return question;
    }
}
