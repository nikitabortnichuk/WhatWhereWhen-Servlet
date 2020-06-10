package com.bortni.model.dao;

import com.bortni.model.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {

    User findByUsernameAndPassword(String username, String password);

    boolean isUsernameExist(String username);

    User findByUsername(String username);
}
