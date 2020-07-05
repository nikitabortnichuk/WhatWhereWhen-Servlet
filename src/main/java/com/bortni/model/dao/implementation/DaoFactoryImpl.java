package com.bortni.model.dao.implementation;

import com.bortni.model.dao.AdminDao;
import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.UserDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.database.ConnectionPoolHolder;
import com.bortni.model.exception.MySqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactoryImpl extends DaoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactoryImpl.class);

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public GameDao createGameDao() {
        return new GameDaoImpl(getConnection());
    }

    @Override
    public QuestionsDao createQuestionsDao() {
        return new QuestionsDaoImpl(getConnection());
    }

    @Override
    public QuestionsDao createQuestionsWithVariantsDao() {
        return new QuestionsWithVariantsDao(getConnection());
    }

    @Override
    public QuestionsDao createQuestionsNoVariantsDao() {
        return new QuestionsWithNoVariantsDao(getConnection());
    }


    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl(getConnection());
    }

    @Override
    public AdminDao createAdminDao() {
        return new AdminDaoImpl(getConnection());
    }

    @Override
    public VariantDao createVariantDao() {
        return new VariantsDaoImpl(getConnection());
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Error in creating connection");
            throw new MySqlException("Error in creating connection", e);
        }
    }


}
