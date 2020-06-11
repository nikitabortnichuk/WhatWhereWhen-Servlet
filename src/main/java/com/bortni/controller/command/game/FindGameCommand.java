package com.bortni.controller.command.game;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Game;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.exception.MySqlException;
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

        try {
            Game game = gameService.findByIdent(identificator);
            if(!game.isAvailable()){
                throw new RuntimeException("Game is not available");
            }
            request.getSession().setAttribute("game", game);
            response.sendRedirect("/game-www/game/" + game.getGameIdentification());
        }
        catch (EntityNotFoundException | MySqlException e){
            request.setAttribute("errorMessage", "Cannot find game!");
            request.getRequestDispatcher(Routes.HOME).forward(request, response);
        }
    }
}
