package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public void save(Game game) {
        try (GameDao gameDao = daoFactory.createGameDao()){
            gameDao.save(game);
        }
        LOGGER.info("Saving game");
    }

    public Game findByIdent(String identificator){
        Game game;
        try (GameDao gameDao = daoFactory.createGameDao()){
            game = gameDao.findByIdentificator(identificator);
        }
        LOGGER.info("Getting game by identificator");
        return game;
    }

    public void update(Game game){
        try (GameDao gameDao = daoFactory.createGameDao()){
            gameDao.update(game);
        }
        LOGGER.info("Updating game");
    }

    public void checkIfLessThanTwoOrThrowException(int playersNumber) {
        if(playersNumber < 2){
            LOGGER.warn("Number of players is less than two while creating a game");
            throw new RuntimeException("Number of players must not be less than 2");
        }
    }

    public List<Game> findByUserId(int id) {

        List<Game> gameList;
        try(GameDao gameDao = daoFactory.createGameDao()){
            gameList = gameDao.findByUserId(id);
        }
        LOGGER.info("Getting gameList by user");
        return gameList;

    }

    public void saveUserToGame(User user, Game game) {
        try(GameDao gameDao = daoFactory.createGameDao()){
            gameDao.saveUserToGame(user, game);
        }
        LOGGER.info("Saving user to game");
    }

    public List<Game> findByUserId(int id, long from, long to) {
        List<Game> gameList;
        try(GameDao gameDao = daoFactory.createGameDao()){
            gameList = gameDao.findByUserId(id, from, to);
        }
        LOGGER.info("Getting gameList by user paginated");
        return gameList;
    }

    public long getGamesCountByUserId(int userId) {
        try(GameDao gameDao = daoFactory.createGameDao()) {
            return gameDao.getGamesCountByUserId(userId);
        }
    }
}
