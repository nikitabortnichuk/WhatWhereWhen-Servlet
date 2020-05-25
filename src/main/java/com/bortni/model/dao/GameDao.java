package com.bortni.model.dao;

import com.bortni.model.entity.Game;

import java.util.ArrayList;
import java.util.List;

public interface GameDao extends Dao<Game> {
    Game findByIdentificator(String identificator);
}
