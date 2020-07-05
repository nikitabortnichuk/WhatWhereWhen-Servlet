package com.bortni.model.dao.implementation;

import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.database_mapper.QuestionDatabaseMapper;
import com.bortni.model.exception.MySqlException;
import com.bortni.model.sql_query.QuestionsSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            throw new MySqlException("Sql error in deleting question", e);
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
            LOGGER.error("Error in closing connection");
            throw new MySqlException("Error in closing connection", e);
        }
    }

    @Override
    public List<Question> findAll(long from, long to) {
        String sql = QuestionsSqlQuery.FIND_ALL_PAGINATED;
        List<Question> entityList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setLong(1, from);
            preparedStatement.setLong(2, to);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                entityList.add(new QuestionDatabaseMapper().getFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding all questions paginated");
            throw new MySqlException("Sql error in finding all questions paginated", e);
        }
        return entityList;
    }

    @Override
    public long getQuestionsCount() {
        String sql = QuestionsSqlQuery.COUNT_ALL;

        long count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in counting questions");
            throw new MySqlException("Sql error in counting questions", e);
        }
        return count;
    }
}
