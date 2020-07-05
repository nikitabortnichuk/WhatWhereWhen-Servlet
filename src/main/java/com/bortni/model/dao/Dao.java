package com.bortni.model.dao;

import com.bortni.model.dao.specification.Specification;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.exception.MySqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Dao<T> extends AutoCloseable{

    Logger LOGGER = LoggerFactory.getLogger(Dao.class);

    T save(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();

    T findById(int id);

    default List<T> findAll(Specification<T> specification, String sql){
        List<T> entityList = new ArrayList<>();

        try (PreparedStatement preparedStatement = specification.getConnection().prepareStatement(sql)){

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                entityList.add(specification.getDatabaseMapper().getFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding all entities");
            throw new MySqlException("Sql error in finding all entities", e);
        }
        return entityList;
    }

    default T findById(Specification<T> specification, String sql, int id){
        T t ;
        try(PreparedStatement preparedStatement = specification.getConnection().prepareStatement(sql)){

            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                t = specification.getDatabaseMapper().getFromResultSet(resultSet);
            }
            else {
                LOGGER.info("No entity found");
                throw new EntityNotFoundException("entitie");
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding entity by id");
            throw new MySqlException("Sql error in finding entity by id", e);
        }

        return t;
    }

    void close();

}
