package com.bortni.model.dao.implementation;

import com.bortni.model.dao.AdminDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.Admin;
import com.bortni.model.database_mapper.AdminDatabaseMapper;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.exception.MySqlException;
import com.bortni.model.sql_query.AdminSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    private Connection connection;
    private AdminDatabaseMapper adminMapper;
    private Specification<Admin> specification;

    public AdminDaoImpl(Connection connection){
        this.connection = connection;
        adminMapper = new AdminDatabaseMapper();
        specification = new Specification<>(adminMapper, connection);
    }

    @Override
    public Admin save(Admin entity) {
        String sql = AdminSqlQuery.SAVE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Sql error in saving admin: {}", e.getMessage());
            throw new MySqlException("Sql error in saving admin", e);
        }

        return entity;
    }

    @Override
    public void update(Admin entity) {
        String sql = AdminSqlQuery.UPDATE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Sql error in updating admin: {}", e.getMessage());
            throw new MySqlException("Sql error in updating admin", e);
        }
    }

    @Override
    public void delete(Admin entity) {
        String sql = AdminSqlQuery.DELETE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Sql error in deleting admin: {}", e.getMessage());
            throw new MySqlException("Sql error in deleting admin", e);
        }

    }

    @Override
    public List<Admin> findAll() {
        String sql = AdminSqlQuery.FIND_ALL;

        return findAll(specification, sql);
    }

    @Override
    public Admin findById(int id) {
        String sql = AdminSqlQuery.FIND_BY_ID;

        return findById(specification, sql, id);
    }

    @Override
    public Admin findByLoginAndPassword(String login, String password) {
        String sql = AdminSqlQuery.FIND_BY_LOGIN_AND_PASSWORD;

        Admin admin;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                admin = adminMapper.getFromResultSet(resultSet);
            }
            else {
                LOGGER.info("No admin found");
                throw new EntityNotFoundException("admin");
            }
        } catch (SQLException e) {
            LOGGER.error("Sql error in finding admin by login and password: {}", e.getMessage());
            throw new MySqlException("Sql error in finding admin by login and password", e);
        }
        return admin;
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
