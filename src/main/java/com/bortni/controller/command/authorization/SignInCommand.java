package com.bortni.controller.command.authorization;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.User;
import com.bortni.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {

    private UserService userService;

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = (User) request.getSession().getAttribute("userSession");

        if(user != null) {
            response.sendRedirect("/game-www/" + UrlPath.USER_PROFILE);
        }
        else {
            user = userService.findByUsernameAndPassword(username, password);
            if (user.equals(new User())){
                String message = "Wrong email or password";
                request.setAttribute("SignInFailedMessage", message);
                request.getRequestDispatcher(Routes.SIGN_IN).forward(request, response);
            }
            else {
                request.getSession().setAttribute("userSession", user);
                response.sendRedirect("/game-www" + UrlPath.USER_PROFILE);
            }
        }

    }
}
