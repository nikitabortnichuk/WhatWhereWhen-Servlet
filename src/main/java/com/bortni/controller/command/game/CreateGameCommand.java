package com.bortni.controller.command.game;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.GameStringGenerator;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Configuration;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.question.Question;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateGameCommand implements Command {

    GameService gameService;
    QuestionService questionService;

    public CreateGameCommand(GameService gameService, QuestionService questionService) {
        this.gameService = gameService;
        this.questionService = questionService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Game game = getGameByRequest(request);
            gameService.save(game);
            String username = request.getParameter("username");
            request.getSession().setAttribute("game", game);
            request.getSession().setAttribute("username", username);
            response.sendRedirect("/game-www/game/" + game.getGameIdentification());

        } catch (RuntimeException e){
            response.sendRedirect("/?errorMessage=" + e.getMessage());
        }
    }

    private Game getGameByRequest(HttpServletRequest request){
        int playersNumber = Integer.parseInt(request.getParameter("players_number"));

        gameService.checkIfLessThanTwoOrThrowException(playersNumber);

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
                .isAvailable(true)
                .build();
    }



}
