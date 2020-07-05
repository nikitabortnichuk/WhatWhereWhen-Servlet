package com.bortni.controller.command.game;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameCommand implements Command {

    GameService gameService;

    public GameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Routes.GAME).forward(request, response);
    }
}
