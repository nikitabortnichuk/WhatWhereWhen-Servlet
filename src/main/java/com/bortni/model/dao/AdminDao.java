package com.bortni.model.dao;

import com.bortni.model.entity.Admin;

public interface AdminDao extends Dao<Admin> {
    Admin findByLoginAndPassword(String login, String password);
}
