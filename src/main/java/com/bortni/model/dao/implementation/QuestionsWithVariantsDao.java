package com.bortni.model.dao.implementation;

import com.bortni.model.entity.question.Question;
import com.bortni.model.sql_query.QuestionsSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionsWithVariantsDao extends QuestionsDaoImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsWithVariantsDao.class);

    public QuestionsWithVariantsDao(Connection connection) {
        super(connection);
    }

    @Override
    public Question save(Question entity) {

        String sql = QuestionsSqlQuery.SAVE_WITH_VARIANTS;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getQuestionText());
            preparedStatement.setString(2, entity.getQuestionType().name());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                entity.setId(id);
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in saving question with variants: {}", e.getMessage());
            throw new RuntimeException();
        }
        return entity;
    }

    @Override
    public void update(Question entity) {
        String sql = QuestionsSqlQuery.UPDATE_WITH_VARIANTS;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getQuestionText());
            preparedStatement.setString(2, entity.getQuestionType().name());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in updating question with variants: {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
