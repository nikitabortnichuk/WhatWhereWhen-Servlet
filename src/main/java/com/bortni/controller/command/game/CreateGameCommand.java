package com.bortni.controller.command.game;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.GameStringGenerator;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Configuration;
import com.bortni.model.entity.Game;
import com.bortni.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateGameCommand implements Command {

    GameService gameService;

    public CreateGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Game game = getGameByRequest(request);
        gameService.save(game);

        String username = request.getParameter("username");

        request.setAttribute("game", game);
        request.getSession().setAttribute("username", username);

        response.sendRedirect("/game-www/game/" + game.getGameIdentification());
    }


    private Game getGameByRequest(HttpServletRequest request){
        int playersNumber = Integer.parseInt(request.getParameter("players_number"));
        int roundsNumber = Integer.parseInt(request.getParameter("rounds_number"));
        int roundTime = Integer.parseInt(request.getParameter("round_time"));

        String gameIdentificator = GameStringGenerator.generate();
        String password = GameStringGenerator.generate();

        return Game.builder()
                .gameIdentification(gameIdentificator)
                .password(password)
                .configuration(
                        Configuration.builder()
                                .playersNumber(playersNumber)
                                .roundsNumber(roundsNumber)
                                .roundTime(roundTime)
                                .build()
                )
                .build();
    }

}
