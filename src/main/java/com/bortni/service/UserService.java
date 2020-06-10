package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.UserDao;
import com.bortni.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public User findByUsernameAndPassword(String username, String password){
        User user;
        try (UserDao userDao = daoFactory.createUserDao()){
            user = userDao.findByUsernameAndPassword(username, password);
        }
        LOGGER.info("Finding user by username and password");
        return user;
    }

    public void save(User user) {
        try(UserDao userDao = daoFactory.createUserDao()){
            userDao.save(user);
        }
        LOGGER.info("Saving user");
    }

    public User findByUsername(String username) {
        User user;
        try(UserDao userDao = daoFactory.createUserDao()){
            user = userDao.findByUsername(username);
        }
        LOGGER.info("Finding user by username");
        return user;
    }
}
