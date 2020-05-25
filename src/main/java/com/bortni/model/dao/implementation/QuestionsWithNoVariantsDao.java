package com.bortni.model.dao.implementation;

import com.bortni.model.entity.question.Question;
import com.bortni.model.sql_query.QuestionsSqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionsWithNoVariantsDao extends QuestionsDaoImpl {

    public QuestionsWithNoVariantsDao(Connection connection) {
        super(connection);
    }

    @Override
    public Question save(Question question) {
        String sql = QuestionsSqlQuery.SAVE_NO_VARIANTS;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, question.getQuestionType().name());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                question.setId(id);
            }

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }
        return question;
    }

    @Override
    public void update(Question question) {
        String sql = QuestionsSqlQuery.UPDATE_NO_VARIANTS;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getAnswer());
            preparedStatement.setString(3, question.getQuestionType().name());
            preparedStatement.setInt(4, question.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }
    }
}
