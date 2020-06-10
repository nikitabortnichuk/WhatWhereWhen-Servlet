package com.bortni.model.dao.implementation;

import com.bortni.model.dao.VariantDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.database_mapper.AdminDatabaseMapper;
import com.bortni.model.database_mapper.VariantDatabaseMapper;
import com.bortni.model.entity.Admin;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.sql_query.QuestionsSqlQuery;
import com.bortni.model.sql_query.VariantSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VariantsDaoImpl implements VariantDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantsDaoImpl.class);

    private Connection connection;
    private VariantDatabaseMapper variantMapper;
    private Specification<Variant> specification;

    public VariantsDaoImpl(Connection connection) {
        this.connection = connection;
        variantMapper = new VariantDatabaseMapper();
        specification = new Specification<>(variantMapper, connection);
    }

    @Override
    public Variant save(Variant entity) {

        String sql = VariantSqlQuery.SAVE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setInt(2, entity.getQuestionId());
            preparedStatement.setBoolean(3, entity.isCorrect());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in saving user: {}", e.getMessage());
            throw new RuntimeException();
        }

        return entity;
    }

    @Override
    public void update(Variant entity) {
        String sql = VariantSqlQuery.UPDATE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setInt(2, entity.getQuestionId());
            preparedStatement.setBoolean(3, entity.isCorrect());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in updating variant: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Variant entity) {
        String sql = VariantSqlQuery.DELETE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in deleting variant: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public List<Variant> findAll() {
        String sql = VariantSqlQuery.FIND_ALL;

        return findAll(specification, sql);
    }

    @Override
    public Variant findById(int id) {
        String sql = VariantSqlQuery.FIND_BY_ID;

        return findById(specification, sql, id);
    }

    @Override
    public List<Variant> findVariantsByQuestionId(Question question) {
        String sql = VariantSqlQuery.FIND_VARIANTS_BY_QUESTION_ID;
        List<Variant> variantList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, question.getId());

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                variantList.add(variantMapper.getFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding variants: {}", e.getMessage());
            throw new RuntimeException();
        }

        return variantList;
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
