package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.entity.Game;

import java.util.ArrayList;
import java.util.List;

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

    public void update(Game game){
        try (GameDao gameDao = daoFactory.createGameDao()){
            gameDao.update(game);
        }
    }

    public void checkIfLessThanTwoOrThrowException(int playersNumber) {
        if(playersNumber < 2){
            throw new RuntimeException("Number of players must not be less than 2");
        }
    }

    public List<Game> findByUserId(int id) {

        List<Game> gameList;
        try(GameDao gameDao = daoFactory.createGameDao()){
            gameList = gameDao.findByUserId(id);
        }
        return gameList;
    }
}
