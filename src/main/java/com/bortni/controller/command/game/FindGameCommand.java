package com.bortni.controller.command.game;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Game;
import com.bortni.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindGameCommand implements Command {

    GameService gameService;

    public FindGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String identificator = request.getParameter("game_identification");
        String username = request.getParameter("username");

        try {
            Game game = gameService.findByIdent(identificator);
            request.setAttribute("game", game);
            request.getSession().setAttribute("username", username);
            response.sendRedirect("/game-www/game/" + game.getGameIdentification());
        }
        catch (RuntimeException e){
            e.printStackTrace();
            response.sendError(404);
        }
    }
}
