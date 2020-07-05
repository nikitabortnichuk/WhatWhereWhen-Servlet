package com.bortni.model.dao.implementation;

import com.bortni.model.dao.UserDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.User;
import com.bortni.model.database_mapper.UserDatabaseMapper;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.exception.MySqlException;
import com.bortni.model.sql_query.UserSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

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

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in saving user: {}", e.getMessage());
            throw new MySqlException("Sql error in saving user", e);
        }

        return entity;
    }

    @Override
    public void update(User entity) {
        String sql = UserSqlQuery.UPDATE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in updating user: {}", e.getMessage());
            throw new MySqlException("Sql error in updating user", e);
        }

    }

    @Override
    public void delete(User entity) {
        String sql = UserSqlQuery.DELETE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in deleting user: {}", e.getMessage());
            throw new MySqlException("Sql error in deleting user", e);
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
    public User findByUsernameAndPassword(String username, String password) {
        String sql = UserSqlQuery.FIND_BY_USERNAME_PASSWORD;

        User user;
        try(PreparedStatement preparedStatement = specification.getConnection().prepareStatement(sql)){

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new UserDatabaseMapper().getFromResultSet(resultSet);
            }
            else {
                LOGGER.info("No users found");
                throw new EntityNotFoundException("user");
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding user: {}", e.getMessage());
            throw new MySqlException("Sql error in finding user", e);
        }

        return user;
    }

    @Override
    public boolean isUsernameExist(String username) {
        String sql = UserSqlQuery.FIND_BY_USERNAME;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            final ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            LOGGER.error("Sql error in checking is user exist: {}", e.getMessage());
            throw new MySqlException("Sql error in checking is user exist", e);

        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = UserSqlQuery.FIND_BY_USERNAME;

        User user;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new UserDatabaseMapper().getFromResultSet(resultSet);
            }
            else {
                LOGGER.info("No users found");
                throw new EntityNotFoundException("user");
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding user: {}", e.getMessage());
            throw new MySqlException("Sql error in finding user", e);
        }
        return user;
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error in closing connection");
            throw new MySqlException("Error in closing connection", e);
        }
    }
}
