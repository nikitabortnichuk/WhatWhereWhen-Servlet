package com.bortni.model.dao.implementation;

import com.bortni.model.dao.UserDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.User;
import com.bortni.model.database_mapper.UserDatabaseMapper;
import com.bortni.model.sql_query.UserSqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final Connection connection;
    private Specification<User> specification;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
        UserDatabaseMapper userMapper = new UserDatabaseMapper();
        specification = new Specification<>(userMapper, connection);
    }

    @Override
    public User save(User entity) {
        String sql = UserSqlQuery.SAVE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, entity.getNickname());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }

        return entity;
    }

    @Override
    public void update(User entity) {
        String sql = UserSqlQuery.UPDATE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, entity.getNickname());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }

    }

    @Override
    public void delete(User entity) {
        String sql = UserSqlQuery.DELETE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }

    }

    @Override
    public List<User> findAll() {
        String sql = UserSqlQuery.FIND_ALL;

        return findAll(specification, sql);
    }

    @Override
    public User findById(int id) {
        String sql = UserSqlQuery.FIND_BY_ID;

        return findById(specification, sql, id);
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }
    }
}
