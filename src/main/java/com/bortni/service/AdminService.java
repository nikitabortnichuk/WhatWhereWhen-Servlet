package com.bortni.service;

import com.bortni.model.dao.AdminDao;
import com.bortni.model.dao.DaoFactory;
import com.bortni.model.entity.Admin;

public class AdminService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public Admin getAdministratorByLoginAndPassword(String login, String password){
        Admin admin;
        try (AdminDao administratorDao = daoFactory.createAdminDao()){
            admin = administratorDao.findByLoginAndPassword(login, password);
        }
        return admin;
    }
}
