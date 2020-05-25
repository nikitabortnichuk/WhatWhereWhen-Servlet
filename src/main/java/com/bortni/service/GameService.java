package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.entity.Game;

public class GameService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public void save(Game game) {
        try (GameDao gameDao = daoFactory.createGameDao()){
            gameDao.save(game);
        }
    }

    public Game findByIdent(String identificator){
        try (GameDao gameDao = daoFactory.createGameDao()){
            return gameDao.findByIdentificator(identificator);
        }
    }
}
