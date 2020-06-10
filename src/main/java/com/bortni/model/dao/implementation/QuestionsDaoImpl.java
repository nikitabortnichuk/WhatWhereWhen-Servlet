package com.bortni.model.dao.implementation;

import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.database_mapper.QuestionDatabaseMapper;
import com.bortni.model.sql_query.QuestionsSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionsDaoImpl implements QuestionsDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsDaoImpl.class);

    Specification<Question> specification;
    protected final Connection connection;
    QuestionDatabaseMapper questionMapper;

    public QuestionsDaoImpl(Connection connection) {
        questionMapper = new QuestionDatabaseMapper();
        this.connection = connection;
        specification = new Specification<>(questionMapper, connection);
    }

    @Override
    public Question save(Question entity) {
        return entity;
    }

    @Override
    public void update(Question entity) {

    }

    @Override
    public void delete(Question entity) {

        String sql = QuestionsSqlQuery.DELETE;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Sql error in deleting question: {}", e.getMessage());
            throw new RuntimeException();
        }

    }

    @Override
    public List<Question> findAll() {
        String sql = QuestionsSqlQuery.FIND_ALL_DESC;
        return findAll(specification, sql);
    }

    @Override
    public Question findById(int id) {
        String sql = QuestionsSqlQuery.FIND_BY_ID;
        return findById(specification, sql, id);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Connection was closed");
            throw new RuntimeException();
        }
    }
}
