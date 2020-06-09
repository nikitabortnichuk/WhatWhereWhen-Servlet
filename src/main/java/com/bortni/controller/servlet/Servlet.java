package com.bortni.controller.servlet;

import com.bortni.controller.command.ProfileCommand;
import com.bortni.controller.command.admin.SignInAdminCommand;
import com.bortni.controller.command.authorization.SignInCommand;
import com.bortni.controller.command.authorization.SignUpCommand;
import com.bortni.controller.command.game.CreateGameCommand;
import com.bortni.controller.command.admin.AdminDeleteQuestionCommand;
import com.bortni.controller.command.admin.AdminSaveQuestionCommand;
import com.bortni.controller.command.admin.AdminShowQuestionsCommand;
import com.bortni.controller.command.admin.AdminUpdatePageQuestionCommand;
import com.bortni.controller.command.Command;
import com.bortni.controller.command.HomeCommand;
import com.bortni.controller.command.game.FindGameCommand;
import com.bortni.controller.command.game.GameCommand;
import com.bortni.controller.util.UrlPath;
import com.bortni.service.AdminService;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;
import com.bortni.service.UserService;
import com.bortni.service.VariantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {

    private Map<String, Command> commands;

    @Override
    public void init() throws ServletException {
        commands = new HashMap<>();

        QuestionService questionService = new QuestionService();
        VariantService variantService = new VariantService();
        GameService gameService = new GameService();
        UserService userService = new UserService();
        AdminService adminService = new AdminService();

        commands.put("/", new HomeCommand());
        commands.put(UrlPath.HOME, new HomeCommand());
        commands.put(UrlPath.ADMIN_SHOW_QUESTIONS, new AdminShowQuestionsCommand(questionService));
        commands.put(UrlPath.ADMIN, new AdminShowQuestionsCommand(questionService));
        commands.put(UrlPath.ADMIN_ADD_QUESTION, new AdminSaveQuestionCommand(questionService, variantService));
        commands.put(UrlPath.ADMIN_DELETE_QUESTION, new AdminDeleteQuestionCommand(questionService));
        commands.put(UrlPath.ADMIN_UPDATE_PAGE_QUESTION, new AdminUpdatePageQuestionCommand(questionService));
        commands.put(UrlPath.ADMIN_UPDATE_QUESTION, new AdminSaveQuestionCommand(questionService, variantService));
        commands.put(UrlPath.CREATE_GAME, new CreateGameCommand(gameService, questionService));
        commands.put(UrlPath.FIND_GAME, new FindGameCommand(gameService));
        commands.put(UrlPath.SIGN_IN, new SignInCommand(userService));
        commands.put(UrlPath.SIGN_UP, new SignUpCommand(userService));
        commands.put(UrlPath.USER_PROFILE, new ProfileCommand(gameService));
        commands.put(UrlPath.ADMIN_SIGN_IN, new SignInAdminCommand(adminService));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }



    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String uriPath = request.getPathInfo();
        Command command = commands.get(uriPath);
        if(command == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            command.execute(request, response);
        }
    }
}
