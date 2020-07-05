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

        final long recordsPerPage = 5L;
        int currentPage;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } else {
            currentPage = 1;
        }
        long from = (currentPage - 1) * recordsPerPage;
        User user = (User) request.getSession().getAttribute("userSession");

        List<Game> gameList = gameService.findByUserId(user.getId(), from, recordsPerPage);
        request.setAttribute("gameList", gameList);

        long rows = gameService.getGamesCountByUserId(user.getId());
        long nOfPages = rows / recordsPerPage;
        long rowsOnPage = rows % recordsPerPage;
        if (rowsOnPage > 0) {
            nOfPages++;
        }

        request.setAttribute("nOfPages", nOfPages);
        request.setAttribute("page", currentPage);

        request.getRequestDispatcher(Routes.USER_PROFILE).forward(request, response);
    }
}
