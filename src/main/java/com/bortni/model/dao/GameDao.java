package com.bortni.model.dao;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface GameDao extends Dao<Game> {
    Game findByIdentificator(String identificator);
    void saveUserToGame(User user, Game game);

    List<Game> findByUserId(int id);

    List<Game> findByUserId(int id, long from, long to);

    long getGamesCountByUserId(int id);

}
