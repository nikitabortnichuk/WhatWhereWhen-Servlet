package com.bortni.model.dao;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface GameDao extends Dao<Game> {
    Game findByIdentificator(String identificator);
    void saveUsersInGame(String identificator, List<User> userList);

    List<Game> findByUserId(int id);
}
