package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.UrlPath;
import com.bortni.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminDeleteQuestionCommand implements Command {

    QuestionService questionService;

    public AdminDeleteQuestionCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("question_id"));

        questionService.delete(id);

        response.sendRedirect("/game-www" + UrlPath.ADMIN_SHOW_QUESTIONS);
    }
}
