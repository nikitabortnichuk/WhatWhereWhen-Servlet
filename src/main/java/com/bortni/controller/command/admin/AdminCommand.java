package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Routes.ADMIN_SIGN_IN).forward(request, response);
    }
}
