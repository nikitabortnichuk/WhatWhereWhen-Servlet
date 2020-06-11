package com.bortni.service;

import com.bortni.model.dao.AdminDao;
import com.bortni.model.dao.DaoFactory;
import com.bortni.model.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public Admin getAdministratorByLoginAndPassword(String login, String password){
        Admin admin;
        try (AdminDao administratorDao = daoFactory.createAdminDao()){
            admin = administratorDao.findByLoginAndPassword(login, password);
        }
        LOGGER.debug("Get admin by login and password");
        return admin;
    }
}
