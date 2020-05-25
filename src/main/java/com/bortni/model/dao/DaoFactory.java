package com.bortni.model.dao;

import com.bortni.model.dao.implementation.DaoFactoryImpl;
import com.bortni.model.entity.Admin;
import com.bortni.model.entity.Variant;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    public abstract GameDao createGameDao();

    public abstract QuestionsDao createQuestionsDao();

    public abstract QuestionsDao createQuestionsWithVariantsDao();

    public abstract QuestionsDao createQuestionsNoVariantsDao();

    public abstract UserDao createUserDao();

    public abstract AdminDao createAdminDao();

    public abstract VariantDao createVariantDao();

    public static DaoFactory getInstance(){

        if(daoFactory == null){
            synchronized (DaoFactory.class){
                if(daoFactory == null){
                    daoFactory = new DaoFactoryImpl();
                }
            }
        }
        return daoFactory;

    }
}
