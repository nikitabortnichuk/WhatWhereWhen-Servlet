package com.bortni.service;

import com.bortni.model.entity.Game;
import org.junit.jupiter.api.Test;

public class GameTest {

    GameService gameService = new GameService();

    @Test
    public void checkUpdate_WhenUpdate(){
        String gameId = "7dQLD";

        Game game = gameService.findByIdent(gameId);
        game.setAvailable(false);
        gameService.update(game);

    }

}
