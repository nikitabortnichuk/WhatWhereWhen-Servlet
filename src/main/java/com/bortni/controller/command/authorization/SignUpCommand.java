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

public class SignUpCommand implements Command {

    UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("readerSession");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(user != null){
            response.sendRedirect("/library" + UrlPath.USER_PROFILE);
        }
        else {
            if (userService.isUsernameExist(email)) {
                String message = "Username is already exist!";
                request.setAttribute("SignUpFailedMessage", message);
                request.getRequestDispatcher(Routes.SIGN_UP).forward(request, response);
            } else {
                user = User.builder()
                        .username(username)
                        .email(email)
                        .password(password)
                        .build();
                userService.save(user);
                request.getRequestDispatcher(Routes.SUCCESSFUL_REGISTRATION).forward(request, response);
            }
        }
    }
}
