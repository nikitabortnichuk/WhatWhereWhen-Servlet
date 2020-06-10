package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Admin;
import com.bortni.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInAdminCommand implements Command {

    AdminService adminService;

    public SignInAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Admin admin = (Admin) request.getSession().getAttribute("adminSession");
        if(admin != null){
            response.sendRedirect("/game-www" + UrlPath.ADMIN_SHOW_QUESTIONS);
        }
        else {

            try {
                admin = adminService.getAdministratorByLoginAndPassword(login, password);
                request.getSession().setAttribute("adminSession", admin);
                response.sendRedirect("/game-www" + UrlPath.ADMIN_SHOW_QUESTIONS);
            } catch (RuntimeException e){
                String message = "Wrong email or password";
                request.setAttribute("SignInFailedMessage", message);
                request.getRequestDispatcher(Routes.ADMIN_SIGN_IN).forward(request, response);
            }
        }

    }
}
