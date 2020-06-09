package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.UserDao;
import com.bortni.model.entity.User;

public class UserService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public User findByUsernameAndPassword(String username, String password){
        User user;
        try (UserDao userDao = daoFactory.createUserDao()){
            user = userDao.findByUsernameAndPassword(username, password);
        }
        return user;
    }

    public boolean isUsernameExist(String username) {
        boolean isExist;
        try (UserDao userDao = daoFactory.createUserDao()){
            isExist = userDao.isUsernameExist(username);
        }
        return isExist;
    }

    public void save(User user) {
        try(UserDao userDao = daoFactory.createUserDao()){
            userDao.save(user);
        }
    }
}
