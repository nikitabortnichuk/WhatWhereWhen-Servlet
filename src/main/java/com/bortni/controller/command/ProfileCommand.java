package com.bortni.controller.command;

import com.bortni.controller.util.Routes;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.service.GameService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProfileCommand implements Command {

    GameService gameService;

    public ProfileCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("userSession");

        List<Game> gameList = gameService.findByUserId(user.getId());
        request.setAttribute("gameList", gameList);

        request.getRequestDispatcher(Routes.USER_PROFILE).forward(request, response);
    }
}
